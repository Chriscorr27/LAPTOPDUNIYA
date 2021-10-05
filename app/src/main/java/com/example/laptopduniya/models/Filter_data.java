package com.example.laptopduniya.models;

public class Filter_data {
    private String brand,size,ram_type,ram_capacity,ssd_capacity,hdd_capacity,weight;
    private Boolean is_ssd,is_hdd;

    public Filter_data(String brand, String size, String ram_type, String ram_capacity, String ssd_capacity, String hdd_capacity, String weight, Boolean is_ssd, Boolean is_hdd) {
        this.brand = brand;
        this.size = size;
        this.ram_type = ram_type;
        this.ram_capacity = ram_capacity;
        this.ssd_capacity = ssd_capacity;
        this.hdd_capacity = hdd_capacity;
        this.weight = weight;
        this.is_ssd = is_ssd;
        this.is_hdd = is_hdd;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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
}
