package com.example.laptopduniya.models;

import com.google.gson.annotations.SerializedName;

public class StudentVerify {
    @SerializedName("email")
    private String email;
    @SerializedName("id_number")
    String id_number;
    @SerializedName("id_pic")
    String id_pic;
    @SerializedName("is_verified")
    private Boolean is_verified;

    public StudentVerify(String email, String id_number, String id_pic, Boolean is_verified) {
        this.email = email;
        this.id_number = id_number;
        this.id_pic = id_pic;
        this.is_verified = is_verified;
    }

    public StudentVerify() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getId_pic() {
        return id_pic;
    }

    public void setId_pic(String id_pic) {
        this.id_pic = id_pic;
    }

    public Boolean getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(Boolean is_verified) {
        this.is_verified = is_verified;
    }
}
