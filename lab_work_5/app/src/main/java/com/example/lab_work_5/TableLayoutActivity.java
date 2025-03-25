package com.example.lab_work_5;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class TableLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_layout);

        Button btnSwitchToGrid = findViewById(R.id.btnSwitchToGrid);
        Button btnAddColumn = findViewById(R.id.btnAddColumn);

        // Switch to GridLayoutActivity Activity
        btnSwitchToGrid.setOnClickListener(v -> {
            Intent intent = new Intent(TableLayoutActivity.this, GridLayoutActivity.class);
            startActivity(intent);
        });

        btnAddColumn.setOnClickListener(v -> addColumn());
    }
    private void addColumn() {
        TableRow tableRow = findViewById(R.id.tableRow);

        // Create a new TextView
        TextView textView = new TextView(this);

        // Set properties to match the XML TextView
        textView.setText("New Row");
        textView.setBackgroundResource(R.drawable.blue_background);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.dark_blue));
        textView.setTextSize(15);
        textView.setTypeface(ResourcesCompat.getFont(this, R.font.inter_font), Typeface.BOLD);

        // Convert dp to pixels for padding
        int paddingVertical = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

        // Apply padding (top, left, bottom, right)
        textView.setPadding(0, paddingVertical, 0, paddingVertical);

        // Set margins for the TextView
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(6, 0, 0, 6);
        textView.setLayoutParams(layoutParams);

        // Add the TextView to the TableRow
        tableRow.addView(textView);
    }



}
