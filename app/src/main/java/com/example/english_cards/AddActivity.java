package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }
    public void to_finish(View view) {
        Intent i = new Intent(AddActivity.this, MainActivity.class);
        i.putExtra("num", 2);
        startActivity(i);
    }
}