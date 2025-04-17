package com.example.lab_work_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class ListViewActivity extends AppCompatActivity {

    ListView listView;
    Button switchActivityButton;

    // Animal types to display in the ListView
    String[] animalTypes = {"Cats", "Dogs", "Birds", "Rabbits", "Hamsters"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        // Set custom MaterialToolbar as ActionBar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);  // Optionally hide the title if needed


        // Initialize the ListView
        listView = findViewById(R.id.animalListView);

        // Initialize the Button
        switchActivityButton = findViewById(R.id.switchActivityButton);

        // Set up the adapter for the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                animalTypes
        );

        listView.setAdapter(adapter);

        // Set click listener for ListView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected animal type
                String selectedAnimal = animalTypes[position];

                // Create intent to open the ImageActivity
                Intent intent = new Intent(ListViewActivity.this, ImageActivity.class);

                // Pass the selected animal type to the ImageActivity
                intent.putExtra("animalType", selectedAnimal);
                startActivity(intent);
            }
        });

        // Set click listener for Switch Activity button
        switchActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This will be implemented later to switch to a different activity
                Intent intent = new Intent(ListViewActivity.this, FuncTabActivity.class);
                startActivity(intent);
            }
        });
    }
}