package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Repeating extends AppCompatActivity {
    int maxi;
    int this_num = 1;
    Button nextw, backw;
    TextView origs, trans, know;
    HashMap<String, String> all_words;
    ArrayList<String> choise_arr = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeating);
        nextw = findViewById(R.id.next_rep);
        backw = findViewById(R.id.backw_rep);
        origs = findViewById(R.id.orig_rep);
        trans = findViewById(R.id.trans_rep);
        know = findViewById(R.id.know_btn);

        all_words = get_words();
        ArrayList<String> arr = new ArrayList<>();
        for(String item: all_words.keySet()) arr.add(item);
        int n;
        maxi = all_words.size();
        HashMap<String, String> checking = new HashMap<>();
        for (int i =0; i<maxi; i ++){
            n = (int) Math.floor(Math.random()*arr.size());
            choise_arr.add(arr.get(n));
            checking.put(arr.get(n), all_words.get(arr.get(n)));
            arr.remove(n);
        }
        origs.setText(choise_arr.get(this_num));
        backw.setEnabled(false);



    }
    public void back2(View view) {
        Intent i = new Intent(Repeating.this, MainActivity.class);
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
        if(this_num > maxi){
            Intent i = new Intent(Repeating.this, MainActivity.class);
            i.putExtra("num", 1);
            startActivity(i);
        }
        else {
            this_num ++;
            origs.setText(choise_arr.get(this_num));
            trans.setText("");
            know.setEnabled(true);

        }

        if(this_num != 1) backw.setEnabled(true);
    }
    public void to_get_trans(View view){
        trans.setText(all_words.get(choise_arr.get(this_num)));
        know.setEnabled(false);
    }

    public void last_w(View view) {
        this_num--;
        if(this_num == 1) backw.setEnabled(false);
        origs.setText(choise_arr.get(this_num));
        trans.setText("");
        know.setEnabled(true);
    }
}