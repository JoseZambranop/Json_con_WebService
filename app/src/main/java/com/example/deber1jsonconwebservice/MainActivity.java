package com.example.deber1jsonconwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import WebService.Asynchtask;
import WebService.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask{
    TextView txtviewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtviewer=(TextView)findViewById(R.id.viewers);
        txtviewer.setMovementMethod(new ScrollingMovementMethod());
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("https://api.androidhive.info/contacts/",
        datos, MainActivity.this, MainActivity.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.i("ProcessFinish",result);
        ArrayList<HashMap<String,String>>
                contactList= new ArrayList<HashMap<String, String>>();
        String Dt="";
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray contacts = jsonObject.getJSONArray("contacts");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                String id = c.getString("id");
                String name = c.getString("name");
                String email = c.getString("email");
                String address = c.getString("address");
                String gender = c.getString("gender");

                JSONObject phone = c.getJSONObject("phone");
                String mobile = phone.getString("mobile");
                String home = phone.getString("home");
                String office = phone.getString("office");

                HashMap<String, String> contact = new HashMap<>();

                contact.put("id", id);
                contact.put("name", name);
                contact.put("email", email);
                contact.put("mobile", mobile);

                contactList.add(contact);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        for (int i=0; i< contactList.size();i++){
            HashMap<String,String> item=contactList.get(i);
            for (Map.Entry<String,String> mp: item.entrySet()){
                Dt=Dt+ mp.getKey()+": "+ mp.getValue()+"\n";
            }
            Dt=Dt+"\n\n";
        }
        txtviewer.setText(Dt);
    }
}
