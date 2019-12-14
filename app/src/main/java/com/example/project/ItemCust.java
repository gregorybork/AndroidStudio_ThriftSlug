package com.example.project;

public class ItemCust {
    private String itemName;
    private String itemType;
    private String itemGender;
    private String itemSize;
    private String itemBrand;
    private String itemColor;
    private String itemCondition;
    private String itemPrice;
    private String itemPhoto;
    private String itemID;
    private String itemSellerEmail;


    public ItemCust(String itemID, String itemName, String itemType, String itemGender, String itemSize, String itemBrand, String itemColor, String itemCondition, String itemPrice, String itemPhoto, String itemSellerEmail) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemGender = itemGender;
        this.itemSize = itemSize;
        this.itemBrand = itemBrand;
        this.itemColor = itemColor;
        this.itemCondition = itemCondition;
        this.itemPrice = itemPrice;
        this.itemPhoto = itemPhoto;
        this.itemSellerEmail = itemSellerEmail;

    }

    public String getItemId() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public String getItemGender() {
        return itemGender;
    }

    public String getItemSize() {
        return itemSize;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public String getItemColor() {
        return itemColor;
    }

    public String getItemCondition() {
        return itemCondition;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemPhoto() {
        return itemPhoto;
    }

    public String getItemSellerEmail() {return itemSellerEmail;}


}
