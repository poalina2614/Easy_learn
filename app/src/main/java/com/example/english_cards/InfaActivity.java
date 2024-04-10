package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class InfaActivity extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infa);
        text = findViewById(R.id.textView3);
        ImageButton button = findViewById(R.id.imageButton);
        button.setBackgroundColor(0);
        String msg = getIntent().getStringExtra("group");
        TextView name = findViewById(R.id.name_txt);
        name.setText(msg);
        ListView lv = findViewById(R.id.word_list);
        AdapterForWords adapter = new AdapterForWords(this, makeGroups(msg));
        lv.setAdapter(adapter);

    }
    MyGroups[] makeGroups(String group){
        Gson gson = new Gson();
        ArrayList<String> orig = new ArrayList<>();
        ArrayList<String> trans = new ArrayList<>();
        LinkedTreeMap<String, LinkedTreeMap<String, String>> myGroups = gson.fromJson(loadJson(), LinkedTreeMap.class);
        for (String item: myGroups.get(group).keySet()){
            orig.add(item);
            trans.add(myGroups.get(group).get(item));
        };
        MyGroups[] ans = new MyGroups[myGroups.get(group).size()];
        for (int i = 0; i < ans.length; i++) {
            MyGroups gr = new MyGroups();
            gr.orig = orig.get(i);
            gr.trans = trans.get(i);
            ans[i] = gr;
        }
        return ans;
    }

    String loadJson() {
        String json = null;
        try {
            InputStream is = openFileInput("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    public void to_catalog(View view) {
        Intent i = new Intent(InfaActivity.this, MainActivity.class);
        i.putExtra("num", 2);
        startActivity(i);
    }


}