package com.example.foodapplicationcustomer.support;

public class OrderModel {

    private String productID;
    private String productname;
    private String quantity;
    private String price;
    private String imageLink;



    public OrderModel(){

    }
    public OrderModel(String productID, String productname, String quantity, String price,String imageLink) {
        this.productID = productID;
        this.productname = productname;
        this.quantity = quantity;
        this.price = price;
        this.imageLink = imageLink;
    }



    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }


    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
