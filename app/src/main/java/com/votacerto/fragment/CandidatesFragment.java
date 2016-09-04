package com.votacerto.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.votacerto.MainActivity;
import com.votacerto.R;
import com.votacerto.adapter.CandidateAdapter;
import com.votacerto.model.Candidate;
import com.votacerto.network.Api;
import com.votacerto.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CandidatesFragment extends Fragment {
    private ProgressBar progressBar;
    private CandidateAdapter adapter;
    private List<Candidate> candidatesList;
    private Subscription subscription = null;
    private TextView noCandidatesMsgView;
    private SwipeRefreshLayout swipeContainer;

    public static CandidatesFragment newInstance() {

        Bundle args = new Bundle();

        CandidatesFragment fragment = new CandidatesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_candidates_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCandidates();
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        noCandidatesMsgView = (TextView) view.findViewById(R.id.no_candidates_message);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.candidates_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        candidatesList = new ArrayList<>();
        adapter = new CandidateAdapter(getContext(), candidatesList);
        recyclerView.setAdapter(adapter);
        getCandidates();
    }

    private void getCandidates() {
        subscription = Api.getInstance().getCandidates(((MainActivity)getActivity()).user.getAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Candidate>>() {
                    @Override
                    public void onCompleted() {
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        swipeContainer.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        swipeContainer.setRefreshing(false);
                        noCandidatesMsgView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(List<Candidate> candidates) {
                        progressBar.setVisibility(View.GONE);
                        candidatesList.clear();
                        if (candidates.size() == 0) {
                            noCandidatesMsgView.setVisibility(View.VISIBLE);
                        } else {
                            noCandidatesMsgView.setVisibility(View.GONE);
                        }
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