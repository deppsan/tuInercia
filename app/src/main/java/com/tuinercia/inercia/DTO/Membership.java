package com.tuinercia.inercia.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ricar on 28/03/2018.
 */

public class Membership {

    @SerializedName("name")
    String name;
    @SerializedName("amount")
    double amount;
    @SerializedName("cnktplan_id")
    String cnktplan_id;
    @SerializedName("num_classes")
    int num_classes;

    public Membership(String name, double amount, String cnktplan_id, int num_classes) {
        this.name = name;
        this.amount = amount;
        this.cnktplan_id = cnktplan_id;
        this.num_classes = num_classes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCnktplan_id() {
        return cnktplan_id;
    }

    public void setCnktplan_id(String cnktplan_id) {
        this.cnktplan_id = cnktplan_id;
    }

    public int getNum_classes() {
        return num_classes;
    }

    public void setNum_classes(int num_classes) {
        this.num_classes = num_classes;
    }
}
