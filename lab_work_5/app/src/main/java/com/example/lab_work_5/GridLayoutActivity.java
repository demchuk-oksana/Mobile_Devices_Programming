package com.example.lab_work_5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;

public class GridLayoutActivity extends AppCompatActivity {

    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);

        // Initialize GridLayout and Buttons
        gridLayout = findViewById(R.id.gridLayout);
        Button btnSwitchToTable = findViewById(R.id.btnSwitchToTable);
        Button btnAddColumn = findViewById(R.id.btnAddColumn);

        // Switch to TableLayoutActivity Activity
        btnSwitchToTable.setOnClickListener(v -> {
            Intent intent = new Intent(GridLayoutActivity.this, TableLayoutActivity.class);
            startActivity(intent);
        });

        // Add a new column
        btnAddColumn.setOnClickListener(v -> addNewColumn());
    }

    private void addNewColumn() {
        // Get the current number of columns
        int currentColumnCount = gridLayout.getColumnCount();

        // Set the new column count (increment by 1)
        gridLayout.setColumnCount(currentColumnCount + 1);
    }

}
