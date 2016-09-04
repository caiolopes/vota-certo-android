package com.votacerto.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.votacerto.CandidateActivity;
import com.votacerto.R;
import com.votacerto.model.Candidate;

import java.util.List;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.ViewHolder> {
    private final Context mContext;
    private List<Candidate> mList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_item, parent, false);
        return new ViewHolder(view);
    }

    public CandidateAdapter(Context context, List<Candidate> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Candidate candidate = mList.get(position);
        holder.picture.setImageURI(Uri.parse(candidate.getPolitician().getPicture()));
        holder.name.setText(candidate.getPolitician().getName());
        holder.party.setText(candidate.getPolitician().getParty().getInitials());
        holder.positive.setProgress(candidate.getPositive());
        holder.negative.setProgress(candidate.getNegative());
        holder.positive.setMax(candidate.getPositive()+candidate.getNegative());
        holder.negative.setMax(candidate.getPositive()+candidate.getNegative());

        holder.positive.setProgressText(String.valueOf(candidate.getPositive()));
        holder.negative.setProgressText(String.valueOf(candidate.getNegative()));

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(mContext, CandidateActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(CandidateActivity.CANDIDATE, new Gson().toJson(candidate));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView party;
        SimpleDraweeView picture;
        TextRoundCornerProgressBar positive;
        TextRoundCornerProgressBar negative;
        ItemClickListener itemClickListener;

        public ViewHolder(View itemView)  {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            party = (TextView) itemView.findViewById(R.id.party);
            picture = (SimpleDraweeView) itemView.findViewById(R.id.candidate_pic);
            positive = (TextRoundCornerProgressBar) itemView.findViewById(R.id.positive);
            negative = (TextRoundCornerProgressBar) itemView.findViewById(R.id.negative);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getLayoutPosition(), false);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
