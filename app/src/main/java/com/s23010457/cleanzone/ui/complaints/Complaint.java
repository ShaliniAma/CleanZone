package com.s23010457.cleanzone.ui.complaints;

public class Complaint {
    private final String title;
    private final String category;
    private final String description;
    private final String location;

    public Complaint(String title, String category, String description, String location) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }
}
