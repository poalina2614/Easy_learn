package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Nothing extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nothing);
        text = findViewById(R.id.textik);
        String msg = getIntent().getStringExtra("who");
        if(msg=="study") text.setText("Вы не выбрали слова для изучения");
        else text.setText("Вы не выбрали слова для повторения");
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