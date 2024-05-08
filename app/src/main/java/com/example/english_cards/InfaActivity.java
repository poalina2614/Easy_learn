package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class InfaActivity extends AppCompatActivity implements
        TextToSpeech.OnInitListener {
    TextView text;
    TextToSpeech tts;
    String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infa);
        text = findViewById(R.id.textView3);
        ImageButton button = findViewById(R.id.imageButton);
        button.setBackgroundColor(0);
        msg = getIntent().getStringExtra("group");
        TextView name = findViewById(R.id.name_txt);
        name.setText(msg);


        ListView lv = findViewById(R.id.word_list);


        tts = new TextToSpeech(this, this);
        AdapterForWords adapter = new AdapterForWords(this, makeGroups(msg));
        lv.setAdapter(adapter);
        setOnClickListener(lv);

    }

    private void setOnClickListener(ListView lv) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MyGroups group = (MyGroups) (lv.getItemAtPosition(position));
                String msg = group.orig;
                speakOut(msg);


            }
        });
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
        if(ans.length == 0) text.setText("пока что здесь нет слов");
        else {
            for (int i = 0; i < ans.length; i++) {
            MyGroups gr = new MyGroups();
            gr.orig = orig.get(i);
            gr.trans = trans.get(i);
            ans[i] = gr;
            }
        }
        return ans;
    }

    public void delete(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Вы точно хотите удалить этот каталог?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Gson gson = new Gson();
                        LinkedTreeMap<String, LinkedTreeMap<String, String>> myGroups = gson.fromJson(loadJson(), LinkedTreeMap.class);
                        myGroups.remove(msg);
                        String json = gson.toJson(myGroups);
                        try {
                            FileOutputStream fos = openFileOutput("data.json", Context.MODE_PRIVATE);
                            fos.write(json.getBytes());
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent i = new Intent(InfaActivity.this, MainActivity.class);
                        i.putExtra("num", 2);
                        startActivity(i);

                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("нет", null)
                .show();
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


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut(String text) {


        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}