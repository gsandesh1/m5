package com.example.m5;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.m5.database.Students;

/**************************************************************************************************
 * Class Database Handler provides methods to create and update database tables and columns
 *************************************************************************************************/

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "waiting_list_db";

    // Constructors
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Waiting list table
        db.execSQL(Students.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Students.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**********************************************************************************************
     * Inserts new name into the Waiting list
     ***********************************************************************************************/
    public long insertEntry(Students entry) {

        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // 'id' will be inserted automatically. No need to add it
        values.put(Students.COLUMN_STUDENT_NAME, entry.getName());
        values.put(Students.COLUMN_PRIORITY, entry.getPriority());

        // insert row
        long id = db.insert(Students.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    /**********************************************************************************************
     * Fetches the Entry for the supplied ID
     ***********************************************************************************************/
    public Students getEntry(long id) {

        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Students.TABLE_NAME,
                new String[]{Students.COLUMN_ID, Students.COLUMN_STUDENT_NAME, Students.COLUMN_PRIORITY},
                Students.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Students entry = new Students(
                cursor.getInt(cursor.getColumnIndex(Students.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Students.COLUMN_STUDENT_NAME)),
                cursor.getString(cursor.getColumnIndex(Students.COLUMN_PRIORITY)));

        // close the db connection
        cursor.close();

        return entry;
    }

    /*********************************************************************************************
     * Returns all the entries in the Waiting list table
     ***********************************************************************************************/
    public List<Students> getAllEntries() {
        List<Students> student = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Students.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Students entry = new Students();

                // Add each value into entry object
                entry.setId(cursor.getInt(cursor.getColumnIndex(Students.COLUMN_ID)));
                entry.setName(cursor.getString(cursor.getColumnIndex(Students.COLUMN_STUDENT_NAME)));
                entry.setPriority(cursor.getString(cursor.getColumnIndex(Students.COLUMN_PRIORITY)));

                // Add the entry object into the list
                student.add(entry);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return waiting list
        return student;
    }

    /**********************************************************************************************
     * Get count of the entries in the Waiting List
     ***********************************************************************************************/
    public int getNameCount() {

        // Form the query as SELECT *
        String countQuery = "SELECT  * FROM " + Students.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        // To get count of values returned
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /*********************************************************************************************
     * Update the entry in waiting list with supplied information
     ***********************************************************************************************/
    public int updateEntry(Students entry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Students.COLUMN_STUDENT_NAME, entry.getName());
        values.put(Students.COLUMN_PRIORITY, entry.getPriority());

        // updating row
        return db.update(Students.TABLE_NAME, values, Students.COLUMN_ID + " = ?",
                new String[]{String.valueOf(entry.getId())});
    }

    /*********************************************************************************************
     * Deletes a particular entry from the Waiting List
     ***********************************************************************************************/
    public void deleteEntry(Students entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Students.TABLE_NAME, Students.COLUMN_ID + " = ?",
                new String[]{String.valueOf(entry.getId())});
        db.close();
    }
}
/************************************ End of class DatabaseHelper *******************************/