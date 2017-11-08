package com.tuinercia.inercia.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ricar on 24/10/2017.
 */

public class User {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("password_digest")
    String password_digest;
    @SerializedName("user_type")
    String user_type;
    @SerializedName("birthdate")
    String birthdate;
    @SerializedName("sex")
    String sex;
    @SerializedName("admin")
    String admin;
    @SerializedName("postmaster")
    String postmaster;

    public User(int id, String name, String email, String password_digest, String user_type, String birthdate, String sex, String admin, String postmaster) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password_digest = password_digest;
        this.user_type = user_type;
        this.birthdate = birthdate;
        this.sex = sex;
        this.admin = admin;
        this.postmaster = postmaster;
    }

    public User() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_digest() {
        return password_digest;
    }

    public void setPassword_digest(String password_digest) {
        this.password_digest = password_digest;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getPostmaster() {
        return postmaster;
    }

    public void setPostmaster(String postmaster) {
        this.postmaster = postmaster;
    }
}

