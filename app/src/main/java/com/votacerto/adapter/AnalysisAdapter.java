package com.votacerto.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.votacerto.R;
import com.votacerto.model.Analysis;

import java.util.List;

public class AnalysisAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final Integer HEADER = 0;
    private static final Integer ITEM = 1;
    private final String mBio;
    private List<Analysis> mList;

    @Override
    public int getItemViewType(int position) {
        return position == HEADER ? HEADER : ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_header, parent, false);
            return new BioHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.analysis_item, parent, false);
            return new ViewHolder(view);
        }
    }

    public static class BioHolder extends RecyclerView.ViewHolder {
        TextView bio;

        public BioHolder(View itemView)  {
            super(itemView);
            bio = (TextView) itemView.findViewById(R.id.candidate_bio);
        }
    }

    public AnalysisAdapter(List<Analysis> mList, String bio) {
        this.mList = mList;
        this.mBio = bio;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
        if (vh instanceof AnalysisAdapter.ViewHolder) {
            ViewHolder holder = (ViewHolder) vh;
            final Analysis analysis = mList.get(position);
            holder.picture.setImageURI(Uri.parse(analysis.getTweet().getPicture()));
            holder.name.setText(analysis.getTweet().getName());
            holder.username.setText("@" + analysis.getTweet().getUsername());
            holder.text.setText(analysis.getTweet().getText());
            setTweetSentiment(analysis, holder);
        } else {
            BioHolder holder = (BioHolder) vh;
            holder.bio.setText(mBio);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size()-1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView username;
        TextView text;
        ImageView likeDislike;
        SimpleDraweeView picture;

        public ViewHolder(View itemView)  {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tweet_author);
            username = (TextView) itemView.findViewById(R.id.username);
            text = (TextView) itemView.findViewById(R.id.tweet_text);
            picture = (SimpleDraweeView) itemView.findViewById(R.id.tweet_pic);
            likeDislike = (ImageView) itemView.findViewById(R.id.like_dislike);
        }
    }

    private void setTweetSentiment(Analysis analysis, ViewHolder holder) {
        if (analysis.getSentiment().equals("positive")) {
            holder.likeDislike.setImageResource(R.drawable.ic_check_white_24dp);
            holder.likeDislike.setBackgroundResource(R.drawable.circle_button_green);
        } else {
            holder.likeDislike.setImageResource(R.drawable.ic_close_white_24dp);
            holder.likeDislike.setBackgroundResource(R.drawable.circle_button_red);
        }
    }
}
