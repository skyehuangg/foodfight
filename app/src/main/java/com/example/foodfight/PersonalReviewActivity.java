package com.example.foodfight;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PersonalReviewActivity extends AppCompatActivity {

    //all good

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_review);
        ImageButton myButton = findViewById(R.id.myButton);
        myButton.setOnClickListener(v -> {
            Intent intent = new Intent(PersonalReviewActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        Button submit = findViewById(R.id.submitreview);
        submit.setOnClickListener(v -> {
            //Skye add Toast here
            Context ctx = PersonalReviewActivity.this;
            Toast.makeText(ctx, "Review successfully submitted.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PersonalReviewActivity.this, ThankYouActivity.class);
            startActivity(intent);
        });

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(PersonalReviewActivity.this, ReviewsActivity.class);
            startActivity(intent);
        });
    }
}