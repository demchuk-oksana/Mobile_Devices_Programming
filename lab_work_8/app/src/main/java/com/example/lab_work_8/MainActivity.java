package com.example.lab_work_8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private PetAdapter adapter;
    private ArrayList<String> petId, petName, petType, petBreed, petPassport;
    private ArrayList<Integer> petAge;

    private Spinner spinnerFilter, spinnerPetType, spinnerBreed, spinnerPassport;
    private LinearLayout typeFilterLayout, breedFilterLayout, passportFilterLayout;
    private TextView headerText;

    private String currentPetType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        recyclerView = findViewById(R.id.recycler_pets);
        spinnerFilter = findViewById(R.id.spinner_filter);
        spinnerPetType = findViewById(R.id.spinner_pet_type);
        spinnerBreed = findViewById(R.id.spinner_breed);
        spinnerPassport = findViewById(R.id.spinner_passport);
        typeFilterLayout = findViewById(R.id.type_filter_layout);
        breedFilterLayout = findViewById(R.id.breed_filter_layout);
        passportFilterLayout = findViewById(R.id.passport_filter_layout);
        headerText = findViewById(R.id.header_text);

        // Initialize database helper
        dbHelper = new DBHelper(MainActivity.this);

        // Initialize ArrayLists
        petId = new ArrayList<>();
        petName = new ArrayList<>();
        petType = new ArrayList<>();
        petBreed = new ArrayList<>();
        petAge = new ArrayList<>();
        petPassport = new ArrayList<>();

        // Set up RecyclerView
        adapter = new PetAdapter(this, petId, petName, petType, petBreed, petAge, petPassport);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the database with some data (only if it's empty)
        initializeDatabase();

        // Set up filter spinner
        setupFilterSpinner();

        // Set up pet type spinner
        setupPetTypeSpinner();

        // Set up passport spinner
        setupPassportSpinner();

        // Display all pets by default
        displayData("all", "");
    }

    private void initializeDatabase() {
        // Check if the database is empty
        Cursor cursor = dbHelper.getAllData("all", "");

        if (cursor.getCount() == 0) {
            // Add some sample data
            dbHelper.addPet("Барсик", "Кіт", "Мейн-кун", 3, "Так");
            dbHelper.addPet("Мурчик", "Кіт", "Сіамський", 2, "Ні");
            dbHelper.addPet("Рекс", "Собака", "Німецька вівчарка", 5, "Так");
            dbHelper.addPet("Бобік", "Собака", "Чихуахуа", 1, "Ні");
            dbHelper.addPet("Зубік", "Хом'як", "Сирійський", 1, "Ні");
            dbHelper.addPet("Хома", "Хом'як", "Джунгарський", 2, "Ні");
            dbHelper.addPet("Немо", "Рибка", "Гуппі", 1, "Ні");
            dbHelper.addPet("Дорі", "Рибка", "Телескоп", 2, "Ні");
            dbHelper.addPet("Шарік", "Собака", "Лабрадор", 4, "Так");
            dbHelper.addPet("Пушок", "Кіт", "Британець", 3, "Так");
            dbHelper.addPet("Мухтар", "Собака", "Доберман", 6, "Так");
            dbHelper.addPet("Лорд", "Собака", "Хаскі", 3, "Так");
            dbHelper.addPet("Сніжок", "Кіт", "Перський", 5, "Ні");
            dbHelper.addPet("Буся", "Кіт", "Бенгальський", 2, "Так");
            dbHelper.addPet("Булька", "Рибка", "Золота рибка", 1, "Ні");

            Toast.makeText(this, "Базу даних створено та заповнено зразками", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    private void setupFilterSpinner() {
        String[] filterOptions = {"Всі тварини", "Сортувати за ім'ям (А-Я)", "Сортувати за ім'ям (Я-А)",
                "Сортувати за віком (зростання)", "Сортувати за віком (спадання)",
                "Фільтрувати за типом тварини", "Фільтрувати за наявністю паспорта"};

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filterOptions);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(filterAdapter);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeFilterLayout.setVisibility(View.GONE);
                breedFilterLayout.setVisibility(View.GONE);
                passportFilterLayout.setVisibility(View.GONE);
                currentPetType = "";

                switch (position) {
                    case 0: // All pets
                        headerText.setText("Всі тварини");
                        displayData("all", "");
                        break;
                    case 1: // Sort by name (A-Z)
                        headerText.setText("Тварини (сортування за ім'ям А-Я)");
                        displayData("name_asc", "");
                        break;
                    case 2: // Sort by name (Z-A)
                        headerText.setText("Тварини (сортування за ім'ям Я-А)");
                        displayData("name_desc", "");
                        break;
                    case 3: // Sort by age (ascending)
                        headerText.setText("Тварини (сортування за віком зростання)");
                        displayData("age_asc", "");
                        break;
                    case 4: // Sort by age (descending)
                        headerText.setText("Тварини (сортування за віком спадання)");
                        displayData("age_desc", "");
                        break;
                    case 5: // Filter by pet type
                        headerText.setText("Фільтр за типом тварини");
                        typeFilterLayout.setVisibility(View.VISIBLE);
                        setupPetTypeSpinner();
                        break;
                    case 6: // Filter by passport
                        headerText.setText("Фільтр за наявністю паспорта");
                        passportFilterLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupPetTypeSpinner() {
        List<String> types = dbHelper.getAllTypes();

        if (types.isEmpty()) {
            types.add("Немає доступних типів");
        }

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPetType.setAdapter(typeAdapter);

        spinnerPetType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = parent.getItemAtPosition(position).toString();
                currentPetType = selectedType;
                headerText.setText("Тварини типу: " + selectedType);
                displayData("type", selectedType);

                // Show breed filter for current pet type
                breedFilterLayout.setVisibility(View.VISIBLE);
                setupBreedSpinnerForType(selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupBreedSpinnerForType(String petType) {
        List<String> breeds = dbHelper.getBreedsByType(petType);

        if (breeds.isEmpty()) {
            breeds.add("Немає доступних порід");
        }

        ArrayAdapter<String> breedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, breeds);
        breedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBreed.setAdapter(breedAdapter);

        spinnerBreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedBreed = parent.getItemAtPosition(position).toString();
                if (selectedBreed.equals("Немає доступних порід")) {
                    return;
                }
                headerText.setText("Тварини типу: " + petType + ", порода: " + selectedBreed);
                displayData("type_and_breed", petType + ";" + selectedBreed);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupPassportSpinner() {
        String[] passportOptions = {"Є паспорт", "Немає паспорта"};

        ArrayAdapter<String> passportAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, passportOptions);
        passportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPassport.setAdapter(passportAdapter);

        spinnerPassport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Has passport
                        headerText.setText("Тварини з паспортом");
                        displayData("passport_yes", "");
                        break;
                    case 1: // No passport
                        headerText.setText("Тварини без паспорта");
                        displayData("passport_no", "");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void displayData(String filterType, String filterValue) {
        Cursor cursor = dbHelper.getAllData(filterType, filterValue);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Немає даних для відображення", Toast.LENGTH_SHORT).show();
            clearData();
            return;
        }

        // Clear previous data
        clearData();

        // Add new data
        while (cursor.moveToNext()) {
            petId.add(cursor.getString(0));
            petName.add(cursor.getString(1));
            petType.add(cursor.getString(2));
            petBreed.add(cursor.getString(3));
            petAge.add(cursor.getInt(4));
            petPassport.add(cursor.getString(5));
        }

        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void clearData() {
        adapter.clearData();
        petId.clear();
        petName.clear();
        petType.clear();
        petBreed.clear();
        petAge.clear();
        petPassport.clear();
    }
}