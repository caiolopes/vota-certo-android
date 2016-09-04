package com.votacerto.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.votacerto.model.User;

import java.util.Map;

public class Helper {
    /**
     * Shows the progress UI and hides the login form.
     */
    public static void showProgress(Context context, final boolean show, final View hideView, final View progressView) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = context.getResources().getInteger(android.R.integer.config_shortAnimTime);

        hideView.setVisibility(show ? View.GONE : View.VISIBLE);
        hideView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                hideView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    public static User getUser(Map<String, ?> map) {
        Log.v("Helper", "Map size: " + map.size());
        if (map.size() == 0)
            return null;

        User user = new User();
        if (map.get("ID") instanceof Integer)
            user.setId((Integer)map.get("ID"));
        if (map.get("NAME") instanceof String)
            user.setName((String)map.get("NAME"));
        if (map.get("EMAIL") instanceof String)
            user.setEmail((String)map.get("EMAIL"));
        if (map.get("PICTURE") instanceof String)
            user.setPicture((String)map.get("PICTURE"));
        if (map.get("FACEBOOK_ID") instanceof String)
            user.setFacebookId((String)map.get("FACEBOOK_ID"));
        if (map.get("CREATED_AT") instanceof String)
            user.setCreatedAt((String)map.get("CREATED_AT"));
        if (map.get("UPDATED_AT") instanceof String)
            user.setUpdatedAt((String)map.get("UPDATED_AT"));
        if (map.get("ACCESS_TOKEN") instanceof String)
            user.setAccessToken((String)map.get("ACCESS_TOKEN"));

        return user;
    }

    public static void putUser(SharedPreferences pref, User user) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("ID", user.getId());
        editor.putString("NAME", user.getName());
        editor.putString("EMAIL", user.getEmail());
        editor.putString("PICTURE", user.getPicture());
        editor.putString("FACEBOOK_ID", user.getFacebookId());
        editor.putString("CREATED_AT", user.getCreatedAt());
        editor.putString("UPDATED_AT", user.getUpdatedAt());
        editor.putString("ACCESS_TOKEN", user.getAccessToken());
        editor.apply();
    }
}
