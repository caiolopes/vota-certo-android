package com.votacerto.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.votacerto.R;
import com.votacerto.model.Tweet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Mauricio Giordano on 11/7/15.
 * Author: Mauricio Giordano (mauricio.c.giordano@gmail.com)
 * Copyright (c) by Travell, 2015 - All rights reserved.
 */
public class SwipeAdapter extends BaseAdapter {

    private List<Tweet> dataList;
    private List<String> dataListColors;

    private Random randomGenerator = new Random();
    private List<String> colors = Arrays.asList(new String[]{
        "#16a085", "#27ae60", "#2980b9", "#8e44ad", "#2c3e50",
        "#f39c12", "#d35400", "#c0392b", "#7f8c8d"
    });

    public void setDataList(List<Tweet> _dataList) {
        dataList = _dataList;
        dataListColors = new ArrayList<>();

        String last = "";

        for (Tweet tweet : dataList) {
            String color = last;

            while (color.equals(last)) {
                color = colors.get(randomGenerator.nextInt(colors.size()));
            }

            dataListColors.add(color);
            last = color;
        }

        notifyDataSetChanged();
    }

    public void remove(int i) {
        dataList.remove(i);
        notifyDataSetChanged();
        notifyDataSetInvalidated();
    }

    public void popColor() {
        if (dataListColors.size() > 0) {
            dataListColors.remove(0);
        }
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

        TextView textView = (TextView) view.findViewById(R.id.text);
        final Tweet tweet = dataList.get(i);
        textView.setText(tweet.getText());

        switch (i) {
            case 0:
                view.setPadding(16, 8, 16, 8);
                view.findViewById(R.id.cardView).setBackgroundColor(Color.parseColor(dataListColors.get(0)));
                break;
            case 1:
                view.setPadding(16, 16, 16, 8);
                view.findViewById(R.id.cardView).setBackgroundColor(Color.parseColor(dataListColors.get(1)));
                break;
            default:
                view.setPadding(16, 24, 16, 8);
                view.findViewById(R.id.cardView).setBackgroundColor(Color.parseColor(dataListColors.get(2)));
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
