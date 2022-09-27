package com.example.foodapplicationcustomer;

public class FoodItem {

    private String Name;
    private String Image;
    private String Description;
    private String Price;
    private String MenuID;


    public FoodItem(){

    }

    public FoodItem(String name, String image, String description, String price, String menuID) {
        Name = name;
        Image = image;
        Description = description;
        Price = price;
        MenuID = menuID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }

    public int getRes(){
        return R.drawable.ic_baseline_fastfood_24;
    }
}
