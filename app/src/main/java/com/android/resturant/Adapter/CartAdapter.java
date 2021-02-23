package com.android.resturant.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.resturant.Interface.CartInterface;
import com.android.resturant.Interface.FoodInterface;
import com.android.resturant.Model.Cart;
import com.android.resturant.Model.Food;
import com.android.resturant.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<Cart> list;
    private List<Cart> exampleListFull;
    private CartInterface Interface;

    public CartAdapter(ArrayList<Cart> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        exampleListFull = new ArrayList<>(list);
    }

    public CartAdapter(ArrayList<Cart> list, Context mContext, CartInterface Interface) {
        this.list = list;
        this.mContext = mContext;
        this.Interface = Interface;
        exampleListFull = new ArrayList<>(list);
    }

    public void updateData(ArrayList<Cart> viewModels) {
        list.clear();
        list.addAll(viewModels);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cart_design, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, final int position) {
        final Cart lists = list.get(position);
        holder.bind(lists);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Interface.onClick(lists);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class CartViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView name, type, price, ingredients;

        private CartViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.cart_img);
            name = itemView.findViewById(R.id.cart_tv_name);
            type = itemView.findViewById(R.id.cart_tv_type);
            price = itemView.findViewById(R.id.cart_tv_price);
            ingredients = itemView.findViewById(R.id.cart_tv_ingredients);
        }

        @SuppressLint("SetTextI18n")
        private void bind(Cart lists) {
            name.setText(lists.getName());
            type.setText(lists.getType());
            ingredients.setText(lists.getIngredients());
            price.setText(lists.getPrice().trim() + " ₪ ");
            Picasso.get().load(lists.getImage()).placeholder(R.drawable.ic_restaurant).into(image);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Cart> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Cart item : exampleListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}