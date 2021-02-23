package com.android.resturant.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.android.resturant.Model.Cart;
import com.android.resturant.Model.Food;
import com.android.resturant.Model.User;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class DB_OpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Resturant.db";
    public static final int DB_VERSION = 1;

    /* TODO : >> Menu DB */
    public static final String MENU_TB_NAME = "menu";
    public static final String MENU_COLUMN_ID = "id";
    public static final String MENU_COLUMN_NAME = "name";
    public static final String MENU_COLUMN_INGREDIENTS = "Ingredients";
    public static final String MENU_COLUMN_PRICE = "price";
    public static final String MENU_COLUMN_TYPE = "type";
    public static final String MENU_COLUMN_IMAGE = "image";

    /* TODO : >> Cart DB */
    public static final String CART_TB_NAME = "cart";
    public static final String CART_COLUMN_ID = "id";
    public static final String CART_COLUMN_NAME = "name ";
    public static final String CART_COLUMN_INGREDIENTS = "Ingredients";
    public static final String CART_COLUMN_PRICE = "price";
    public static final String CART_COLUMN_TYPE = "type";
    public static final String CART_COLUMN_IMAGE = "image";
    public static final String CART_COLUMN_USERNAME = "user_id";

    /* TODO : >> User DB */
    public static final String USER_TB_NAME = "user";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_NAME = "name";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_PASSWORD = "passwoed";

    public DB_OpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MENU_TB_NAME + " " + "(" + MENU_COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT ," + MENU_COLUMN_NAME + " TEXT ,"
                + MENU_COLUMN_INGREDIENTS + " TEXT," + MENU_COLUMN_PRICE + " TEXT," + MENU_COLUMN_TYPE
                + " TEXT," + MENU_COLUMN_IMAGE + " TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + MENU_TB_NAME);
//        onCreate(db);
    }


    /// TODO : >>> Menu

    public boolean insertMenu(Food model) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MENU_COLUMN_NAME, model.getName());
        cv.put(MENU_COLUMN_INGREDIENTS, model.getIngredients());
        cv.put(MENU_COLUMN_PRICE, model.getPrice());
        cv.put(MENU_COLUMN_TYPE, model.getType());
        cv.put(MENU_COLUMN_IMAGE, model.getImage());
        long result = db.insert(MENU_TB_NAME, null, cv);
        return result != -1;
    }

    public boolean updateMenu(Food model) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MENU_COLUMN_NAME, model.getName());
        values.put(MENU_COLUMN_INGREDIENTS, model.getIngredients());
        values.put(MENU_COLUMN_PRICE, model.getPrice());
        values.put(MENU_COLUMN_TYPE, model.getType());
        values.put(MENU_COLUMN_IMAGE, model.getImage());
        String args[] = {String.valueOf(model.getId())};
        int res = db.update(MENU_TB_NAME, values, "id=?", args);
        return res > 0;
    }

    public long getMenuCount() {
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, MENU_TB_NAME);
    }

    public boolean deleteMenu(Food model) {
        SQLiteDatabase db = getReadableDatabase();
        String args[] = {String.valueOf(model.getId())};
        int res = db.delete(Database.MENU_TB_NAME, "id=?", args);
        return res > 0;
    }

    public ArrayList<Food> getAllMenu() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Food> models = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + MENU_TB_NAME, null);
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(MENU_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(MENU_COLUMN_NAME));
                String ingredients = c.getString(c.getColumnIndex(MENU_COLUMN_INGREDIENTS));
                String price = c.getString(c.getColumnIndex(MENU_COLUMN_PRICE));
                String type = c.getString(c.getColumnIndex(MENU_COLUMN_TYPE));
                String image = c.getString(c.getColumnIndex(MENU_COLUMN_IMAGE));
                Food model = new Food(id, name, ingredients, price, type, image);
                models.add(model);
            } while (c.moveToNext());
            c.close();
        }
        return models;
    }

    public Food getMenu(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + MENU_TB_NAME + " WHERE " + MENU_COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        if (c != null && c.moveToFirst()) {
            int ids = c.getInt(c.getColumnIndex(MENU_COLUMN_ID));
            String name = c.getString(c.getColumnIndex(MENU_COLUMN_NAME));
            String ingredients = c.getString(c.getColumnIndex(MENU_COLUMN_INGREDIENTS));
            String price = c.getString(c.getColumnIndex(MENU_COLUMN_PRICE));
            String type = c.getString(c.getColumnIndex(MENU_COLUMN_TYPE));
            String image = c.getString(c.getColumnIndex(MENU_COLUMN_IMAGE));
            Food model = new Food(id, name, ingredients, price, type, image);
            c.close();
            return model;
        }
        return null;
    }

    public ArrayList<Food> searchMenu(String search) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Food> models = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + MENU_TB_NAME + " WHERE " + Database.MENU_COLUMN_NAME
                + " LIKE ? ", new String[]{search + "%"});
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(MENU_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(MENU_COLUMN_NAME));
                String ingredients = c.getString(c.getColumnIndex(MENU_COLUMN_INGREDIENTS));
                String price = c.getString(c.getColumnIndex(MENU_COLUMN_PRICE));
                String type = c.getString(c.getColumnIndex(MENU_COLUMN_TYPE));
                String image = c.getString(c.getColumnIndex(MENU_COLUMN_IMAGE));
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
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DB_OpenHelper.CART_COLUMN_NAME, model.getName());
        cv.put(DB_OpenHelper.CART_COLUMN_INGREDIENTS, model.getIngredients());
        cv.put(DB_OpenHelper.CART_COLUMN_PRICE, model.getPrice());
        cv.put(DB_OpenHelper.CART_COLUMN_TYPE, model.getType());
        cv.put(DB_OpenHelper.CART_COLUMN_IMAGE, model.getImage());
        cv.put(DB_OpenHelper.CART_COLUMN_USERNAME, model.getUser_id());
        long result = db.insert(DB_OpenHelper.CART_TB_NAME, null, cv);
        return result != -1;
    }

    public boolean updateCart(Cart model) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_OpenHelper.CART_COLUMN_NAME, model.getName());
        values.put(DB_OpenHelper.CART_COLUMN_INGREDIENTS, model.getIngredients());
        values.put(DB_OpenHelper.CART_COLUMN_PRICE, model.getPrice());
        values.put(DB_OpenHelper.CART_COLUMN_TYPE, model.getType());
        values.put(DB_OpenHelper.CART_COLUMN_IMAGE, model.getImage());
        String args[] = {String.valueOf(model.getId())};
        int res = db.update(DB_OpenHelper.CART_TB_NAME, values, "id=?", args);
        return res > 0;
    }

    public long getCartCount() {
        SQLiteDatabase db = getWritableDatabase();
        return DatabaseUtils.queryNumEntries(db, DB_OpenHelper.CART_TB_NAME);
    }

    public boolean deleteCart(Cart model) {
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {String.valueOf(model.getId())};
        int res = db.delete(DB_OpenHelper.CART_TB_NAME, "id=?", args);
        return res > 0;
    }

    public ArrayList<Cart> getAllCart() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Cart> models = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + DB_OpenHelper.CART_TB_NAME, null);
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DB_OpenHelper.CART_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(DB_OpenHelper.CART_COLUMN_NAME));
                String ingredients = c.getString(c.getColumnIndex(DB_OpenHelper.CART_COLUMN_INGREDIENTS));
                String price = c.getString(c.getColumnIndex(DB_OpenHelper.CART_COLUMN_PRICE));
                String type = c.getString(c.getColumnIndex(DB_OpenHelper.CART_COLUMN_TYPE));
                String image = c.getString(c.getColumnIndex(DB_OpenHelper.CART_COLUMN_IMAGE));
                String user_id = c.getString(c.getColumnIndex(DB_OpenHelper.CART_COLUMN_USERNAME));
                Cart model = new Cart(id, name, ingredients, price, type, image, user_id);
                models.add(model);
            } while (c.moveToNext());
            c.close();
        }
        return models;
    }

    public ArrayList<Cart> searchCart(String search) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Cart> models = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + DB_OpenHelper.CART_TB_NAME + " WHERE " + DB_OpenHelper.CART_COLUMN_USERNAME
                + " LIKE ? ", new String[]{search + "%"});
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DB_OpenHelper.CART_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(DB_OpenHelper.CART_COLUMN_NAME));
                String ingredients = c.getString(c.getColumnIndex(DB_OpenHelper.CART_COLUMN_INGREDIENTS));
                String price = c.getString(c.getColumnIndex(DB_OpenHelper.CART_COLUMN_PRICE));
                String type = c.getString(c.getColumnIndex(DB_OpenHelper.CART_COLUMN_TYPE));
                String image = c.getString(c.getColumnIndex(DB_OpenHelper.CART_COLUMN_IMAGE));
                String user_id = c.getString(c.getColumnIndex(DB_OpenHelper.CART_COLUMN_USERNAME));
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
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DB_OpenHelper.USER_COLUMN_NAME, model.getName());
        cv.put(DB_OpenHelper.USER_COLUMN_EMAIL, model.getEmail());
        cv.put(DB_OpenHelper.USER_COLUMN_PASSWORD, model.getPassword());
        long result = db.insert(DB_OpenHelper.USER_TB_NAME, null, cv);
        return result != -1;
    }

    public boolean checkUser(String email, String pass) {
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {DB_OpenHelper.USER_COLUMN_ID};
        String selection = DB_OpenHelper.USER_COLUMN_EMAIL + "=?" + " AND " + DB_OpenHelper.USER_COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {email, pass};
        Cursor c = db.query(DB_OpenHelper.USER_TB_NAME, columns, selection, selectionArgs,
                null, null, null);
        int cursorCount = c.getCount();
        c.close();
        return cursorCount > 0;
    }

    public ArrayList<User> getAllUser() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<User> models = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + DB_OpenHelper.USER_TB_NAME, null);
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DB_OpenHelper.USER_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(DB_OpenHelper.USER_COLUMN_NAME));
                String password = c.getString(c.getColumnIndex(DB_OpenHelper.USER_COLUMN_PASSWORD));
                String email = c.getString(c.getColumnIndex(DB_OpenHelper.USER_COLUMN_EMAIL));
                User model = new User(id, name, email, password);
                models.add(model);
            } while (c.moveToNext());
            c.close();

        }
        return models;
    }

    public ArrayList<User> searchUser(String search) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<User> models = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + DB_OpenHelper.USER_TB_NAME + " WHERE " + DB_OpenHelper.USER_COLUMN_EMAIL
                + " LIKE ? ", new String[]{search + "%"});
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DB_OpenHelper.USER_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(DB_OpenHelper.USER_COLUMN_NAME));
                String password = c.getString(c.getColumnIndex(DB_OpenHelper.USER_COLUMN_PASSWORD));
                String email = c.getString(c.getColumnIndex(DB_OpenHelper.USER_COLUMN_EMAIL));
                User model = new User(id, name, email, password);
                models.add(model);
            }
            while (c.moveToNext());
            c.close();
        }
        return models;
    }

    public long getUserCount() {
        SQLiteDatabase db = getWritableDatabase();
        return DatabaseUtils.queryNumEntries(db, DB_OpenHelper.USER_TB_NAME);
    }

}
