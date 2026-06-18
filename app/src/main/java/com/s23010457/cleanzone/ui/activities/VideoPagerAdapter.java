package com.s23010457.cleanzone.ui.activities;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s23010457.cleanzone.R;

import java.util.List;

public class VideoPagerAdapter extends RecyclerView.Adapter<VideoPagerAdapter.VideoViewHolder> {

    private final List<WasteVideo> videoList;

    public VideoPagerAdapter(List<WasteVideo> videoList) {
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        WasteVideo video = videoList.get(position);
        holder.txtTitle.setText(video.getTitle());
        holder.txtDescription.setText(video.getDescription());
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.imgPlayPause.setVisibility(View.GONE);

        // Load video URL
        holder.videoView.setVideoURI(Uri.parse(video.getVideoUrl()));

        holder.videoView.setOnPreparedListener(mp -> {
            holder.progressBar.setVisibility(View.GONE);
            mp.setLooping(true);
        });

        holder.itemView.setOnClickListener(v -> {
            if (holder.videoView.isPlaying()) {
                holder.videoView.pause();
                holder.imgPlayPause.setImageResource(android.R.drawable.ic_media_play);
                holder.imgPlayPause.setVisibility(View.VISIBLE);
            } else {
                holder.videoView.start();
                holder.imgPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                holder.imgPlayPause.setVisibility(View.VISIBLE);
                holder.imgPlayPause.postDelayed(() -> holder.imgPlayPause.setVisibility(View.GONE), 1000);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        final VideoView videoView;
        final ProgressBar progressBar;
        final TextView txtTitle;
        final TextView txtDescription;
        final ImageView imgPlayPause;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            progressBar = itemView.findViewById(R.id.progressBar);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            imgPlayPause = itemView.findViewById(R.id.imgPlayPause);
        }
    }
}
