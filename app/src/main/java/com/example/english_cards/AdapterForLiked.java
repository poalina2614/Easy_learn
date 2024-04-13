package com.example.english_cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class AdapterForLiked extends ArrayAdapter<My_cats> {
    public AdapterForLiked(Context context, My_cats[] arr) {
        super(context, R.layout.adapter_choice, arr);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final My_cats cats = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_choice, null);
        }

// Заполняем адаптер
        ((TextView) convertView.findViewById(R.id.cat_name)).setText(cats.name);
        CheckBox ch = (CheckBox) convertView.findViewById(R.id.like_check);
        ch.setChecked(cats.like);
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cats.like = ((CheckBox) v).isChecked();
            }
        });
        return convertView;
    }
}