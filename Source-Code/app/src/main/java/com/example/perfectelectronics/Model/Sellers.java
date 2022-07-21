package com.example.perfectelectronics.Model;

public class Sellers {
    private  String name,password,phone,company_shope_name,type;
    private  int sellerid;



    public Sellers() {
    }

    public Sellers(String name, String password, String phone, String company_shope_name, String type, int sellerid) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.company_shope_name = company_shope_name;
        this.type = type;
        this.sellerid = sellerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany_shope_name() {
        return company_shope_name;
    }

    public void setCompany_shope_name(String company_shope_name) {
        this.company_shope_name = company_shope_name;
    }

    public int getSellerid() {
        return sellerid;
    }

    public void setSellerid(int sellerid) {
        this.sellerid = sellerid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
