package com.example.foodfight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ThankYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thank_you);

        Button button2 = findViewById(R.id.homebutton);
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(ThankYouActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        ImageButton button3 = findViewById(R.id.myButton);
        button3.setOnClickListener(v -> {
            Intent intent = new Intent(ThankYouActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}