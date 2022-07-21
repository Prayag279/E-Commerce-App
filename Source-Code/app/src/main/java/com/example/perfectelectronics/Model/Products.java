package com.example.perfectelectronics.Model;

public class Products {
    private int productid,sellerid,quantity;
    private float price;
    private String sellerphone, category, brand, model, imageurl, details,colour,variant,wholemodelname;


    public Products() {
    }

    public Products(int productid, int sellerid, int quantity, float price, String sellerphone, String category, String brand, String model, String imageurl, String details, String colour, String variant, String wholemodelname) {
        this.productid = productid;
        this.sellerid = sellerid;
        this.quantity = quantity;
        this.price = price;
        this.sellerphone = sellerphone;
        this.category = category;
        this.brand = brand;
        this.model = model;
        this.imageurl = imageurl;
        this.details = details;
        this.colour = colour;
        this.variant = variant;
        this.wholemodelname = wholemodelname;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getSellerid() {
        return sellerid;
    }

    public void setSellerid(int sellerid) {
        this.sellerid = sellerid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSellerphone() {
        return sellerphone;
    }

    public void setSellerphone(String sellerphone) {
        this.sellerphone = sellerphone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getWholemodelname() {
        return wholemodelname;
    }

    public void setWholemodelname(String wholemodelname) {
        this.wholemodelname = wholemodelname;
    }
}