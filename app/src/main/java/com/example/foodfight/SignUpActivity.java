//Ava Obara
package com.example.foodfight;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

//Database imports

import androidx.appcompat.app.AppCompatActivity;

//Skye and Ava collaborated to implement database!!
//all good

public class SignUpActivity extends AppCompatActivity {

    Button send_button;
    EditText firstName;
    EditText lastName;
    boolean nextActivity = false;
    Integer chosenProfile = R.drawable.strawberry_icon;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = AppDatabase.getInstance(this);

        ImageButton imageButton = findViewById(R.id.carrot);

        imageButton.setOnClickListener(v->{
            chosenProfile = R.drawable.carrot_icon;
            Context ctx = SignUpActivity.this;
            Toast.makeText(ctx, "You've chosen Carrot!", Toast.LENGTH_SHORT).show();
        });

        ImageButton imageButton2 = findViewById(R.id.banana);

        imageButton2.setOnClickListener(v->{
            chosenProfile = R.drawable.banana_icon;
            Context ctx = SignUpActivity.this;
            Toast.makeText(ctx, "You've chosen Banana!", Toast.LENGTH_SHORT).show();
        });

        ImageButton imageButton4 = findViewById(R.id.strawberry);

        imageButton4.setOnClickListener(v->{
            chosenProfile = R.drawable.strawberry_icon;
            Context ctx = SignUpActivity.this;
            Toast.makeText(ctx, "You've chosen Strawberry!", Toast.LENGTH_SHORT).show();
        });

        ImageButton imageButton5 = findViewById(R.id.tomato);

        imageButton5.setOnClickListener(v->{
            chosenProfile = R.drawable.tomato_icon;
            Context ctx = SignUpActivity.this;
            Toast.makeText(ctx, "You've chosen Tomato!", Toast.LENGTH_SHORT).show();
        });

        send_button = findViewById(R.id.set);
        firstName = findViewById(R.id.inputFirstName);
        lastName = findViewById(R.id.inputLastName);

        send_button.setOnClickListener(v -> {
            checkForRequirements();
            if(nextActivity){
                saveData();
                }
            });

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void saveData(){
        final String mlastname = ((EditText)
                findViewById(R.id.inputLastName)).getText().toString();
                String lName = mlastname.substring(0, 1).toUpperCase() + mlastname.substring(1).toLowerCase();
        final String mfirstname = ((EditText)
                findViewById(R.id.inputFirstName)).getText().toString();
                String fName = mfirstname.substring(0, 1).toUpperCase() + mfirstname.substring(1).toLowerCase();
        final String mfullname = fName + " " + lName;
        final String memail = ((EditText)
                findViewById(R.id.signupEmail)).getText().toString();
        final String mpassword = ((EditText)
                findViewById(R.id.signupPassword)).getText().toString();
        final Integer mprofileicon = chosenProfile;

        new Thread(() -> {
//            @Override
//            public void run(){
                User user = new User(lName, fName, mfullname, memail, mpassword, mprofileicon);
                UserDAO dao = db.getUserDAO();
                long id = dao.insertUser(user);

                // checks if insert was successful
                if(id > 0){
                    user.uid = (int) id;
                    StorageActivity.setCurrentUserId(user.uid);

                    runOnUiThread(() -> {
                        Intent intent = new Intent(SignUpActivity.this, ConfirmProfileActivity.class);
                        startActivity(intent);
                    });
                } else{
                    runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Failed to save user", Toast.LENGTH_SHORT).show());
                }
        }).start();
    }

        private void checkForRequirements(){
           String nameText = firstName.getText().toString();
           String otherNameText = lastName.getText().toString();
           EditText signupEmailText = findViewById(R.id.signupEmail);
           EditText signupPasswordText = findViewById(R.id.signupPassword);
           String inputEmail = signupEmailText.getText().toString();
           String inputPassword = signupPasswordText.getText().toString();

           if (inputEmail.isEmpty() && inputPassword.isEmpty() && nameText.isEmpty() && otherNameText.isEmpty()) {
            Context ctx = SignUpActivity.this;
                Toast.makeText(ctx, "Missing First and Last Name, Email, and Password", Toast.LENGTH_SHORT).show();
            } else if (inputEmail.isEmpty() && inputPassword.isEmpty()) {
                Context ctx = SignUpActivity.this;
                Toast.makeText(ctx, "Missing Email and Password", Toast.LENGTH_SHORT).show();
            } else if (nameText.isEmpty() && inputEmail.isEmpty()) {
                Context ctx = SignUpActivity.this;
                Toast.makeText(ctx, "Missing First Name and Email", Toast.LENGTH_SHORT).show();
            } else if (otherNameText.isEmpty() && inputEmail.isEmpty()) {
                Context ctx = SignUpActivity.this;
                Toast.makeText(ctx, "Missing Last Name and Email", Toast.LENGTH_SHORT).show();
            } else if (inputPassword.isEmpty() && nameText.isEmpty()) {
                Context ctx = SignUpActivity.this;
                Toast.makeText(ctx, "Missing First Name and Password", Toast.LENGTH_SHORT).show();
            } else if (inputPassword.isEmpty() && otherNameText.isEmpty()) {
                Context ctx = SignUpActivity.this;
                Toast.makeText(ctx, "Missing Last Name and Password", Toast.LENGTH_SHORT).show();
            } else if (inputEmail.isEmpty()) {
                Context ctx = SignUpActivity.this;
                Toast.makeText(ctx, "Missing Email", Toast.LENGTH_SHORT).show();
            } else if (inputPassword.isEmpty()) {
                Context ctx = SignUpActivity.this;
                Toast.makeText(ctx, "Missing Password", Toast.LENGTH_SHORT).show();
            } else if (nameText.isEmpty()) {
                Context ctx = SignUpActivity.this;
                Toast.makeText(ctx, "Missing First Name", Toast.LENGTH_SHORT).show();
            } else if (otherNameText.isEmpty()) {
                Context ctx = SignUpActivity.this;
                Toast.makeText(ctx, "Missing Last Name", Toast.LENGTH_SHORT).show();
            } else {
                nextActivity = true;
            }
        }
    }

