package com.example.all.staffapp;

public class Data_List {
    private int id;
    private String fname;
    private String lname;
    private String phone;
    private String gender;
    private String date_Created;
    private String admins_id;
    private String address;
    private String salary;

    public Data_List() {
    }

    public Data_List(int id, String fname, String lname, String phone, String gender, String date_Created, String admins_id, String address, String salary) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.gender = gender;
        this.date_Created = date_Created;
        this.admins_id = admins_id;
        this.address = address;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate_Created() {
        return date_Created;
    }

    public void setDate_Created(String date_Created) {
        this.date_Created = date_Created;
    }

    public String getAdmins_id() {
        return admins_id;
    }

    public void setAdmins_id(String admins_id) {
        this.admins_id = admins_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
