package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Studying extends AppCompatActivity {
    TextView orig, result;
    Button check;
    EditText trans;
    int this_num = 0;
    int maxi, secondsCounter;
    int grade = 0;
    HashMap<String, String> all_words;
    ArrayList<String> choise_arr = new ArrayList<>();
    HashMap<String, String> checking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studying);
        orig = findViewById(R.id.orig_word);
        trans = findViewById(R.id.trans_input);
        result = findViewById(R.id.check_ans);
        check = findViewById(R.id.check_btn);
        all_words = get_words(); // тут список всех слов, которые выбраны
        ArrayList<String> arr = new ArrayList<>();
        for(String item: all_words.keySet()) arr.add(item);
        int n, vid;
        maxi = Math.min(all_words.size(), 10);
        checking = new HashMap<>();
        for (int i =0; i<maxi; i ++){
            n = (int) Math.floor(Math.random()*arr.size());
            vid = (int) Math.floor(Math.random()*2);
            if(vid == 0) {
                choise_arr.add(arr.get(n));
                checking.put(arr.get(n), all_words.get(arr.get(n)));
            }
            else {
                choise_arr.add(all_words.get(arr.get(n)));
                checking.put(all_words.get(arr.get(n)), arr.get(n));
            }
            arr.remove(n);
        }
        check.setEnabled(false);
        orig.setText(choise_arr.get(this_num));


        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                secondsCounter++;
            }
        };

        timer.schedule(task, 0, 1000);

        trans.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                check.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                check.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals(""))check.setEnabled(false);
                else {
                    check.setEnabled(true);
                }

            }
        });

    }

    public void back(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Вы точно хотите уйти?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Studying.this, MainActivity.class);
                        i.putExtra("num", 1);
                        startActivity(i);

                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("нет", null)
                .show();
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

    public void buttonchik(View view){
        if(check.getText().toString().equals("проверить")){
            to_check();
            check.setText("далее");
        }
        else {
            nextWord();
            check.setText("проверить");
            trans.setEnabled(true);


        }
    }

    public void nextWord(){
        if(this_num == maxi - 1){
            Intent i = new Intent(Studying.this, Result.class);
            i.putExtra("result", grade);
            i.putExtra("time", secondsCounter);
            startActivity(i);
        }
        else {
            this_num ++;
            orig.setText(choise_arr.get(this_num));
            trans.setText("");
            result.setText("");

        }
    }

    public void to_check(){
        trans.setEnabled(false);
        String answer = trans.getText().toString().toLowerCase(Locale.ROOT);
        answer = answer.trim();
        answer = answer.replaceAll("\\s+", " ");
        int wrong = StringUtils.getLevenshteinDistance(answer, checking.get(choise_arr.get(this_num)));
        if(wrong < 3) {
            result.setText("Правильно");
            result.setTextColor(Color.parseColor("#138808"));
            grade ++;
        }
        else {
            result.setText("Не совсем, вот правильный перевод: " + checking.get(choise_arr.get(this_num)));
            result.setTextColor(Color.parseColor("#CC0605"));
        }
    }

}
