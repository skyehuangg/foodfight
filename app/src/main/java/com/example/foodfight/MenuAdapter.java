package com.example.foodfight;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private final Context context;
    private final ArrayList<MenuItemMode1> items;
    private User currentUser;
    private boolean showAllergyWarnings = false;

    public MenuAdapter(Context context, List<MenuItemMode1> items) {
        this.context = context;
        this.items = new ArrayList<>(items);
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setShowAllergyWarnings(boolean show) {
        if (this.showAllergyWarnings != show) {
            this.showAllergyWarnings = show;
            notifyItemRangeChanged(0, items.size());
        }
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.items_list, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItemMode1 item = items.get(position);

        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());
        holder.image.setImageResource(item.getImageResId());

        if (showAllergyWarnings && currentUser != null && containsAllergen(item, currentUser)) {
            holder.allergyWarning.setVisibility(View.VISIBLE);
        } else {
            holder.allergyWarning.setVisibility(View.GONE);
        }

        holder.more.setOnClickListener(v -> {
            Context context = v.getContext();

            Intent intent = new Intent(context, CheeseburgerActivity.class);
            intent.putExtra("key", "value_for_cheeseburger_activity");
            context.startActivity(intent);
        });
    }

    private boolean containsAllergen(MenuItemMode1 item, User user) {
        if (user.getNoAllergy()) {
            return false;
        }

        String itemName = item.getName().toLowerCase();

        if (user.getDairyAllergy() && itemContainsDairy(itemName)) {
            return true;
        }
        if (user.getPeanutAllergy() && itemContainsPeanuts()) {
            return true;
        }
        if (user.getGlutenAllergy() && itemContainsGluten(itemName)){
            return true;
        }
        if(user.getVegetarian() && itemContainsVegetarian(itemName)){
            return true;
        }
        if(user.getVegan() && itemContainsVegan(itemName)){
            return true;
        }
        if(user.getShellfishAllergy() && itemContainsShellfish()){
            return true;
        }
        return user.getSoyAllergy() && itemContainsSoy();
    }

    private boolean itemContainsDairy(String itemName) {
        return itemName.contains("cheese");
    }

    private boolean itemContainsPeanuts() {
        return false;
    }

    private boolean itemContainsGluten(String itemName) {
        return itemName.contains("burger") || itemName.contains("tender");
    }

    private boolean itemContainsVegetarian(String itemName){
        return itemName.contains("burger") || itemName.contains("tender");
    }

    private boolean itemContainsVegan(String itemName){
        return itemName.contains("burger") || itemName.contains("tender");
    }

    public boolean itemContainsShellfish(){return false;}

    public boolean itemContainsSoy(){return false;}

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, price, allergyWarning;
        Button more;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.itemImage);
            name  = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.itemPrice);
            more  = itemView.findViewById(R.id.itemMore);
            allergyWarning = itemView.findViewById(R.id.allergyWarning);
        }
    }
}