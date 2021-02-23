package com.android.resturant.Model;

public class Food {
    private int id;
    private String name, Ingredients, price, type, image;

    public Food() {
    }

    public Food(int id, String name, String ingredients, String price, String type, String image) {
        this.id = id;
        this.name = name;
        Ingredients = ingredients;
        this.price = price;
        this.type = type;
        this.image = image;
    }

    public Food(String name, String ingredients, String price, String type, String image) {
        this.name = name;
        Ingredients = ingredients;
        this.price = price;
        this.type = type;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
