package com.votacerto.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.votacerto.R;
import com.votacerto.model.Candidate;

import java.util.List;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.ViewHolder> {
    private List<Candidate> mList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_item, parent, false);
        return new ViewHolder(view);
    }

    public CandidateAdapter(List<Candidate> mList) {
        this.mList = mList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Candidate candidate = mList.get(position);
        holder.name.setText(candidate.getPolitician().getName());
        holder.picture.setImageURI(Uri.parse(candidate.getPolitician().getPicture()));
        holder.name.setText(candidate.getPolitician().getName());
        holder.positive.setProgress(candidate.getPositive());
        holder.negative.setProgress(candidate.getNegative());
        holder.positive.setMax(candidate.getPositive()+candidate.getNegative());
        holder.negative.setMax(candidate.getPositive()+candidate.getNegative());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        SimpleDraweeView picture;
        RoundCornerProgressBar positive;
        RoundCornerProgressBar negative;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            picture = (SimpleDraweeView) itemView.findViewById(R.id.candidate_pic);
            positive = (RoundCornerProgressBar) itemView.findViewById(R.id.positive);
            negative = (RoundCornerProgressBar) itemView.findViewById(R.id.negative);
        }
    }
}
