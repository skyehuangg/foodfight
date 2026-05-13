package com.example.foodfight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

//Skye coded Confirm School Activity
//all good

public class ConfirmSchoolActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirm_school);

        db = AppDatabase.getInstance(this);
        retrieveData();

        Button yes = findViewById(R.id.yesButton);
        yes.setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmSchoolActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        Button no = findViewById(R.id.noButton);
        no.setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmSchoolActivity.this, MapActivity.class);
            startActivity(intent);
        });
    }

    //Skye, Ava, and Abby work on retrieving data to display
    private void retrieveData() {

        int uid = StorageActivity.getCurrentUserId();
        TextView confirmationServiceText = findViewById(R.id.isthiscorrect);

        new Thread(() -> {
            UserDAO dao = db.getUserDAO();
            User user = dao.getUserById(uid);

            runOnUiThread(() -> {
                if (user != null) {
                    if(user.getAccountType().equals("Student")){
                        String text1 = "Is this your food service?";
                        confirmationServiceText.setText(text1);
                    } else{
                        String text2 = "Is this your child's food service?";
                        confirmationServiceText.setText(text2);
                    }
                }
            });
        }).start();
    }
}
