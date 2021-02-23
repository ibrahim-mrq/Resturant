package com.android.resturant.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.resturant.Model.Cart;
import com.android.resturant.Model.Food;
import com.android.resturant.Model.User;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;

    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new Database(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = this.openHelper.getWritableDatabase();
    }

    public void close() {
        if (this.database != null) {
            this.database.close();
        }
    }

    /// TODO : >>> Menu

    public boolean insertMenu(Food model) {
        ContentValues cv = new ContentValues();
        cv.put(Database.MENU_COLUMN_NAME, model.getName());
        cv.put(Database.MENU_COLUMN_INGREDIENTS, model.getIngredients());
        cv.put(Database.MENU_COLUMN_PRICE, model.getPrice());
        cv.put(Database.MENU_COLUMN_TYPE, model.getType());
        cv.put(Database.MENU_COLUMN_IMAGE, model.getImage());
        long result = database.insert(Database.MENU_TB_NAME, null, cv);
        return result != -1;
    }

    public boolean updateMenu(Food model) {
        ContentValues values = new ContentValues();
        values.put(Database.MENU_COLUMN_NAME, model.getName());
        values.put(Database.MENU_COLUMN_INGREDIENTS, model.getIngredients());
        values.put(Database.MENU_COLUMN_PRICE, model.getPrice());
        values.put(Database.MENU_COLUMN_TYPE, model.getType());
        values.put(Database.MENU_COLUMN_IMAGE, model.getImage());
        String args[] = {String.valueOf(model.getId())};
        int res = database.update(Database.MENU_TB_NAME, values, "id=?", args);
        return res > 0;
    }

    public long getMenuCount() {
        return DatabaseUtils.queryNumEntries(database, Database.MENU_TB_NAME);
    }

    public boolean deleteMenu(Food model) {
        String args[] = {String.valueOf(model.getId())};
        int res = database.delete(Database.MENU_TB_NAME, "id=?", args);
        return res > 0;
    }

    public ArrayList<Food> getAllMenu() {
        ArrayList<Food> models = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM " + Database.MENU_TB_NAME, null);
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(Database.MENU_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(Database.MENU_COLUMN_NAME));
                String ingredients = c.getString(c.getColumnIndex(Database.MENU_COLUMN_INGREDIENTS));
                String price = c.getString(c.getColumnIndex(Database.MENU_COLUMN_PRICE));
                String type = c.getString(c.getColumnIndex(Database.MENU_COLUMN_TYPE));
                String image = c.getString(c.getColumnIndex(Database.MENU_COLUMN_IMAGE));
                Food model = new Food(id, name, ingredients, price, type, image);
                models.add(model);
            } while (c.moveToNext());
            c.close();
        }
        return models;
    }

    public Food getMenu(int id) {
        Cursor c = database.rawQuery("SELECT * FROM " + Database.MENU_TB_NAME + " WHERE " + Database.MENU_COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        if (c != null && c.moveToFirst()) {
            int ids = c.getInt(c.getColumnIndex(Database.MENU_COLUMN_ID));
            String name = c.getString(c.getColumnIndex(Database.MENU_COLUMN_NAME));
            String ingredients = c.getString(c.getColumnIndex(Database.MENU_COLUMN_INGREDIENTS));
            String price = c.getString(c.getColumnIndex(Database.MENU_COLUMN_PRICE));
            String type = c.getString(c.getColumnIndex(Database.MENU_COLUMN_TYPE));
            String image = c.getString(c.getColumnIndex(Database.MENU_COLUMN_IMAGE));
            Food model = new Food(ids, name, ingredients, price, type, image);
            c.close();
            return model;
        }
        return null;
    }

    public ArrayList<Food> searchMenu(String search) {
        ArrayList<Food> models = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM " + Database.MENU_TB_NAME + " WHERE " + Database.MENU_COLUMN_TYPE
                + " LIKE ? ", new String[]{search + "%"});
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(Database.MENU_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(Database.MENU_COLUMN_NAME));
                String ingredients = c.getString(c.getColumnIndex(Database.MENU_COLUMN_INGREDIENTS));
                String price = c.getString(c.getColumnIndex(Database.MENU_COLUMN_PRICE));
                String type = c.getString(c.getColumnIndex(Database.MENU_COLUMN_TYPE));
                String image = c.getString(c.getColumnIndex(Database.MENU_COLUMN_IMAGE));
                Food model = new Food(id, name, ingredients, price, type, image);
                models.add(model);
            }
            while (c.moveToNext());
            c.close();
        }
        return models;
    }

    /// TODO : >>> Cart

    public boolean insertCart(Cart model) {
        ContentValues cv = new ContentValues();
        cv.put(Database.CART_COLUMN_NAME, model.getName());
        cv.put(Database.CART_COLUMN_INGREDIENTS, model.getIngredients());
        cv.put(Database.CART_COLUMN_PRICE, model.getPrice());
        cv.put(Database.CART_COLUMN_TYPE, model.getType());
        cv.put(Database.CART_COLUMN_IMAGE, model.getImage());
        cv.put(Database.CART_COLUMN_USERNAME, model.getUser_id());
        long result = database.insert(Database.CART_TB_NAME, null, cv);
        return result != -1;
    }

    public boolean updateCart(Cart model) {
        ContentValues values = new ContentValues();
        values.put(Database.CART_COLUMN_NAME, model.getName());
        values.put(Database.CART_COLUMN_INGREDIENTS, model.getIngredients());
        values.put(Database.CART_COLUMN_PRICE, model.getPrice());
        values.put(Database.CART_COLUMN_TYPE, model.getType());
        values.put(Database.CART_COLUMN_IMAGE, model.getImage());
        String args[] = {String.valueOf(model.getId())};
        int res = database.update(Database.CART_TB_NAME, values, "id=?", args);
        return res > 0;
    }

    public long getCartCount() {
        return DatabaseUtils.queryNumEntries(database, Database.CART_TB_NAME);
    }

    public boolean deleteCart(Cart model) {
        String args[] = {String.valueOf(model.getId())};
        int res = database.delete(Database.CART_TB_NAME, "id=?", args);
        return res > 0;
    }

    public ArrayList<Cart> getAllCart() {
        ArrayList<Cart> models = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM " + Database.CART_TB_NAME, null);
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(Database.CART_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(Database.CART_COLUMN_NAME));
                String ingredients = c.getString(c.getColumnIndex(Database.CART_COLUMN_INGREDIENTS));
                String price = c.getString(c.getColumnIndex(Database.CART_COLUMN_PRICE));
                String type = c.getString(c.getColumnIndex(Database.CART_COLUMN_TYPE));
                String image = c.getString(c.getColumnIndex(Database.CART_COLUMN_IMAGE));
                String user_id = c.getString(c.getColumnIndex(Database.CART_COLUMN_USERNAME));
                Cart model = new Cart(id, name, ingredients, price, type, image, user_id);
                models.add(model);
            } while (c.moveToNext());
            c.close();
        }
        return models;
    }

    public ArrayList<Cart> searchCart(String search) {
        ArrayList<Cart> models = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM " + Database.CART_TB_NAME + " WHERE " + Database.CART_COLUMN_USERNAME
                + " LIKE ? ", new String[]{search + "%"});
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(Database.CART_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(Database.CART_COLUMN_NAME));
                String ingredients = c.getString(c.getColumnIndex(Database.CART_COLUMN_INGREDIENTS));
                String price = c.getString(c.getColumnIndex(Database.CART_COLUMN_PRICE));
                String type = c.getString(c.getColumnIndex(Database.CART_COLUMN_TYPE));
                String image = c.getString(c.getColumnIndex(Database.CART_COLUMN_IMAGE));
                String user_id = c.getString(c.getColumnIndex(Database.CART_COLUMN_USERNAME));
                Cart model = new Cart(id, name, ingredients, price, type, image, user_id);
                models.add(model);
            }
            while (c.moveToNext());
            c.close();
        }
        return models;
    }

    /// TODO : >>> User

    public boolean insertUser(User model) {
        ContentValues cv = new ContentValues();
        cv.put(Database.USER_COLUMN_NAME, model.getName());
        cv.put(Database.USER_COLUMN_EMAIL, model.getEmail());
        cv.put(Database.USER_COLUMN_PASSWORD, model.getPassword());
        long result = database.insert(Database.USER_TB_NAME, null, cv);
        return result != -1;
    }

    public boolean checkUser(String email, String pass) {
        String[] columns = {Database.USER_COLUMN_ID};
        String selection = Database.USER_COLUMN_EMAIL + "=?" + " AND " + Database.USER_COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {email, pass};
        Cursor c = database.query(Database.USER_TB_NAME, columns, selection, selectionArgs,
                null, null, null);
        int cursorCount = c.getCount();
        c.close();
        return cursorCount > 0;
    }

    public ArrayList<User> getAllUser() {
        ArrayList<User> models = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM " + Database.USER_TB_NAME, null);
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(Database.USER_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(Database.USER_COLUMN_NAME));
                String password = c.getString(c.getColumnIndex(Database.USER_COLUMN_PASSWORD));
                String email = c.getString(c.getColumnIndex(Database.USER_COLUMN_EMAIL));
                User model = new User(id, name, email, password);
                models.add(model);
            } while (c.moveToNext());
            c.close();

        }
        return models;
    }

    public ArrayList<User> searchUser(String search) {
        ArrayList<User> models = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM " + Database.USER_TB_NAME + " WHERE " + Database.USER_COLUMN_EMAIL
                + " LIKE ? ", new String[]{search + "%"});
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(Database.USER_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(Database.USER_COLUMN_NAME));
                String password = c.getString(c.getColumnIndex(Database.USER_COLUMN_PASSWORD));
                String email = c.getString(c.getColumnIndex(Database.USER_COLUMN_EMAIL));
                User model = new User(id, name, email, password);
                models.add(model);
            }
            while (c.moveToNext());
            c.close();
        }
        return models;
    }

    public long getUserCount() {
        return DatabaseUtils.queryNumEntries(database, Database.USER_TB_NAME);
    }

}
