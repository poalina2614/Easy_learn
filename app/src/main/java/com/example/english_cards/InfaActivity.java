package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

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
        String msg = getIntent().getStringExtra("group");
        ListView lv = findViewById(R.id.word_list);
        AdapterForWords adapter = new AdapterForWords(this, makeGroups(msg));
        lv.setAdapter(adapter);

    }
    MyGroups[] makeGroups(String group){
        Gson gson = new Gson();
        ArrayList<String> arr = new ArrayList<>();
        HashMap<String, HashMap<String, String>> myGroups = gson.fromJson(loadJson(), HashMap.class);
        for (String item: myGroups.get(group).keySet()){
            arr.add(item);
        };
        MyGroups[] ans = new MyGroups[myGroups.get(group).size()];
        for (int i = 0; i < ans.length; i++) {
            MyGroups gr = new MyGroups();
            gr.orig = arr.get(i);
            ans[i] = gr;
        }
        return ans;
    }

    String loadJson() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("data.json");
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

}