package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Nothing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nothing);
    }

    public void to_back(View view) {
        Intent i = new Intent(Nothing.this, MainActivity.class);
        i.putExtra("num", 1);
        startActivity(i);
    }
    public void choose_words(View view) {
        Intent i = new Intent(Nothing.this, Choose.class);
        startActivity(i);
    }

}