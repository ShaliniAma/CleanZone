package com.s23010457.cleanzone.ui.activities;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s23010457.cleanzone.R;

import java.util.List;

/**
 * VideoPagerAdapter binds the list of educational videos to the slider.
 * It configures WebViews to embed and play YouTube videos dynamically in the slider cells.
 */
public class VideoPagerAdapter extends RecyclerView.Adapter<VideoPagerAdapter.VideoViewHolder> {

    // List containing titles, descriptions, and URLs for videos
    private final List<WasteVideo> videoList;

    public VideoPagerAdapter(List<WasteVideo> videoList) {
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the cell layout for a single video slide
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        WasteVideo video = videoList.get(position);
        
        // Bind text data
        holder.txtTitle.setText(video.getTitle());
        holder.txtDescription.setText(video.getDescription());

        // Configure WebView settings to allow JavaScript playback for YouTube embeds
        WebSettings webSettings = holder.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setDomStorageEnabled(true);

        // Assign standard web clients for loading web pages inside the app frame
        holder.webView.setWebChromeClient(new WebChromeClient());
        holder.webView.setWebViewClient(new WebViewClient());

        // Construct a safe, compatible YouTube embed URL using the video parser helper
        String embedUrl = extractYoutubeEmbedUrl(video.getVideoUrl());
        
        // Generate light HTML wrapper string containing full-page iframe for YouTube video
        String html = "<!DOCTYPE html>" +
                "<html><head>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0'>" +
                "<style>*{margin:0;padding:0;overflow:hidden;background:#000}" +
                "iframe{width:100%;height:100%;border:none;position:absolute;top:0;left:0}</style>" +
                "</head><body>" +
                "<iframe src='" + embedUrl + "?rel=0&showinfo=0&modestbranding=1'" +
                " frameborder='0' allowfullscreen allow='autoplay; encrypted-media'></iframe>" +
                "</body></html>";

        // Load the HTML content directly into the slide's WebView
        holder.webView.loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "UTF-8", null);
    }

    /**
     * Helper method: extracts YouTube video ID from share link / watch link
     * and converts it to a standard iframe-embeddable format.
     */
    private String extractYoutubeEmbedUrl(String url) {
        if (url == null) return "";
        String videoId = "";
        
        // Parse YouTube short share links (e.g. youtu.be/...)
        if (url.contains("youtu.be/")) {
            int index = url.indexOf("youtu.be/");
            videoId = url.substring(index + 9);
            if (videoId.contains("?")) {
                videoId = videoId.substring(0, videoId.indexOf("?"));
            }
        } 
        // Parse YouTube watch URLs (e.g. youtube.com/watch?v=...)
        else if (url.contains("youtube.com/watch?v=")) {
            int index = url.indexOf("watch?v=");
            videoId = url.substring(index + 8);
            if (videoId.contains("&")) {
                videoId = videoId.substring(0, videoId.indexOf("&"));
            }
        } 
        // If already formatted as embed, return direct URL
        else if (url.contains("youtube.com/embed/")) {
            return url;
        } 
        // Default fallback if format is unrecognized
        else {
            return url;
        }
        
        return "https://www.youtube.com/embed/" + videoId;
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    // ViewHolder class caches view layouts to optimize slider scrolling performance
    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        final WebView webView;
        final TextView txtTitle;
        final TextView txtDescription;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.webViewVideo);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }
}
