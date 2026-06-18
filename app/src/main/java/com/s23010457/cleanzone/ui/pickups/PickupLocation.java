package com.s23010457.cleanzone.ui.pickups;

public class PickupLocation {
    private final String name;
    private final double lat;
    private final double lng;
    private final String address;
    private final String schedule;
    private final String type;

    public PickupLocation(String name, double lat, double lng, String address, String schedule, String type) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.schedule = schedule;
        this.type = type;
    }

    public String getName() { return name; }
    public double getLat() { return lat; }
    public double getLng() { return lng; }
    public String getAddress() { return address; }
    public String getSchedule() { return schedule; }
    public String getType() { return type; }
}
