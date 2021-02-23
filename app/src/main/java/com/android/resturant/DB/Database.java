package com.android.resturant.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.android.resturant.Model.Food;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class Database extends SQLiteAssetHelper {

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
    public static final String CART_COLUMN_NAME = "name";
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

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

}
