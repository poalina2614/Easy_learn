package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class AddCat extends AppCompatActivity {
    Button addCat;
    EditText category;
    TextView status;
    HashMap<String, HashMap<String, String>> all_words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);
        addCat = findViewById(R.id.fin_cat_btn);
        category = findViewById(R.id.new_cat);
        status = findViewById(R.id.warning);
        Gson gson = new Gson();
        all_words = gson.fromJson(loadJson("data.json"), HashMap.class);
        category.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addCat.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addCat.setEnabled(true);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals("")) addCat.setEnabled(false);
                else addCat.setEnabled(true);
            }
        });
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


    public void to_back_cat(View view) {
        Intent i = new Intent(AddCat.this, MainActivity.class);
        i.putExtra("num", 2);
        startActivity(i);
    }

    public void to_fin_cat(View view) {
        if(all_words.containsKey(category.getText().toString())) {
            status.setText("такая категория уже есть");
            addCat.setEnabled(false);
        }
        else {
            Gson gson = new Gson();
            HashMap<String, HashMap<String, String>> all_words = gson.fromJson(loadJson("data.json"), HashMap.class);
            all_words.put(category.getText().toString(), new HashMap<String, String>());
            String json = gson.toJson(all_words);
            try {
                FileOutputStream fos = openFileOutput("data.json", Context.MODE_PRIVATE);
                fos.write(json.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Intent i = new Intent(AddCat.this, MainActivity.class);
            i.putExtra("num", 2);
            startActivity(i);
        }
    }
}