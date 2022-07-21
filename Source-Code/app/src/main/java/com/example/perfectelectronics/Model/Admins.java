package com.example.perfectelectronics.Model;

public class Admins
{
    private String name, phone, password, confirmpassword;

    public Admins()
    {

    }

    public Admins(String name, String phone, String password, String confirmpassword)
    {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.confirmpassword = confirmpassword;
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

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }
}
