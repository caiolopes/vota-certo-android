package com.votacerto.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.drawee.view.SimpleDraweeView;
import com.votacerto.LoginActivity;
import com.votacerto.MainActivity;
import com.votacerto.MyApplication;
import com.votacerto.R;
import com.votacerto.model.User;

public class ProfileFragment extends Fragment {
    private View mView;
    private SharedPreferences pref;
    private Activity activity;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User user = ((MainActivity)getActivity()).user;
        pref = getActivity().getSharedPreferences(MyApplication.PREF, Context.MODE_PRIVATE);
        activity = getActivity();
        ((TextView)mView.findViewById(R.id.name)).setText(user.getName());
        ((TextView)mView.findViewById(R.id.email)).setText(user.getEmail());
        ((SimpleDraweeView)mView.findViewById(R.id.picture)).setImageURI(user.getPicture());
        new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                if (accessToken2 == null) {
                    pref.edit().clear().apply();
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                    activity.finish();
                }
            }
        };
    }
}
