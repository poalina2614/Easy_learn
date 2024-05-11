package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ChangeCat extends AppCompatActivity {
    String name;
    ListView lv;
    My_cats[] words;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_cat);
        name = getIntent().getStringExtra("group");
        lv = findViewById(R.id.del_list);
        words = makeGroups(name);
        AdapterForLiked adapter = new AdapterForLiked(this, words);
        lv.setAdapter(adapter);
    }

    public void to_infa(View view) {
        Intent i  = new Intent(ChangeCat.this, InfaActivity.class);
        startActivity(i);
    }

    ArrayList<Boolean> get_check() {
        ArrayList<Boolean> ans = new ArrayList<>();
        for (int i = 0; i < words.length; i++){
            My_cats word = (My_cats) lv.getItemAtPosition(i);
            ans.add(word.like);
        }
        return ans;
    }

    public void to_del_word(View view) {
        ArrayList<Boolean> arr = get_check();
        if(arr.contains(true)) {
            new AlertDialog.Builder(this)
                    .setMessage("Вы точно хотите удалить эти слова?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("да", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Gson gson = new Gson();
                            LinkedTreeMap<String, LinkedTreeMap<String, String>> myGroups = gson.fromJson(loadJson(), LinkedTreeMap.class);
                            for (int i = 0; i<words.length; i++){
                                My_cats word = (My_cats) lv.getItemAtPosition(i);
                                if(word.like) {
                                    String key = word.name.split(" - ")[0];
                                    myGroups.get(name).remove(key);
                                }
                            }
                            String json = gson.toJson(myGroups);
                            try {
                                FileOutputStream fos = openFileOutput("data.json", Context.MODE_PRIVATE);
                                fos.write(json.getBytes());
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Intent i = new Intent(ChangeCat.this, InfaActivity.class);
                            startActivity(i);

                            // Continue with delete operation
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("нет", null)
                    .show();

        }
        else {
            Toast.makeText(this, "вы не выбрали слова", Toast.LENGTH_SHORT).show();
        }
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

    My_cats[] makeGroups(String group){
        Gson gson = new Gson();
        ArrayList<String> orig = new ArrayList<>();
        ArrayList<String> trans = new ArrayList<>();
        LinkedTreeMap<String, LinkedTreeMap<String, String>> myGroups = gson.fromJson(loadJson(), LinkedTreeMap.class);
        for (String item: myGroups.get(group).keySet()){
            orig.add(item);
            trans.add(myGroups.get(group).get(item));
        };
        My_cats[] ans = new My_cats[myGroups.get(group).size()];
        for (int i = 0; i < ans.length; i++) {
            My_cats word = new My_cats();
            word.name = orig.get(i) + " - " + trans.get(i);
            word.like = false;
            ans[i] = word;
        }
        return ans;
    }


}