package com.example.foodfight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    TextView welcomeName;
    private AppDatabase db;

    //all good

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        welcomeName = findViewById(R.id.welcomeName);
        db = AppDatabase.getInstance(this);
        retrieveData();

        Button menu = findViewById(R.id.menu);
        menu.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        TextView logout = findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        //Ava sent allergy and name info between screens

        Button allergies = findViewById(R.id.allergies);
        allergies.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AllergiesActivity.class);
            startActivity(intent);
        });

        Button profile = findViewById(R.id.profile);
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void retrieveData() {
        int uid = StorageActivity.getCurrentUserId();

        new Thread(() -> {
            UserDAO dao = db.getUserDAO();
            final User user = dao.getUserById(uid);

            runOnUiThread(() -> {
                if (user != null) {
                    String text = user.getFirstName() + "!";
                    welcomeName.setText(text);
                }
            });
        }).start();
    }

}
