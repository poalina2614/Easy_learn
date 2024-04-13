package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Studying extends AppCompatActivity {
    TextView orig, result;
    Button check, next;
    EditText trans;
    int this_num = 1;
    HashMap<String, String> all_words;
    ArrayList<String> choise_arr = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studying);
        orig = findViewById(R.id.orig_word);
        trans = findViewById(R.id.trans_input);
        result = findViewById(R.id.check_ans);
        check = findViewById(R.id.check_btn);
        next = findViewById(R.id.button2);
        all_words = get_words();
        ArrayList<String> arr = new ArrayList<>();
        for(String item: all_words.keySet()) arr.add(item);
        int n;
        HashMap<String, String> checking = new HashMap<>();
        for (int i =0; i<=10; i ++){
            n = (int) Math.floor(Math.random()*arr.size());
            choise_arr.add(arr.get(n));
            checking.put(arr.get(n), all_words.get(arr.get(n)));
            arr.remove(n);
        }
        orig.setText(choise_arr.get(this_num));

    }

    public void back(View view) {
        Intent i = new Intent(Studying.this, MainActivity.class);
        i.putExtra("num", 1);
        startActivity(i);
    }

    HashMap<String, String> get_words(){
        Gson gson = new Gson();
        HashMap<String, Boolean> liked = gson.fromJson(loadJson("liked.json"), HashMap.class);
        LinkedTreeMap<String, LinkedTreeMap<String, String>> all_words = gson.fromJson(loadJson("data.json"), LinkedTreeMap.class);
        HashMap<String, String> like_words = new HashMap<>();
        for(String key: liked.keySet()){
            if (liked.get(key) == true) {
                for(String word: all_words.get(key).keySet()){
                    like_words.put(word, all_words.get(key).get(word));
                }
            }
        }
        return like_words;
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

    public void nextWord(View view){
        if(this_num == 9){
            Intent i = new Intent(Studying.this, MainActivity.class);
            i.putExtra("num", 1);
            startActivity(i);
        }
        else {
            this_num ++;
            orig.setText(choise_arr.get(this_num));
            trans.setText("");
            result.setText("");

        }
    }
    public void to_check(View view){
        if(trans.getText().toString().toLowerCase(Locale.ROOT).equals(all_words.get(choise_arr.get(this_num)))) result.setText("маладечик все верно, можешь двигаться дальше");
        else {
            result.setText("эх, ты накосячала, вот правильный перевод: " + all_words.get(choise_arr.get(this_num)));
            Toast.makeText(this, trans.getText().toString(), Toast.LENGTH_LONG).show();
        }
    }
}
