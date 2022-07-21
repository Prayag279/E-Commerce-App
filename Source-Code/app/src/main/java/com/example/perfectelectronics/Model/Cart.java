package com.example.perfectelectronics.Model;

public class Cart {
    String  model,colour,variant,wholemodelname;
    int cartid,quantity,sellerid,productid;
    float price;


    public Cart() {
    }

    public Cart(String model, String colour, String variant, String wholemodelname, int cartid, int quantity, int sellerid, int productid, float price) {
        this.model = model;
        this.colour = colour;
        this.variant = variant;
        this.wholemodelname = wholemodelname;
        this.cartid = cartid;
        this.quantity = quantity;
        this.sellerid = sellerid;
        this.productid = productid;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSellerid() {
        return sellerid;
    }

    public void setSellerid(int sellerid) {
        this.sellerid = sellerid;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
