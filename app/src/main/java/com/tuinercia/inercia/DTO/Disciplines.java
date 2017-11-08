package com.tuinercia.inercia.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ricar on 26/10/2017.
 */

public class Disciplines {

    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("pic_file_name")
    String pic_file_name;
    @SerializedName("denomination")
    String denomination;
    @SerializedName("description")
    String description;
    @SerializedName("pic_content_type")
    String pic_content_type;

    public Disciplines(String id, String name, String pic_file_name, String denomination, String description, String pic_content_type) {
        this.id = id;
        this.name = name;
        this.pic_file_name = pic_file_name;
        this.denomination = denomination;
        this.description = description;
        this.pic_content_type = pic_content_type;
    }

    public Disciplines() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic_file_name() {
        return pic_file_name;
    }

    public void setPic_file_name(String pic_file_name) {
        this.pic_file_name = pic_file_name;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic_content_type() {
        return pic_content_type;
    }

    public void setPic_content_type(String pic_content_type) {
        this.pic_content_type = pic_content_type;
    }
}
