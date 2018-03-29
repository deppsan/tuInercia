package com.tuinercia.inercia.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ricar on 28/03/2018.
 */

public class MembershipProgress {
    @SerializedName("period_starts")
    String period_starts;
    @SerializedName("period_ends")
    String period_ends;
    @SerializedName("period_total")
    int period_total;
    @SerializedName("period_current")
    int period_current;
    @SerializedName("plan_id")
    int plan_id;

    public MembershipProgress(String period_starts, String period_ends, int period_total, int period_current, int plan_id) {
        this.period_starts = period_starts;
        this.period_ends = period_ends;
        this.period_total = period_total;
        this.period_current = period_current;
        this.plan_id = plan_id;
    }

    public String getPeriod_starts() {
        return period_starts;
    }

    public void setPeriod_starts(String period_starts) {
        this.period_starts = period_starts;
    }

    public String getPeriod_ends() {
        return period_ends;
    }

    public void setPeriod_ends(String period_ends) {
        this.period_ends = period_ends;
    }

    public int getPeriod_total() {
        return period_total;
    }

    public void setPeriod_total(int period_total) {
        this.period_total = period_total;
    }

    public int getPeriod_current() {
        return period_current;
    }

    public void setPeriod_current(int period_current) {
        this.period_current = period_current;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }
}
