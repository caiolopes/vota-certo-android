package com.votacerto;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.votacerto.adapter.AnalysisAdapter;
import com.votacerto.model.Analysis;
import com.votacerto.model.Candidate;
import com.votacerto.model.User;
import com.votacerto.network.Api;
import com.votacerto.util.Helper;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CandidateActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {
    public static final String CANDIDATE = "CANDIDATE";
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private TextView mCandidateName;
    private TextView mCandidateParty;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private SimpleDraweeView mCover;
    private SimpleDraweeView mPic;
    private RecyclerView mRecyclerView;
    private Candidate candidate = null;
    private List<Analysis> mAnalysisList;
    private AnalysisAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate);

        bindActivity();

        if(getIntent().hasExtra(CANDIDATE)) {
            candidate = new Gson().fromJson(getIntent().getStringExtra(CANDIDATE), Candidate.class);
            Log.v("CANDIDATE", candidate.getPolitician().getCover());
        }

        mPic.setImageURI(Uri.parse(candidate.getPolitician().getPicture()));
        mCover.setImageURI(Uri.parse(candidate.getPolitician().getCover()));
        mTitle.setText(candidate.getPolitician().getName());
        mCandidateName.setText(candidate.getPolitician().getName());
        mCandidateParty.setText(candidate.getPolitician().getParty().getName());

        mAppBarLayout.addOnOffsetChangedListener(this);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);

        mAnalysisList = new ArrayList<>();
        mAdapter = new AnalysisAdapter(mAnalysisList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        User user = Helper.getUser(getSharedPreferences(MyApplication.PREF, MODE_PRIVATE).getAll());
        if (user != null) {
            Api.getInstance().getAnalysisByPolitician(candidate.getPolitician().getId(), user.getAccessToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Analysis>>() {
                        @Override
                        public void onCompleted() {
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("ANALYSIS", "ERROR", e);
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNext(List<Analysis> analysisList) {
                            mAnalysisList.clear();
                            mAnalysisList.addAll(analysisList);
                        }
                    });
        }
    }

    private void bindActivity() {
        mToolbar        = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle          = (TextView) findViewById(R.id.main_textview_title);
        mCandidateName  = (TextView) findViewById(R.id.candidate_name);
        mCandidateParty = (TextView) findViewById(R.id.candidate_party);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.main_appbar);
        mCover          = (SimpleDraweeView) findViewById(R.id.main_cover);
        mPic            = (SimpleDraweeView) findViewById(R.id.main_pic);
        mRecyclerView   = (RecyclerView) findViewById(R.id.tweet_list);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
