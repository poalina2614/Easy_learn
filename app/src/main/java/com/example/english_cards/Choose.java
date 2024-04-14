package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Choose extends AppCompatActivity {
    int size;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        AdapterForLiked adapter = new AdapterForLiked(this, MakeCategory());
        lv = findViewById(R.id.choose_list);

        lv.setAdapter(adapter);
    }

    My_cats[] MakeCategory ()  {
        Gson gson = new Gson();
        ArrayList<String> catArr = new ArrayList<>();
        ArrayList<Boolean> likeArr = new ArrayList<>();
        HashMap<String, Boolean> myGroups = gson.fromJson(loadJson("liked.json"), HashMap.class);
        size = myGroups.size();

        for (String key: myGroups.keySet()){
            catArr.add(key);
            likeArr.add(myGroups.get(key));
        }
        My_cats[] arr = new My_cats[myGroups.size()];
        for (int i = 0; i < arr.length; i++) {
            My_cats cat = new My_cats();
            cat.name = catArr.get(i);
            cat.like = likeArr.get(i);
            arr[i] = cat;
        }

        return arr;
    }

    String loadJson(String name) {
        String json = null;
        try {
            InputStream is = openFileInput(name);
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


    public void to_save(View view) {
        Gson gson = new Gson();
        HashMap<String, Boolean> redact_cats = new HashMap<>();

        for(int j = 0; j<size; j ++){
            My_cats cat = (My_cats) lv.getItemAtPosition(j);
            redact_cats.put(cat.name, cat.like);
        }
        String json = gson.toJson(redact_cats);
        try {
            FileOutputStream fos = openFileOutput("liked.json", Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent i = new Intent(Choose.this, MainActivity.class);
        i.putExtra("num", 1);
        startActivity(i);
    }
    public void to_back_ch(View view) {
        Intent i = new Intent(Choose.this, MainActivity.class);
        i.putExtra("num", 1);
        startActivity(i);
    }

}