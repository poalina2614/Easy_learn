package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.english_cards.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    BDHelper bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new PractFragment());


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.practice:
                    replaceFragment(new PractFragment());
                    break;
                case R.id.catalog:
                    replaceFragment(new CatalogFrag());
//                    MyAdapter adapter = new MyAdapter(this, MakeCategory());
//                    ListView lv = findViewById(R.id.cat_list);
//                    lv.setAdapter(adapter);

                    break;
                case R.id.settings:
                    replaceFragment(new SettFragment());
                    break;
            }



            return true;
        });

//        bdHelper = new BDHelper(this);
//        SQLiteDatabase database = bdHelper.getWritableDatabase();
    }



    public void to_study(View view) {
        Intent i = new Intent(MainActivity.this, Studying.class);
        startActivity(i);
    }

    public void to_learn(View view) {
        Intent i = new Intent(MainActivity.this, Repeating.class);
        startActivity(i);
    }

    category[] MakeCategory ()  {
        category[] arr = new category[12];

        String[] catArr = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        for (int i = 0; i < arr.length; i++) {
            category cate = new category();
            cate.name = catArr[i];
            arr[i] = cate;
        }
        return arr;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}