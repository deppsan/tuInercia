package com.tuinercia.inercia.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ricar on 02/11/2017.
 */

public class Parlor {

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("address")
    String address;
    @SerializedName("studio_id")
    int studio_id;
    @SerializedName("description")
    String description;
    @SerializedName("contact")
    String contact;
    @SerializedName("coord_x")
    String coord_x;
    @SerializedName("coord_y")
    String coord_y;

    public Parlor(int id, String name, String address, int studio_id, String description, String contact, String coord_x, String coord_y) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.studio_id = studio_id;
        this.description = description;
        this.contact = contact;
        this.coord_x = coord_x;
        this.coord_y = coord_y;
    }

    public Parlor() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStudio_id() {
        return studio_id;
    }

    public void setStudio_id(int studio_id) {
        this.studio_id = studio_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCoord_x() {
        return coord_x;
    }

    public void setCoord_x(String coord_x) {
        this.coord_x = coord_x;
    }

    public String getCoord_y() {
        return coord_y;
    }

    public void setCoord_y(String coord_y) {
        this.coord_y = coord_y;
    }
}

