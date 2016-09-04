package com.votacerto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.votacerto.R;
import com.votacerto.model.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mauricio Giordano on 11/7/15.
 * Author: Mauricio Giordano (mauricio.c.giordano@gmail.com)
 * Copyright (c) by Travell, 2015 - All rights reserved.
 */
public class SwipeAdapter extends BaseAdapter {

    private List<Tweet> dataList;

    public void setDataList(List<Tweet> _dataList) {
        dataList = _dataList;
        notifyDataSetChanged();
    }

    public void remove(int i) {
        dataList.remove(i);
        notifyDataSetChanged();
        notifyDataSetInvalidated();
    }

    public SwipeAdapter() {
        dataList = new ArrayList<>();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.swipe_item, viewGroup, false);
        }

        TextView text = (TextView) view.findViewById(R.id.text);

        final Tweet tweet = dataList.get(i);

        text.setText(tweet.getText());

        switch (i) {
            case 0:
                view.setPadding(16, 8, 16, 8);
                break;
            case 1:
                view.setPadding(16, 16, 16, 8);
                break;
            default:
                view.setPadding(16, 24, 16, 8);
                break;
        }

        return view;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Tweet getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return dataList.get(i).getId().hashCode();
    }
}
