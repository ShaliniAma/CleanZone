package com.s23010457.cleanzone.ui.activities;

public class WasteVideo {
    private final String title;
    private final String description;
    private final String videoUrl;

    public WasteVideo(String title, String description, String videoUrl) {
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
