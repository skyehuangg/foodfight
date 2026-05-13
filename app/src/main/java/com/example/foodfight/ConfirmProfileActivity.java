//Ava Obara
package com.example.foodfight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmProfileActivity extends AppCompatActivity {

    Button send_button;
    TextView confirmName;
    private AppDatabase db;
    ImageView chosenIcon;

    //all good

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_profile);

        confirmName = findViewById(R.id.name);
        chosenIcon = findViewById(R.id.chosenIcon);
        db = AppDatabase.getInstance(this);
        retrieveData();

        Button signup = findViewById(R.id.backToSignup);
        signup.setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmProfileActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        send_button = findViewById(R.id.goToMap);
        send_button.setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmProfileActivity.this, SignupModeActivity.class);
            startActivity(intent);
            });
    }

    //Skye, Ava, and Abby work on retrieving data to display
    private void retrieveData() {

        int uid = StorageActivity.getCurrentUserId();

        new Thread(() -> {
            UserDAO dao = db.getUserDAO();
            User user = dao.getUserById(uid);

            runOnUiThread(() -> {
                if (user != null) {
                    String fullName = user.getFullName();
                    int profileIcon = user.getProfileIcon();

                    confirmName.setText(fullName);
                    chosenIcon.setImageResource(profileIcon);
                } else {
                    String alternateText = "No user found";
                    confirmName.setText(alternateText);
                }
            });
        }).start();
    }
}

