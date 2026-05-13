package com.example.foodfight;

public class MenuItemMode1 {
    private final String name;
    private final String price;
    private final int imageResId;

    public MenuItemMode1(String name, String price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    //all good

    public String getName() { return name; }
    public String getPrice() { return price; }
    public int getImageResId() { return imageResId; }
}
