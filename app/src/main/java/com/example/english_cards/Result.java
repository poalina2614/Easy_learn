package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    TextView grade, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        grade = findViewById(R.id.result_txt);
        time = findViewById(R.id.time);
        int result = getIntent().getIntExtra("result", 0)*10;
        int timer = getIntent().getIntExtra("time", 0);
        int mm = timer/60;
        int ss = timer - mm*60;
        grade.setText(result+"%");
        if (mm < 10) time.setText("0" + mm + ":" + ss);
        else time.setText(mm + ":" + ss);
    }


    public void to_back_res(View view) {
        Intent i = new Intent(Result.this, MainActivity.class);
        startActivity(i);
    }
}