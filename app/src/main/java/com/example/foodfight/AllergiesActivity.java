package com.example.foodfight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

//Skye and Ava collaborated to implement database!!
//all good

public class AllergiesActivity extends AppCompatActivity {
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_allergies);

        ImageButton button3 = findViewById(R.id.myButton);
        button3.setOnClickListener(v -> {
            Intent intent = new Intent(AllergiesActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        //Ava sent allergy information between pages
        //Skye worked on organizing

        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(v -> { saveData();

            CheckBox otherCheckbox = findViewById(R.id.other);

        if (otherCheckbox.isChecked()) {
            Intent intent = new Intent(AllergiesActivity.this, OtherAllergenActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(AllergiesActivity.this, ThankYouActivity.class);
            startActivity(intent);}
        });

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(AllergiesActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        db = AppDatabase.getInstance(this);
    }

    private void saveData(){
        CheckBox dairyCheckbox = findViewById(R.id.dairy);
        CheckBox peanutCheckbox = findViewById(R.id.peanut);
        CheckBox glutenCheckbox = findViewById(R.id.gluten);
        CheckBox vegetarianCheckbox = findViewById(R.id.vegetarian);
        CheckBox veganCheckbox = findViewById(R.id.vegan);
        CheckBox shellfishCheckbox = findViewById(R.id.shellfish);
        CheckBox soyCheckbox = findViewById(R.id.soy);
        CheckBox noneCheckbox = findViewById(R.id.none);
        CheckBox otherCheckbox = findViewById(R.id.other);

        int uid = StorageActivity.getCurrentUserId();

        new Thread(() -> {
            UserDAO dao = db.getUserDAO();
            User user = dao.getUserById(uid);

            if (noneCheckbox.isChecked()){
                user.noAllergy = noneCheckbox.isChecked();
                user.dairyAllergy = false;
                user.peanutAllergy = false;
                user.glutenAllergy = false;
                user.vegetarian = false;
                user.vegan = false;
                user.shellfishAllergy = false;
                user.soyAllergy = false;
                user.otherAllergy = false;
                user.stringOther = null;
            }
            else{
                user.dairyAllergy = dairyCheckbox.isChecked();
                user.peanutAllergy = peanutCheckbox.isChecked();
                user.glutenAllergy = glutenCheckbox.isChecked();
                user.vegetarian = vegetarianCheckbox.isChecked();
                user.vegan = veganCheckbox.isChecked();
                user.shellfishAllergy = shellfishCheckbox.isChecked();
                user.soyAllergy = soyCheckbox.isChecked();
                user.otherAllergy = otherCheckbox.isChecked();
                user.noAllergy = false;
                if(!dairyCheckbox.isChecked() && !peanutCheckbox.isChecked() && !glutenCheckbox.isChecked() && !vegetarianCheckbox.isChecked() && !veganCheckbox.isChecked() && !shellfishCheckbox.isChecked() && !soyCheckbox.isChecked() && !otherCheckbox.isChecked()){
                    user.noAllergy = noneCheckbox.isChecked();
                }
            }

            dao.updateUser(user);


        }).start();
    }
}