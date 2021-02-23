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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.resturant.Interface.FoodInterface;
import com.android.resturant.Model.Food;
import com.android.resturant.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ProductViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<Food> list;
    private List<Food> exampleListFull;
    private FoodInterface Interface;

    public FoodAdapter(ArrayList<Food> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
            exampleListFull = new ArrayList<>(list);
    }


    public FoodAdapter(ArrayList<Food> list, Context mContext, FoodInterface Interface) {
        this.list = list;
        this.mContext = mContext;
        this.Interface = Interface;
        exampleListFull = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_food_design, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        final Food lists = list.get(position);
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


    static class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView name, price, ingredients;

        private ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.food_img);
            name = itemView.findViewById(R.id.food_tv_name);
            price = itemView.findViewById(R.id.food_tv_price);
            ingredients = itemView.findViewById(R.id.food_tv_ingredients);
        }

        @SuppressLint("SetTextI18n")
        private void bind(Food lists) {
            name.setText(lists.getName());
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
            List<Food> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Food item : exampleListFull) {
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