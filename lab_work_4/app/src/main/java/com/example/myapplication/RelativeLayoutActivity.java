package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RelativeLayoutActivity extends AppCompatActivity {

    private ImageView imgKitten;
    private TextView txtDescription;
    private boolean isMovedDown = false; // Track if moved

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_layout);

        // Initialize UI elements
        Button btnSwitchToConstraint = findViewById(R.id.btnSwitchToConstraint);
        Button btnMove = findViewById(R.id.btnMove);
        imgKitten = findViewById(R.id.imgKitten);
        txtDescription = findViewById(R.id.txtDescription);

        // Switch to ConstraintLayout Activity
        btnSwitchToConstraint.setOnClickListener(v -> {
            Intent intent = new Intent(RelativeLayoutActivity.this, ConstraintLayoutActivity.class);
            startActivity(intent);
        });
        // Move Image and Description Down on Button Click
        btnMove.setOnClickListener(v -> moveImage());
    }

    private void moveImage() {
        RelativeLayout.LayoutParams imgParams = (RelativeLayout.LayoutParams) imgKitten.getLayoutParams();
        RelativeLayout.LayoutParams textParams = (RelativeLayout.LayoutParams) txtDescription.getLayoutParams();

        if (!isMovedDown) {
            // Move Image and Description Below the Button
            imgParams.addRule(RelativeLayout.BELOW, R.id.btnMove);
            isMovedDown = true; // Update state
        } else {
            // Move Image and Description Back to Original Position
            imgParams.addRule(RelativeLayout.BELOW, R.id.btnSwitchToConstraint);
            isMovedDown = false; // Reset state
        }
        // Apply changes
        imgKitten.setLayoutParams(imgParams);
        txtDescription.setLayoutParams(textParams);
    }
}
