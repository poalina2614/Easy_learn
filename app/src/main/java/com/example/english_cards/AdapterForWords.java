package com.example.english_cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterForWords  extends ArrayAdapter<MyGroups> {
    public AdapterForWords(Context context, MyGroups[] arr) {
        super(context, R.layout.word_ite, arr);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MyGroups groups = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item, null);
        }

// Заполняем адаптер
        ((TextView) convertView.findViewById(R.id.orig_text)).setText(groups.orig);
        ((TextView) convertView.findViewById(R.id.trans_text)).setText(groups.trans);

        return convertView;
    }
}
