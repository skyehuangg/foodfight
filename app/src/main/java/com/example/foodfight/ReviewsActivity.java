package com.example.foodfight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reviews);

        ImageButton myButton = findViewById(R.id.myButton);
        myButton.setOnClickListener(v -> {
                Intent intent = new Intent(ReviewsActivity.this, HomeActivity.class);
                startActivity(intent);
        });

        Button review = findViewById(R.id.personalreview);
        review.setOnClickListener(v -> {
            Intent intent = new Intent(ReviewsActivity.this, PersonalReviewActivity.class);
            startActivity(intent);
        });

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(ReviewsActivity.this, CheeseburgerActivity.class);
            startActivity(intent);
        });
    }
}