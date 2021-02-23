package com.android.resturant.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.resturant.Adapter.FoodAdapter;
import com.android.resturant.DB.DatabaseAccess;
import com.android.resturant.Interface.FoodInterface;
import com.android.resturant.Model.Cart;
import com.android.resturant.Model.Food;
import com.android.resturant.R;

import java.util.ArrayList;

public class EntreesFragment extends Fragment {

    private String token;
    private SharedPreferences sp;
    private DatabaseAccess db;
    private RecyclerView rv;
    private FoodAdapter adapter;
    private ArrayList<Food> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_entrees, container, false);

        sp = getActivity().getSharedPreferences("Resturant", Context.MODE_PRIVATE);
        token = sp.getString("token", "-1");
        rv = v.findViewById(R.id.entrees_rv);

        list = new ArrayList<>();

         db = DatabaseAccess.getInstance(getContext());
        db.open();
        list = db.searchMenu("مقبلات");
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
}