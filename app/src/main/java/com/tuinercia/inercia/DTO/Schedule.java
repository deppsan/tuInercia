package com.tuinercia.inercia.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ricar on 14/12/2017.
 */

public class Schedule {
    @SerializedName("id_agenda")
    int id;
    @SerializedName("date")
    String date;
    @SerializedName("time")
    String time;
    @SerializedName("datetime")
    String datetime;
    @SerializedName("id_clase")
    int training_id;
    @SerializedName("instructor_id")
    int instructor_id;
    @SerializedName("inst_name")
    String inst_name;
    @SerializedName("name")
    String name;
    boolean isSelected;
    @SerializedName("description")
    String description;

    public Schedule(int id, String date, String time, String datetime, int training_id, int instructor_id, String inst_name, String name,String description) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.datetime = datetime;
        this.training_id = training_id;
        this.instructor_id = instructor_id;
        this.inst_name = inst_name;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getTraining_id() {
        return training_id;
    }

    public void setTraining_id(int training_id) {
        this.training_id = training_id;
    }

    public int getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(int instructor_id) {
        this.instructor_id = instructor_id;
    }

    public String getInst_name() {
        return inst_name;
    }

    public void setInst_name(String inst_name) {
        this.inst_name = inst_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}