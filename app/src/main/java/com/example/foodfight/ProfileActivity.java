package com.example.foodfight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

//all good

public class ProfileActivity extends AppCompatActivity {

    Button send_button;
    TextView nameTextView;
    TextView emailTextView;
    ImageView profileIcon;
    TextView accountModeTextView;
    private AppDatabase db;

    // Skye linked Profile to Homepage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        nameTextView = findViewById(R.id.profileName2);
        emailTextView = findViewById(R.id.profileEmail2);
        profileIcon = findViewById(R.id.profileFinalIcon);
        accountModeTextView = findViewById(R.id.accountModeText);
        db = AppDatabase.getInstance(this);
        retrieveData();

        // Ava coded information sending between screens

        send_button = findViewById(R.id.profileToHome);
        send_button.setOnClickListener(v -> {
            //send_text = String.valueOf(InformationActivity.userName);
            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }

    //Skye, Ava, and Abby work on retrieving data to display
    private void retrieveData() {

        int uid = StorageActivity.getCurrentUserId();

        new Thread(() -> {

            UserDAO dao = db.getUserDAO();
            User user = dao.getUserById(uid);

            setAllergies(user);

            String fullName = user.getFullName();
            int setIcon = user.getProfileIcon();
            String email = user.getEmail();
            String accountType = user.getAccountType();

            runOnUiThread(() -> {
                nameTextView.setText(fullName);
                profileIcon.setImageResource(setIcon);
                emailTextView.setText(email);
                accountModeTextView.setText(accountType);
            });
        }).start();
    }

    // Skye, Ava, and Abby work to show customized allergies
    private void setAllergies(User user){
        TextView allergyBox = findViewById(R.id.userAllergies);
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
