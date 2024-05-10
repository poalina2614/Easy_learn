package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class ReWord extends AppCompatActivity {
    EditText new_orig, new_trans;
    String old_orig, old_trans, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_word);
        new_orig = findViewById(R.id.orig_new);
        new_trans = findViewById(R.id.trans_new);
        old_orig = getIntent().getStringExtra("orig");
        old_trans = getIntent().getStringExtra("trans");
        name = getIntent().getStringExtra("name");
        new_orig.setText(old_orig);
        new_trans.setText(old_trans);
    }

    public void to_infa2(View view) {
        Intent i = new Intent(ReWord.this, InfaActivity.class);
        startActivity(i);
    }

    public void finish_word(View view) {
        String orig = new_orig
                .getText()
                .toString()
                .toLowerCase(Locale.ROOT)
                .trim()
                .replaceAll("\\p{Punct}", "");
        String trans = new_trans
                .getText()
                .toString()
                .toLowerCase(Locale.ROOT)
                .trim()
                .replaceAll("\\p{Punct}", "");

        if(!(orig.equals("")) && !(trans.equals(""))) {
            Gson gson = new Gson();
            LinkedTreeMap<String, LinkedTreeMap<String, String>> myGroups = gson.fromJson(loadJson(), LinkedTreeMap.class);
            myGroups.get(name).remove(old_orig);
            myGroups.get(name).put(orig, trans);
            String json = gson.toJson(myGroups);
            try {
                FileOutputStream fos = openFileOutput("data.json", Context.MODE_PRIVATE);
                fos.write(json.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent i = new Intent(ReWord.this, InfaActivity.class);
            startActivity(i);
        }
        else Toast.makeText(this, "вы не заполнили поля", Toast.LENGTH_SHORT).show();
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