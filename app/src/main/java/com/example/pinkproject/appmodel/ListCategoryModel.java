package com.example.pinkproject.appmodel;

public class ListCategoryModel {
    String imageUrl,name;

    public ListCategoryModel(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public ListCategoryModel() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}