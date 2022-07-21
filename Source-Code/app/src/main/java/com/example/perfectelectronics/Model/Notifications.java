package com.example.perfectelectronics.Model;

public class Notifications {
    int Notification_ID,Seller_ID;
    String Seller_Phone,Category,Brand,Model,Details;

    public Notifications() {
    }

    public Notifications(int notification_ID, int seller_ID, String seller_Phone, String category, String brand, String model, String details) {
        Notification_ID = notification_ID;
        Seller_ID = seller_ID;
        Seller_Phone = seller_Phone;
        Category = category;
        Brand = brand;
        Model = model;
        Details = details;
    }

    public int getNotification_ID() {
        return Notification_ID;
    }

    public void setNotification_ID(int notification_ID) {
        Notification_ID = notification_ID;
    }

    public int getSeller_ID() {
        return Seller_ID;
    }

    public void setSeller_ID(int seller_ID) {
        Seller_ID = seller_ID;
    }

    public String getSeller_Phone() {
        return Seller_Phone;
    }

    public void setSeller_Phone(String seller_Phone) {
        Seller_Phone = seller_Phone;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }
}
