package com.android.resturant.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.resturant.Adapter.FoodAdapter;
import com.android.resturant.DB.DatabaseAccess;
import com.android.resturant.Interface.FoodInterface;
import com.android.resturant.Model.Cart;
import com.android.resturant.Model.Food;
import com.android.resturant.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {

    private DatabaseAccess db;
    private RecyclerView rv;
    private FoodAdapter adapter;
    private ArrayList<Food> list;
    private String token;
    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        sp = getActivity().getSharedPreferences("Resturant", Context.MODE_PRIVATE);
        token = sp.getString("token", "-1");

        rv = v.findViewById(R.id.home_rv);
        list = new ArrayList<>();

        db = DatabaseAccess.getInstance(getContext());
        db.open();
        list = db.getAllMenu();
        db.close();

        adapter = new FoodAdapter(list, getContext(), new FoodInterface() {
            @Override
            public void onClick(Food model) {
                onCreateDialog(getResources().getString(R.string.Do_you_want_add) + model.getName()
                                + getResources().getString(R.string.At_a_price) + model.getPrice() + " ₪ "
                                + getResources().getString(R.string.To_the_Cart),
                        model.getName(), model.getIngredients(), model.getPrice() + "", model.getType(), model.getImage()
                        , token);
            }
        });

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        return v;
    }

    private void onCreateDialog(String message, final String name, final String Ingredients, final String price
            , final String type, final String image, final String user_id) {
        new AlertDialog.Builder(getContext())
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
                        db = DatabaseAccess.getInstance(getContext());
                        db.open();
                        db.insertCart(new Cart(name, Ingredients, price, type, image, user_id));
                        db.close();
                    }
                })
                .show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
        super.onCreateOptionsMenu(menu, inflater);
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

}