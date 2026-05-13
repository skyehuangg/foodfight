package com.example.foodfight;

//all good

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class OtherAllergenActivity extends AppCompatActivity {

    private AppDatabase db;
    private EditText textInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_other_allergen);

        db = AppDatabase.getInstance(this);
        textInput = findViewById(R.id.inputOtherAllergy);

        ImageButton myButton = findViewById(R.id.myButton);
        myButton.setOnClickListener(v -> {
            Intent intent = new Intent(OtherAllergenActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        //Ava sent allergy information between pages

        Button submit = findViewById(R.id.submitreview);
        submit.setOnClickListener(v -> {
             if(textInput != null) {
                 saveData();
                 Intent nextIntent = new Intent(OtherAllergenActivity.this, ThankYouActivity.class);
                 startActivity(nextIntent);
             }
             else{
                 Context ctx = OtherAllergenActivity.this;
                 Toast.makeText(ctx, "You must enter an allergy.", Toast.LENGTH_SHORT).show();
             }
        });

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(OtherAllergenActivity.this, AllergiesActivity.class);
            startActivity(intent);
        });
    }

    private void saveData(){


        int uid = StorageActivity.getCurrentUserId();

        new Thread(() -> {
            UserDAO dao = db.getUserDAO();
            User user = dao.getUserById(uid);

            user.stringOther = textInput.getText().toString().substring(0, 1).toUpperCase() + textInput.getText().toString().substring(1).toLowerCase() + ", ";
            dao.updateUser(user);
        }).start();
    }
}