package com.android.resturant.Model;

public class Cart {
    private int id;
    private String name, Ingredients, price, type, image, user_id;

    public Cart() {
    }

    public Cart(int id, String name, String ingredients, String price, String type, String image, String user_id) {
        this.id = id;
        this.name = name;
        this.Ingredients = ingredients;
        this.price = price;
        this.type = type;
        this.image = image;
        this.user_id = user_id;
    }

    public Cart(String name, String ingredients, String price, String type, String image, String user_id) {
        this.name = name;
        this.Ingredients = ingredients;
        this.price = price;
        this.type = type;
        this.image = image;
        this.user_id = user_id;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
