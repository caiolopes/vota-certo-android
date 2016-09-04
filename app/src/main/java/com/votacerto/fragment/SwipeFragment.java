package com.votacerto.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.votacerto.MainActivity;
import com.votacerto.R;
import com.votacerto.adapter.SwipeAdapter;
import com.votacerto.model.Tweet;
import com.votacerto.network.Api;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SwipeFragment extends Fragment {
    private View mView;
    private View progressBar;
    private View actions;
    private SwipeFlingAdapterView flingContainer;
    private SwipeAdapter adapter;
    private List<Tweet> dataList;
    private int dataListSize = 0;

    public static SwipeFragment newInstance() {
        Bundle args = new Bundle();

        SwipeFragment fragment = new SwipeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_swipe, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        flingContainer = (SwipeFlingAdapterView) mView.findViewById(R.id.frame);
        progressBar = mView.findViewById(R.id.progressBar);
        RelativeLayout like = (RelativeLayout) mView.findViewById(R.id.like);
        RelativeLayout dislike = (RelativeLayout) mView.findViewById(R.id.dislike);
        adapter = new SwipeAdapter();

        flingContainer.setAdapter(adapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {

            private int selected = 0;

            @Override
            public void removeFirstObjectInAdapter() {
                adapter.remove(0);
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                selected++;
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_left).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_right).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }

        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getCount() == 0) return;
                flingContainer.getTopCardListener().selectRight();
                View _view = flingContainer.getSelectedView();
                _view.findViewById(R.id.item_swipe_right).setAlpha(1);
                _view.findViewById(R.id.item_swipe_right_indicator).setAlpha(1);
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getCount() == 0) return;
                flingContainer.getTopCardListener().selectLeft();
                View _view = flingContainer.getSelectedView();
                _view.findViewById(R.id.item_swipe_left).setAlpha(1);
                _view.findViewById(R.id.item_swipe_left_indicator).setAlpha(1);
            }
        });

        progressBar.setVisibility(View.VISIBLE);
        dataList = new ArrayList<>();
        Log.v("TWEET", "Get tweets with access-token: " + ((MainActivity)getActivity()).user.getAccessToken());
        Api.getInstance().getTweets(((MainActivity)getActivity()).user.getAccessToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Tweet>>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        adapter.setDataList(dataList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TWEET", "Error", e);
                    }

                    @Override
                    public void onNext(List<Tweet> tweets) {
                        dataList = tweets;
                    }
                });
    }
}
