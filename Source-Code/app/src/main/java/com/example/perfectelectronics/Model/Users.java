package com.example.perfectelectronics.Model;

public class Users
{


    private String name, phone, password,type;
    private int userid;


    public Users() {
    }
    public Users(String name, String phone, String password, String type, int userid) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.type = type;
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
