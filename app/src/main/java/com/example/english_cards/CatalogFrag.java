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
        HashMap<String, HashMap<String, String>> myGroups = gson.fromJson(loadJson(), HashMap.class);

        if(myGroups==null) {
            Create_db();
            myGroups = gson.fromJson(loadJson(), HashMap.class);
        }

        for (String key: myGroups.keySet()){
            catArr.add(key);
        }
        category[] arr = new category[myGroups.size()];
        for (int i = 0; i < arr.length; i++) {
            category cate = new category();
            cate.name = catArr.get(i);
            arr[i] = cate;
        }

        return arr;
    }

    public void Create_db(){
        String db = Create_json();
        try {
            FileOutputStream fos = getActivity().openFileOutput("data.json", Context.MODE_PRIVATE);
            fos.write(db.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String Create_json() {
        String json = null;
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
        return json;
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


}