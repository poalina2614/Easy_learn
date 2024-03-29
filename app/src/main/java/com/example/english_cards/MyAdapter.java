package com.example.english_cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


public class MyAdapter extends ArrayAdapter<category> {

    public MyAdapter(Context context, category[] arr) {
        super(context, R.layout.adapter_item, arr);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final category cate = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item, null);
        }

// Заполняем адаптер
        ((TextView) convertView.findViewById(R.id.textView4)).setText(cate.name);
        CheckBox ch = convertView.findViewById(R.id.checkBox);
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cate.mine = ((CheckBox) v).isChecked();
            }
        });
        return convertView;
    }
}