package com.example.foodapplicationcustomer;

public class FoodCategory {
    private String Name;
     private String Image;

    public FoodCategory(){

    }

    public FoodCategory(String name, String image) {
        Name = name;
        Image = image;
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

    public int getRes(){
        return R.drawable.ic_baseline_fastfood_24;
    }
}
