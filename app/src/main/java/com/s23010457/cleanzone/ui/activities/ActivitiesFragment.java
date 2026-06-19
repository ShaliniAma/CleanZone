package com.s23010457.cleanzone.ui.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.s23010457.cleanzone.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ActivitiesFragment manages the educational learning activities screen.
 * It shows a YouTube video slider at the top and three clickable cards below
 * redirecting to environmental/composting/recycling articles.
 */
public class ActivitiesFragment extends Fragment {

    // ViewPager2 is used to slide between YouTube videos
    private ViewPager2 videoViewPager;
    // Arrow navigation buttons
    private ImageButton prevButton;
    private ImageButton nextButton;

    public static ActivitiesFragment newInstance() {
        return new ActivitiesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate fragment layout view
        View view = inflater.inflate(R.layout.fragment_activities, container, false);

        // Bind layout views
        videoViewPager = view.findViewById(R.id.videoViewPager);
        prevButton = view.findViewById(R.id.prevButton);
        nextButton = view.findViewById(R.id.nextButton);

        // Configure the YouTube video slider
        setupVideoSlider();

        // Bind the 3 informational cards and set click listeners to navigate to their respective URLs
        CardView cardRecyclingTips = view.findViewById(R.id.cardRecyclingTips);
        CardView cardComposting = view.findViewById(R.id.cardComposting);
        CardView card3RMethod = view.findViewById(R.id.card3RMethod);

        // Click listener for Card 1 (Recycling Tips)
        if (cardRecyclingTips != null) {
            cardRecyclingTips.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.recyclingtoday.org/blogs/news/10-easy-recycling-tips-to-help-save-the-planet-today"));
                startActivity(intent);
            });
        }
        
        // Click listener for Card 2 (Composting Guide)
        if (cardComposting != null) {
            cardComposting.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.epa.gov/recycle/composting-home"));
                startActivity(intent);
            });
        }
        
        // Click listener for Card 3 (3R Method)
        if (card3RMethod != null) {
            card3RMethod.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://travelifestaybetter.com/the-3rs-explained-reduce-reuse-recycle/"));
                startActivity(intent);
            });
        }

        return view;
    }

    /**
     * Initializes the slide list of YouTube videos and binds it to the slider.
     */
    private void setupVideoSlider() {
        List<WasteVideo> videos = new ArrayList<>();
        // Add educational videos
        videos.add(new WasteVideo(
                "Introduction to Recycling",
                "Learn the basics of sorting plastic, glass, and paper correctly.",
                "https://youtu.be/4JDGFNoY-rQ?si=CeQdL7jOzd3qii0D"
        ));
        videos.add(new WasteVideo(
                "Home Composting Guide",
                "Turn organic kitchen waste into rich soil for your home garden.",
                "https://youtu.be/4JDGFNoY-rQ?si=CeQdL7jOzd3qii0D"
        ));
        videos.add(new WasteVideo(
                "Reduce, Reuse, Recycle",
                "How to Reduce, Reuse, and Recycle to minimize environmental impact.",
                "https://youtu.be/4JDGFNoY-rQ?si=CeQdL7jOzd3qii0D"
        ));

        // Bind the list to the adapter and assign it to ViewPager2
        VideoPagerAdapter adapter = new VideoPagerAdapter(videos);
        videoViewPager.setAdapter(adapter);

        // Left slide navigation arrow button click handler
        prevButton.setOnClickListener(v -> {
            int current = videoViewPager.getCurrentItem();
            if (current > 0) {
                videoViewPager.setCurrentItem(current - 1, true);
            }
        });

        // Right slide navigation arrow button click handler
        nextButton.setOnClickListener(v -> {
            int current = videoViewPager.getCurrentItem();
            if (current < videos.size() - 1) {
                videoViewPager.setCurrentItem(current + 1, true);
            }
        });

        // Event listener: pauses previous WebViews when the user slides to a new page to save CPU power
        videoViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                videoViewPager.post(() -> pauseAllExcept(position));
            }
        });
    }

    /**
     * Pauses all WebViews except the one currently active.
     */
    private void pauseAllExcept(int activePosition) {
        RecyclerView rv = (RecyclerView) videoViewPager.getChildAt(0);
        if (rv == null) return;

        // Loop through slides in view hierarchy
        for (int i = 0; i < rv.getChildCount(); i++) {
            View child = rv.getChildAt(i);
            int itemPos = rv.getChildAdapterPosition(child);
            WebView webView = child.findViewById(R.id.webViewVideo);

            if (webView != null) {
                if (itemPos == activePosition) {
                    webView.onResume(); // Resume play
                } else {
                    webView.onPause();  // Pause play
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Pause all running video WebViews when the user leaves this fragment
        if (videoViewPager != null) {
            RecyclerView rv = (RecyclerView) videoViewPager.getChildAt(0);
            if (rv != null) {
                for (int i = 0; i < rv.getChildCount(); i++) {
                    View child = rv.getChildAt(i);
                    WebView webView = child.findViewById(R.id.webViewVideo);
                    if (webView != null) {
                        webView.onPause();
                    }
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Resume play on the active slide when returning back to this fragment screen
        if (videoViewPager != null) {
            videoViewPager.post(() -> pauseAllExcept(videoViewPager.getCurrentItem()));
        }
    }
}
