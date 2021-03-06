package com.example.laptopduniya.models;

public class Order {
    private String start_date,end_date,total_amount,status,lat,lon,total_amount_dis;
    Boolean is_student;
    private Laptop laptop;

    public Order(String start_date, String end_date, String total_amount, String status, String lat, String lon, Laptop laptop,String total_amount_dis,Boolean is_student) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.total_amount = total_amount;
        this.status = status;
        this.lat = lat;
        this.lon = lon;
        this.laptop = laptop;
        this.total_amount_dis = total_amount_dis;
        this.is_student = is_student;
    }

    public Order() {
    }

    public String getTotal_amount_dis() {
        return total_amount_dis;
    }

    public void setTotal_amount_dis(String total_amount_dis) {
        this.total_amount_dis = total_amount_dis;
    }

    public Boolean getIs_student() {
        return is_student;
    }

    public void setIs_student(Boolean is_student) {
        this.is_student = is_student;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }
}
