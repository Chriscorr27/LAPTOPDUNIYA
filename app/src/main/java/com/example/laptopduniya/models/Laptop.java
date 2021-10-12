package com.example.laptopduniya.models;

import org.json.JSONArray;

import java.util.ArrayList;

public class Laptop {
    int id;
    private String title,brand,ram_type,ram_capacity,ssd_capacity,hdd_capacity,size,weight;
    ArrayList<String> imgs;
    private Boolean is_ssd,is_hdd;
    private int price_per_day,total_price;

    public Laptop(int id,String title,ArrayList<String> imgs, String brand, String ram_type, String ram_capacity, String ssd_capacity, String hdd_capacity, String size, String weight, Boolean is_ssd, Boolean is_hdd,int total_price,int price_per_day) {
        this.imgs = imgs;
        this.id = id;
        this.title = title;
        this.brand = brand;
        this.ram_type = ram_type;
        this.ram_capacity = ram_capacity;
        this.ssd_capacity = ssd_capacity;
        this.hdd_capacity = hdd_capacity;
        this.size = size;
        this.weight = weight;
        this.is_ssd = is_ssd;
        this.is_hdd = is_hdd;
        this.price_per_day = price_per_day;
        this.total_price = total_price;
    }

    public Laptop() {
    }

    public Laptop(int id, String title, ArrayList<String> imgs, String brand, String ram_type, String ram_capacity, String ssd_capacity, String hdd_capacity, String size, String weight, Boolean ssd_present, Boolean hdd_present, String total_price) {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRam_type() {
        return ram_type;
    }

    public void setRam_type(String ram_type) {
        this.ram_type = ram_type;
    }

    public String getRam_capacity() {
        return ram_capacity;
    }

    public void setRam_capacity(String ram_capacity) {
        this.ram_capacity = ram_capacity;
    }

    public String getSsd_capacity() {
        return ssd_capacity;
    }

    public void setSsd_capacity(String ssd_capacity) {
        this.ssd_capacity = ssd_capacity;
    }

    public String getHdd_capacity() {
        return hdd_capacity;
    }

    public void setHdd_capacity(String hdd_capacity) {
        this.hdd_capacity = hdd_capacity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Boolean getIs_ssd() {
        return is_ssd;
    }

    public void setIs_ssd(Boolean is_ssd) {
        this.is_ssd = is_ssd;
    }

    public Boolean getIs_hdd() {
        return is_hdd;
    }

    public void setIs_hdd(Boolean is_hdd) {
        this.is_hdd = is_hdd;
    }


    public int getPrice_per_day() {
        return price_per_day;
    }

    public void setPrice_per_day(int price_per_day) {
        this.price_per_day = price_per_day;
    }
}
