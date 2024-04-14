package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.english_cards.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public int num_frag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        num_frag = getIntent().getIntExtra("num", 1);
        if (num_frag == 1) replaceFragment(new PractFragment());
        else if (num_frag == 2) replaceFragment(new CatalogFrag());
        else replaceFragment(new SettFragment());

        Gson gson = new Gson();
        HashMap<String, HashMap<String, String>> myGroups = gson.fromJson(loadJson("data.json"), HashMap.class);
        if(myGroups==null) {
            Create_db();
        }
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.practice:
                    replaceFragment(new PractFragment());
                    break;
                case R.id.catalog:
                    replaceFragment(new CatalogFrag());

                    break;
                case R.id.settings:
                    replaceFragment(new SettFragment());
                    break;
            }
            return true;
        });
    }


    public void to_study(View view) {
        HashMap<String, String> words = get_words();
        if(words.isEmpty()) {
            Intent i = new Intent(MainActivity.this, Nothing.class);
            startActivity(i);
        }
        else {
            Intent i = new Intent(MainActivity.this, Studying.class);
            startActivity(i);
        }
    }
    public void to_choose(View view) {
        Intent i = new Intent(MainActivity.this, Choose.class);
        startActivity(i);
    }
    public void to_learn(View view) {
        Intent i = new Intent(MainActivity.this, Repeating.class);
        startActivity(i);
    }
    public void to_add(View view) {
        Intent i = new Intent(MainActivity.this, AddActivity.class);
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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


    public void Create_db(){
        String db = Create_json("data.json");
        String like = Create_json("liked.json");
        // создание списка категорий
        try {
            FileOutputStream fos = openFileOutput("data.json", Context.MODE_PRIVATE);
            fos.write(db.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // создание выбранных категорий
        try {
            FileOutputStream fos = openFileOutput("liked.json", Context.MODE_PRIVATE);
            fos.write(like.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String Create_json(String name) {
        String json = null;
        try {
            InputStream is = getAssets().open(name);
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

}