package com.s23010457.cleanzone.ui.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.s23010457.cleanzone.R;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesFragment extends Fragment {

    private ViewPager2 videoViewPager;
    private ImageButton prevButton;
    private ImageButton nextButton;

    public static ActivitiesFragment newInstance() {
        return new ActivitiesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);

        videoViewPager = view.findViewById(R.id.videoViewPager);
        prevButton = view.findViewById(R.id.prevButton);
        nextButton = view.findViewById(R.id.nextButton);

        setupVideoSlider();

        return view;
    }

    private void setupVideoSlider() {
        List<WasteVideo> videos = new ArrayList<>();
        videos.add(new WasteVideo(
                "Introduction to Recycling",
                "Learn the basics of sorting plastic, glass, and paper correctly.",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
        ));
        videos.add(new WasteVideo(
                "Home Composting Guide",
                "Turn organic kitchen waste into rich soil for your home garden.",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"
        ));
        videos.add(new WasteVideo(
                "The 3R Concept",
                "How to Reduce, Reuse, and Recycle to minimize environment impact.",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"
        ));

        VideoPagerAdapter adapter = new VideoPagerAdapter(videos);
        videoViewPager.setAdapter(adapter);

        // Arrow navigation buttons
        prevButton.setOnClickListener(v -> {
            int current = videoViewPager.getCurrentItem();
            if (current > 0) {
                videoViewPager.setCurrentItem(current - 1, true);
            }
        });

        nextButton.setOnClickListener(v -> {
            int current = videoViewPager.getCurrentItem();
            if (current < videos.size() - 1) {
                videoViewPager.setCurrentItem(current + 1, true);
            }
        });

        // Autoplay the selected video and pause others
        videoViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Delay slightly to allow the view to bind
                videoViewPager.post(() -> playVideoAt(position));
            }
        });
    }

    private void playVideoAt(int position) {
        RecyclerView rv = (RecyclerView) videoViewPager.getChildAt(0);
        if (rv == null) return;

        for (int i = 0; i < rv.getChildCount(); i++) {
            View child = rv.getChildAt(i);
            int itemPos = rv.getChildAdapterPosition(child);

            VideoView videoView = child.findViewById(R.id.videoView);
            ImageView playPause = child.findViewById(R.id.imgPlayPause);

            if (videoView != null) {
                if (itemPos == position) {
                    videoView.start();
                    if (playPause != null) playPause.setVisibility(View.GONE);
                } else {
                    videoView.pause();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoViewPager != null) {
            videoViewPager.post(() -> playVideoAt(videoViewPager.getCurrentItem()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoViewPager != null) {
            RecyclerView rv = (RecyclerView) videoViewPager.getChildAt(0);
            if (rv != null) {
                for (int i = 0; i < rv.getChildCount(); i++) {
                    View child = rv.getChildAt(i);
                    VideoView videoView = child.findViewById(R.id.videoView);
                    if (videoView != null && videoView.isPlaying()) {
                        videoView.pause();
                    }
                }
            }
        }
    }
}
