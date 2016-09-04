package com.votacerto.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.votacerto.MainActivity;
import com.votacerto.R;
import com.votacerto.adapter.CandidateAdapter;
import com.votacerto.model.Candidate;
import com.votacerto.network.Api;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CandidatesFragment extends Fragment {
    private View mView;
    private ProgressBar progressBar;
    private CandidateAdapter adapter;
    private List<Candidate> candidatesList;
    private Subscription subscription = null;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ProgressBar) mView.findViewById(R.id.progress);
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.candidates_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        candidatesList = new ArrayList<>();
        adapter = new CandidateAdapter(candidatesList);
        recyclerView.setAdapter(adapter);

        subscription = Api.getInstance().getCandidates(((MainActivity)getActivity()).user.getAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Candidate>>() {
                    @Override
                    public void onCompleted() {
                        Integer maxPositive = 0;
                        Integer maxNegative = 0;
                        for (Candidate candidate : candidatesList) {
                            if (candidate.getPositive() > maxPositive) maxPositive = candidate.getPositive();
                            if (candidate.getNegative() > maxNegative) maxNegative = candidate.getNegative();
                        }
                        adapter.setMaxPositive(maxPositive);
                        adapter.setMaxNegative(maxNegative);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Candidate> candidates) {
                        candidatesList.clear();
                        candidatesList.addAll(candidates);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        if (subscription != null)
            subscription.unsubscribe();
        super.onDestroyView();
    }
}