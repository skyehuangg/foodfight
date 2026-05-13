package com.example.foodfight;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private AppDatabase db;

    //all good

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        db = AppDatabase.getInstance(this);

        Button login = findViewById(R.id.login);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        login.setOnClickListener(v -> {
            int inputEmail = email.getText().length();
            int inputPassword = password.getText().length();
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();

            if (inputEmail == 0 && inputPassword == 0) {
                Toast.makeText(LoginActivity.this, "Missing Email and Password", Toast.LENGTH_SHORT).show();
            } else if (inputEmail == 0) {
                Toast.makeText(LoginActivity.this, "Missing Email", Toast.LENGTH_SHORT).show();
            } else if (inputPassword == 0) {
                Toast.makeText(LoginActivity.this, "Missing Password", Toast.LENGTH_SHORT).show();
            } else {
                checkForAccount(userEmail, userPassword);
            }
        });

        Button Signup = findViewById(R.id.Signup);
        Signup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.peanuts), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void checkForAccount(String userEmail, String password){
        Log.d("LOGIN", "Checking login for email: " + userEmail);

        new Thread(() -> {
            UserDAO dao = db.getUserDAO();
            User user = dao.getUserByEmail(userEmail);

            Log.d("LOGIN", "User found: " + (user != null));

            boolean success = user != null && password.equals(user.getPassword());

            runOnUiThread(() -> {
                if (success) {
                    Log.d("LOGIN", "Login successful, setting user ID: " + user.uid);
                    StorageActivity.setCurrentUserId(user.uid);

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Notifications.sendDailyMenuNotification(this);
            }
        }
    }
}