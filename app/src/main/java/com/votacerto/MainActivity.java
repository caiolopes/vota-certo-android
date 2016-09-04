package com.votacerto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.votacerto.fragment.CandidatesFragment;
import com.votacerto.fragment.ProfileFragment;
import com.votacerto.fragment.SwipeFragment;
import com.votacerto.model.User;
import com.votacerto.util.Helper;

public class MainActivity extends AppCompatActivity {
    public static final int NUM_PAGES = 3;
    private ViewPager mViewPager;
    public User user;
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = Helper.getUser(getSharedPreferences(MyApplication.PREF, MODE_PRIVATE).getAll());
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
        // Setup View Pager
        assert mViewPager != null;
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(NUM_PAGES-1);
        ViewPagerAdapter mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        setTitle(getString(R.string.app_name));
        setupTabs();
    }

    void setupTabs() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_swipe:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_candidates:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_profile:
                        mViewPager.setCurrentItem(2);
                        break;
                    default:

                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.selectTabAtPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private SwipeFragment mSwipeFragment = SwipeFragment.newInstance();
        private CandidatesFragment mCandidatesFragment = CandidatesFragment.newInstance();
        private ProfileFragment mProfileFragment = ProfileFragment.newInstance();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return mSwipeFragment;
            } else if (position == 1) {
                return mCandidatesFragment;
            } else if (position == 2) {
                return mProfileFragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
