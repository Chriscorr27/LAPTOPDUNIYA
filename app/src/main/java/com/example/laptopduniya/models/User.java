package com.example.laptopduniya.models;

public class User {
    private String email,name;
    private Boolean profile_created;

    public User(String email, String name, Boolean profile_created) {
        this.email = email;
        this.name = name;
        this.profile_created = profile_created;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getProfile_created() {
        return profile_created;
    }

    public void setProfile_created(Boolean profile_created) {
        this.profile_created = profile_created;
    }
}
