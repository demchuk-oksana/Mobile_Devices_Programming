package com.example.lab_work_6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class ImageActivity extends AppCompatActivity {

    ImageView imageView1, imageView2, imageView3;
    TextView titleTextView;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images);

        // Initialize views
        titleTextView = findViewById(R.id.titleTextView);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        backButton = findViewById(R.id.backButton);

        // Get the animal type passed from com.example.lab_work_6.ListViewActivity
        String animalType = getIntent().getStringExtra("animalType");

        // Set the title
        titleTextView.setText(animalType);

        // Load images based on the selected animal type
        loadImages(animalType);

        // Set click listener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to the previous activity
                finish();
            }
        });
    }

    private void loadImages(String animalType) {
        // Set images based on the selected animal type
        switch (animalType) {
            case "Cats":
                imageView1.setImageResource(R.drawable.kitten1);
                imageView2.setImageResource(R.drawable.kitten2);
                imageView3.setImageResource(R.drawable.kitten3);
                break;
            case "Dogs":
                imageView1.setImageResource(R.drawable.dog1);
                imageView2.setImageResource(R.drawable.dog2);
                imageView3.setImageResource(R.drawable.dog3);
                break;
            case "Birds":
                imageView1.setImageResource(R.drawable.bird1);
                imageView2.setImageResource(R.drawable.bird2);
                imageView3.setImageResource(R.drawable.bird3);
                break;
            case "Rabbits":
                imageView1.setImageResource(R.drawable.rabbit1);
                imageView2.setImageResource(R.drawable.rabbit2);
                imageView3.setImageResource(R.drawable.rabbit3);
                break;
            case "Hamsters":
                imageView1.setImageResource(R.drawable.hamster1);
                imageView2.setImageResource(R.drawable.hamster2);
                imageView3.setImageResource(R.drawable.hamster3);
                break;

        }
    }
}