package com.tuinercia.inercia.DTO;

import com.google.gson.annotations.SerializedName;

public class History {

    @SerializedName("name")
    String name;
    @SerializedName("sucursal")
    String sucursal;
    @SerializedName("schedules")
    Schedule[] schedules;

    public History(String name, String sucursal, Schedule[] schedules) {
        this.name = name;
        this.sucursal = sucursal;
        this.schedules = schedules;
    }

    public void setSchedules(Schedule[] schedules) {
        this.schedules = schedules;
    }

    public Schedule[] getSchedules() {
        return schedules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

}
