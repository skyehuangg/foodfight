package com.example.foodfight;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

//Skye and Ava collaborated to implement database!!
//all good

public class SignupModeActivity extends AppCompatActivity {

    String accountType;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_mode);

        // Skye working on creating functionality for switches
        SwitchMaterial parentSwitch = findViewById(R.id.parent_mode);
        SwitchMaterial studentSwitch = findViewById(R.id.student_mode);

        parentSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                studentSwitch.setChecked(false);
                accountType = "Parent";
            } else{
                accountType = null;
            }
        });

        //Skye make code remember which mode is selected
        studentSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                parentSwitch.setChecked(false);
                accountType = "Student";
            } else{
                accountType = null;
            }
        });

        Button goToMap = findViewById(R.id.goToMap);
        goToMap.setOnClickListener(v -> { saveData();

        if(accountType == null){
            Context ctx = SignupModeActivity.this;
            Toast.makeText(ctx, "You must select a mode.", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(SignupModeActivity.this, MapActivity.class);
            startActivity(intent);}
        });

        ImageButton backToConfirm = findViewById(R.id.back);
        backToConfirm.setOnClickListener(v -> finish());

        db = AppDatabase.getInstance(this);
    }

    private void saveData(){

        int uid = StorageActivity.getCurrentUserId();

        new Thread(() -> {
            UserDAO dao = db.getUserDAO();
            User user = dao.getUserById(uid);
            user.accountType = accountType;
            dao.updateUser(user);
        }).start();
    }
}
