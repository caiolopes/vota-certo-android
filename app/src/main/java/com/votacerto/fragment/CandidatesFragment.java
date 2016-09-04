package com.votacerto.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.votacerto.R;

public class CandidatesFragment extends Fragment {
    private View mView;

    public static CandidatesFragment newInstance() {

        Bundle args = new Bundle();

        CandidatesFragment fragment = new CandidatesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_candidates_list, container, false);
        return mView;
    }
}