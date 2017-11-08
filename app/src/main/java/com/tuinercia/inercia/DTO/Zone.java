package com.tuinercia.inercia.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ricar on 06/11/2017.
 */

public class Zone {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("updated_at")
    String updated_at;
    @SerializedName("lat")
    String lat;
    @SerializedName("long")
    String lng;

    public Zone(int id, String name, String created_at, String updated_at, String lat, String lng) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.lat = lat;
        this.lng = lng;
    }

    public Zone() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
