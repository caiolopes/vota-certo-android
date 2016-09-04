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

public class AnalysisAdapter extends RecyclerView.Adapter<AnalysisAdapter.ViewHolder> {
    private List<Analysis> mList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.analysis_item, parent, false);
        return new ViewHolder(view);
    }

    public AnalysisAdapter(List<Analysis> mList) {
        this.mList = mList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Analysis analysis = mList.get(position);
        holder.picture.setImageURI(Uri.parse(analysis.getTweet().getPicture()));
        holder.name.setText(analysis.getTweet().getName());
        holder.username.setText("@" + analysis.getTweet().getUsername());
        holder.text.setText(analysis.getTweet().getText());

        setTweetSentiment(analysis, holder);
    }

    @Override
    public int getItemCount() {
        return mList.size();
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
