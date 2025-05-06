package com.example.lab_work_8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "PetsDatabase.db";
    public static final int DATABASE_VERSION = 1;

    // Table name
    private static final String TABLE_NAME = "pets";

    // Column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_BREED = "breed";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_PASSPORT = "has_passport";

    // Basic query to get all data
    private static final String QUERY_ALL = "SELECT * FROM " + TABLE_NAME;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_BREED + " TEXT, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_PASSPORT + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Add a new pet to the database
    public void addPet(String name, String type, String breed, int age, String hasPassport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_BREED, breed);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_PASSPORT, hasPassport);

        db.insert(TABLE_NAME, null, cv);
    }

    // Get all pets from the database based on filter type
    public Cursor getAllData(String filterType, String filterValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        switch (filterType) {
            case "all":
                cursor = db.rawQuery(QUERY_ALL, null);
                break;
            case "name_asc":
                cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_NAME + " ASC", null);
                break;
            case "name_desc":
                cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_NAME + " DESC", null);
                break;
            case "age_asc":
                cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_AGE + " ASC", null);
                break;
            case "age_desc":
                cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_AGE + " DESC", null);
                break;
            case "breed":
                cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_BREED + " = ?", new String[]{filterValue});
                break;
            case "passport_yes":
                cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PASSPORT + " = 'Так'", null);
                break;
            case "passport_no":
                cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PASSPORT + " = 'Ні'", null);
                break;
            case "type":
                cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + " = ?", new String[]{filterValue});
                break;
            case "type_and_breed":
                cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + " = ? AND " + COLUMN_BREED + " = ?",
                        new String[]{filterValue.split(";")[0], filterValue.split(";")[1]});
                break;
        }

        return cursor;
    }

    // Get all unique breeds from the database for a specific pet type
    public List<String> getBreedsByType(String petType) {
        List<String> breeds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_BREED + " FROM " + TABLE_NAME +
                        " WHERE " + COLUMN_TYPE + " = ? ORDER BY " + COLUMN_BREED + " ASC",
                new String[]{petType});

        if (cursor.moveToFirst()) {
            do {
                breeds.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return breeds;
    }

    // Get all unique breeds from the database
    public List<String> getAllBreeds() {
        List<String> breeds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_BREED + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_BREED + " ASC", null);

        if (cursor.moveToFirst()) {
            do {
                breeds.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return breeds;
    }

    // Get all unique pet types from the database
    public List<String> getAllTypes() {
        List<String> types = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_TYPE + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_TYPE + " ASC", null);

        if (cursor.moveToFirst()) {
            do {
                types.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return types;
    }
}