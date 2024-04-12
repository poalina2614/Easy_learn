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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        Intent i = new Intent(MainActivity.this, Studying.class);
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



    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}