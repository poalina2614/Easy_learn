package com.example.english_cards;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CatalogFrag extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.catalog, container, false);
        MyAdapter adapter = new MyAdapter(getContext(), MakeCategory());
        ListView lv = view.findViewById(R.id.cat_list);
        lv.setAdapter(adapter);
        lv.setClickable(true);
        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "its work", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(), "its work", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    category[] MakeCategory ()  {
        Gson gson = new Gson();
        String json = null;
        ArrayList<String> catArr = new ArrayList<>();
        try {
            InputStream is = getContext().getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        MyGroups myGroups = gson.fromJson(json, MyGroups.class);
        for (String key: myGroups.category.keySet()){
            catArr.add(key);
        }
        category[] arr = new category[myGroups.category.size()];
        for (int i = 0; i < arr.length; i++) {
            category cate = new category();
            cate.name = catArr.get(i);
            arr[i] = cate;
        }

        return arr;
    }


}