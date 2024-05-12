package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutPr extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_pr);
        text = findViewById(R.id.text_about);
        text.setText("Английский с EasyLearn \n" +
                "Автор приложения: Атапина Полина, ученица java-курса от Samsung \n" +
                "Приложение создано для изучения новых слов и их повторения. Приложение следует использовать 2-3 раза в день с интервалом в несколько часов.\n" +
                "Все вопросы и предложения можете присылать на atpolina2017@gmail.com");

    }


    public void to_main_sett(View view) {
        Intent i = new Intent(AboutPr.this, MainActivity.class);
        i.putExtra("num", 3);
        startActivity(i);

    }
}