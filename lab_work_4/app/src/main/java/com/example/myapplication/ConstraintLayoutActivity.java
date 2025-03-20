package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class ConstraintLayoutActivity extends AppCompatActivity {
    private boolean isMovedDown = false; // Track position

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraint_layout);
        // Switch back to RelativeLayout
        Button btnSwitchToRelative = findViewById(R.id.btnSwitchToRelative);
        btnSwitchToRelative.setOnClickListener(v -> {
            Intent intent = new Intent(ConstraintLayoutActivity.this, RelativeLayoutActivity.class);
            startActivity(intent);
        });
        // Move Image and Text
        Button btnMoveKitten = findViewById(R.id.btnMoveKitten);
        btnMoveKitten.setOnClickListener(v -> moveKitten());
    }
    private void moveKitten() {
        ConstraintLayout layout = findViewById(R.id.constraintLayout);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        if (!isMovedDown) {
            // Move ImageView down
            constraintSet.clear(R.id.kittenImage, ConstraintSet.TOP);
            constraintSet.connect(R.id.kittenImage, ConstraintSet.TOP, R.id.constraintLayout, ConstraintSet.TOP, 1000);
        } else {
            // Move ImageView back to original position
            constraintSet.clear(R.id.kittenImage, ConstraintSet.TOP);
            constraintSet.connect(R.id.kittenImage, ConstraintSet.TOP, R.id.btnSwitchToRelative, ConstraintSet.BOTTOM, 50);
        }

        constraintSet.applyTo(layout);
        isMovedDown = !isMovedDown;
    }


}
