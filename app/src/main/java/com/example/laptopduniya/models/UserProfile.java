package com.example.laptopduniya.models;

public class UserProfile {
    private String phoneNumber,profilePic,gender,email,dob,idcard_number,idcard_pic;
    private Boolean isStudent,isStudentVerified;

    public UserProfile(String phoneNumber, String profilePic, String gender, String email, String dob, String idcard_number, String idcard_pic, Boolean isStudent, Boolean isStudentVerified) {
        this.phoneNumber = phoneNumber;
        this.profilePic = profilePic;
        this.gender = gender;
        this.email = email;
        this.dob = dob;
        this.idcard_number = idcard_number;
        this.idcard_pic = idcard_pic;
        this.isStudent = isStudent;
        this.isStudentVerified = isStudentVerified;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getIdcard_number() {
        return idcard_number;
    }

    public void setIdcard_number(String idcard_number) {
        this.idcard_number = idcard_number;
    }

    public String getIdcard_pic() {
        return idcard_pic;
    }

    public void setIdcard_pic(String idcard_pic) {
        this.idcard_pic = idcard_pic;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Boolean getStudent() {
        return isStudent;
    }

    public void setStudent(Boolean student) {
        isStudent = student;
    }

    public Boolean getStudentVerified() {
        return isStudentVerified;
    }

    public void setStudentVerified(Boolean studentVerified) {
        isStudentVerified = studentVerified;
    }

    public UserProfile() {
    }
}
