package com.android.resturant.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.resturant.Adapter.CartAdapter;
import com.android.resturant.Adapter.FoodAdapter;
import com.android.resturant.DB.DatabaseAccess;
import com.android.resturant.Interface.CartInterface;
import com.android.resturant.Interface.FoodInterface;
import com.android.resturant.Model.Cart;
import com.android.resturant.Model.Food;
import com.android.resturant.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView rv;
    private CartAdapter adapter;
    private ArrayList<Cart> list;
    private Toolbar toolbar;
    private TextView tv;
    private DatabaseAccess db;
    private String token;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        sp = getSharedPreferences("Resturant", Context.MODE_PRIVATE);
        token = sp.getString("token", "-1");

        rv = findViewById(R.id.cart_rv);
        tv = findViewById(R.id.cart_tv);
        toolbar = findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = new ArrayList<>();
        db = DatabaseAccess.getInstance(this);
        db.open();
        if (db.getCartCount() != 0) {
            list = db.searchCart(token);
            rv.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
        } else {
            rv.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        }
        db.close();
        adapter = new CartAdapter(list, this, new CartInterface() {
            @Override
            public void onClick(Cart model) {
                onCreateDialog(getResources().getString(R.string.Delete_Product), model);
            }
        });
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    private void onCreateDialog(String message, final Cart model) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db = DatabaseAccess.getInstance(CartActivity.this);
                        db.open();
                        db.deleteCart(model);
                        list = db.searchCart(token);
                        adapter.updateData(list);
                        db.close();
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.main_search);
        MenuItemCompat.setShowAsAction(searchItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
                return true;
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}