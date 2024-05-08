package com.example.english_cards;

import android.content.Context;
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
import com.google.gson.internal.LinkedTreeMap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class CatalogFrag extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.catalog, container, false);
        MyAdapter adapter = new MyAdapter(getContext(), MakeCategory());
        ListView lv = view.findViewById(R.id.cat_list);
        lv.setAdapter(adapter);
        setOnClickListener(lv);
        return view;
    }

    private void setOnClickListener(ListView lv) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                category group = (category) (lv.getItemAtPosition(position));
                String msg = group.name;
                Intent i = new Intent(getContext(), InfaActivity.class);
                i.putExtra("group", msg);
                startActivity(i);
            }
        });
    }

    category[] MakeCategory ()  {
        Gson gson = new Gson();
        ArrayList<String> catArr = new ArrayList<>();
        LinkedTreeMap<String, LinkedTreeMap<String, String>> myGroups = gson.fromJson(loadJson(), LinkedTreeMap.class);

        for (String key: myGroups.keySet()){
            catArr.add(key);
        }
        category[] arr = new category[myGroups.size()];
        for (int i = 0; i < arr.length; i++) {
            category cate = new category();
            cate.name = catArr.get(i);
            int words = myGroups.get(catArr.get(i)).size();
            cate.count = words + " "+getWord(words);

            arr[i] = cate;
        }

        return arr;
    }


    String loadJson() {
        String json = null;
        try {
            InputStream is = getActivity().openFileInput("data.json");
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


    public static String getWord(int n) {
        // смотрим две последние цифры
        int result = n % 100;
        if (result >=10 && result <= 20) {
            // если окончание 11 - 20
            return "слов";
        }

        // смотрим одну последнюю цифру
        result = n % 10;
        if (result == 0 || result > 4) {
            return "слов";
        }
        if (result > 1) {
            return "слова";
        } if (result == 1) {
            return "слово";
        }
        return null;
    }

}