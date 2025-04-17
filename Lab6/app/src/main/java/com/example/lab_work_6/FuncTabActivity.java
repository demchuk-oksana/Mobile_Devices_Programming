package com.example.lab_work_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.*;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;

public class FuncTabActivity extends AppCompatActivity {

    EditText startInput, endInput, stepInput;
    Button calculateButton, switchActivityButton ;
    GridView gridView;
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.funtab);

        // Toolbar with back navigation
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(FuncTabActivity.this, ListViewActivity.class));
        });

        startInput = findViewById(R.id.startInput);
        endInput = findViewById(R.id.endInput);
        stepInput = findViewById(R.id.stepInput);
        calculateButton = findViewById(R.id.calculateButton);
        gridView = findViewById(R.id.gridView);
        lineChart = findViewById(R.id.lineChart);
        lineChart.setNoDataTextColor(R.color.dark_blue);
        switchActivityButton = findViewById(R.id.switchActivityButton);

        // Set onClickListener for the switchActivityButton
        switchActivityButton.setOnClickListener(v -> {
            // Intent to switch to another activity
            Intent intent = new Intent(FuncTabActivity.this, ListViewActivity.class);  // Replace with your target activity
            startActivity(intent);
        });

        calculateButton.setOnClickListener(v -> {
            String startStr = startInput.getText().toString();
            String endStr = endInput.getText().toString();
            String stepStr = stepInput.getText().toString();

            if (startStr.isEmpty() || endStr.isEmpty() || stepStr.isEmpty()) {
                Toast.makeText(FuncTabActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double start = Double.parseDouble(startStr);
            double end = Double.parseDouble(endStr);
            double step = Double.parseDouble(stepStr);

            ArrayList<String> tableData = new ArrayList<>();
            ArrayList<Entry> entries = new ArrayList<>();

            for (double x = start; x <= end; x += step) {
                double y = Math.sin(x); // You can replace this with any function
                tableData.add(String.format("x = %.2f", x));
                tableData.add(String.format("y = %.2f", y));
                entries.add(new Entry((float) x, (float) y));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    FuncTabActivity.this,
                    android.R.layout.simple_list_item_1,
                    tableData
            );
            gridView.setAdapter(adapter);

            LineDataSet dataSet = new LineDataSet(entries, "f(x) = sin(x)");
            dataSet.setColor(getResources().getColor(R.color.medium_blue));
            dataSet.setValueTextColor(getResources().getColor(R.color.dark_blue));
            dataSet.setLineWidth(2f);
            dataSet.setCircleRadius(3f);
            dataSet.setDrawValues(false);

            LineData lineData = new LineData(dataSet);
            lineChart.setData(lineData);
            lineChart.invalidate(); // Refresh the chart
        });
    }
}
