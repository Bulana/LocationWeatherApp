package com.bulana.locationsection;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "location_table")
public class Location {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String city;

    private String longitude;

    private String latitude;

    private int position;

    public Location(String city, String longitude,String latitude, int position) {
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.position = position;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public int getPosition() {
        return position;
    }
}
