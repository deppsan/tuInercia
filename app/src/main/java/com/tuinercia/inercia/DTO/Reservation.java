package com.tuinercia.inercia.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ricar on 17/01/2018.
 */

public class Reservation {

    @SerializedName("id")
    int id;
    @SerializedName("reservation_id")
    int reservation_id;
    @SerializedName("clase")
    String clase;
    @SerializedName("date")
    String date;
    @SerializedName("time")
    String time;

    @SerializedName("name")
    String name;
    @SerializedName("no_cancel")
    boolean no_cancel;


    public Reservation(int id, int reservation_id, String clase, String date, String time, boolean no_cancel, String name) {
        this.id = id;
        this.reservation_id = reservation_id;
        this.clase = clase;
        this.date = date;
        this.time = time;
        this.no_cancel = no_cancel;
        this.name = name;
    }

    public boolean isNo_cancel() {
        return no_cancel;
    }

    public void setNo_cancel(boolean no_cancel) {
        this.no_cancel = no_cancel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
