package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class AddActivity extends AppCompatActivity {
    Spinner spinner;
    EditText orig_word, trans_word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        spinner = findViewById(R.id.choose_cat);
        orig_word = findViewById(R.id.add_orig_txt);
        trans_word = findViewById(R.id.add_trans_txt);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, MakeChoose());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void to_finish(View view) {
        Gson gson = new Gson();
        LinkedTreeMap<String, LinkedTreeMap<String, String>> myGroups = gson.fromJson(loadJson(), LinkedTreeMap.class);
        String cat = (String) spinner.getSelectedItem();
        myGroups.get(cat).put(orig_word.getText().toString(), trans_word.getText().toString());
        String json = gson.toJson(myGroups);
        try {
            FileOutputStream fos = openFileOutput("data.json", Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent i = new Intent(AddActivity.this, MainActivity.class);
        i.putExtra("num", 2);
        startActivity(i);
    }

    String[] MakeChoose () {
        Gson gson = new Gson();
        HashMap<String, HashMap<String, String>> myGroups = gson.fromJson(loadJson(), HashMap.class);
        String[] ans = new String[myGroups.size()];
        ArrayList<String> arr = new ArrayList<>();
        for (String key: myGroups.keySet()){
            arr.add(key);
        }
        for (int i = 0; i < arr.size(); i++) {
            ans[i] = arr.get(i);
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

}