package com.example.perfectelectronics.Model;

public class Orders {
    String  model,colour,variant,wholemodelname,address,method;
    int orderid,quantity,sellerid,productid;
    float price;


    public Orders() {
    }

    public Orders(String model, String colour, String variant, String wholemodelname, String address, String method, int orderid, int quantity, int sellerid, int productid, float price) {
        this.model = model;
        this.colour = colour;
        this.variant = variant;
        this.wholemodelname = wholemodelname;
        this.address = address;
        this.method = method;
        this.orderid = orderid;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
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
