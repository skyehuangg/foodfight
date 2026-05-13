package com.example.foodfight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Locale;

public class MenuActivity extends AppCompatActivity {
    String[] menuItem = {"All Items", "Restrictions"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    RecyclerView menuRecycler;
    MenuAdapter adapter;
    TextView balanceText;
    private AppDatabase db;

    private ArrayList<MenuItemMode1> fullMenuList;
    private ArrayList<MenuItemMode1> filteredMenuList;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<>(this, R.layout.activity_dropdown, menuItem);
        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = adapterView.getItemAtPosition(i).toString();
            filterMenu(item);
        });

        balanceText = findViewById(R.id.money);
        db = AppDatabase.getInstance(this);

        menuRecycler = findViewById(R.id.menuRecycler);
        menuRecycler.setLayoutManager(new LinearLayoutManager(this));

        fullMenuList = new ArrayList<>();
        fullMenuList.add(new MenuItemMode1("Cheeseburger", "$5.00", R.drawable.cheeseburger));
        fullMenuList.add(new MenuItemMode1("Hamburger", "$4.50", R.drawable.hamburger));
        fullMenuList.add(new MenuItemMode1("Tenders", "$6.00", R.drawable.tenders));

        filteredMenuList = new ArrayList<>(fullMenuList);

        adapter = new MenuAdapter(this, filteredMenuList);
        menuRecycler.setAdapter(adapter);

        retrieveData();

        ImageButton myButton = findViewById(R.id.myButton);
        myButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }

    private void filterMenu(String filterType) {
        filteredMenuList.clear();

        switch (filterType) {
            case "All Items":
                filteredMenuList.addAll(fullMenuList);
                adapter.setShowAllergyWarnings(false);
                break;

            case "Restrictions":
                filteredMenuList.addAll(fullMenuList);
                adapter.setShowAllergyWarnings(true);
                if (currentUser == null) {
                    Toast.makeText(this, "Loading user data...", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void retrieveData() {
        int uid = StorageActivity.getCurrentUserId();

        new Thread(() -> {
            UserDAO dao = db.getUserDAO();
            final User user = dao.getUserById(uid);

            runOnUiThread(() -> {
                if (user == null) {
                    String balance = "$0.00";
                    balanceText.setText(balance);
                    return;
                }

                currentUser = user;
                adapter.setCurrentUser(user);

                final double moneyAmount = user.getBalance();

                setAllergies(user);
                String setText = "$" + String.format(Locale.getDefault(),"%.2f", moneyAmount);
                balanceText.setText(setText);
            });
        }).start();
    }

    // Skye, Ava, and Abby work to show customized allergies
    private void setAllergies(User user){
        TextView allergyBox = findViewById(R.id.AllergiesBox);
        String setAllergies = "";
        if(user.getNoAllergy()){
            setAllergies = "None";
            allergyBox.setText(setAllergies);
        }
        else{
            if(user.getDairyAllergy()){
                setAllergies += "Dairy, ";
            }
            if(user.getPeanutAllergy()){
                setAllergies += "Peanuts, ";
            }
            if(user.getGlutenAllergy()){
                setAllergies += "Gluten, ";
            }
            if(user.getVegetarian()){
                setAllergies += "Vegetarian, ";
            }
            if(user.getVegan()){
                setAllergies += "Vegan, ";
            }
            if(user.getSoyAllergy()){
                setAllergies += "Soy, ";
            }
            if(user.getShellfishAllergy()){
                setAllergies += "Shellfish, ";
            }
            if(user.getOtherAllergy()){
                setAllergies += user.getOtherStringAllergy();
            }

            if(setAllergies.length() >= 2){
                int removeEnd = setAllergies.length() - 2;
                String showUserAllergies = setAllergies.substring(0, removeEnd);
                allergyBox.setText(showUserAllergies);
            }
        }
    }
}
