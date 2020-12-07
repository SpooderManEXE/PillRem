package com.project.pillrem;
/**
 * Created by Sulthan Nizarudin on 06-12-2020.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "medicineManager";
    private static final String TABLE_MEDICINE = "medicines";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DAY = "day";
    private static final String KEY_TIME = "time";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEDICINE_TABLE = "CREATE TABLE " + TABLE_MEDICINE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DAY + " TEXT," + KEY_TIME + " TEXT" + ")";
        db.execSQL(CREATE_MEDICINE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINE);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    void addMedicine(Medicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, medicine.getName()); // Contact Name
        values.put(KEY_DAY, medicine.getday()); // Contact Phone
        values.put(KEY_TIME, medicine.gettime()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_MEDICINE, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    Medicine getMedicine(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEDICINE, new String[] { KEY_ID,
                        KEY_NAME, KEY_DAY, KEY_TIME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Medicine medicine = new Medicine(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        db.close();
        // return contact
        return medicine;
    }

    // code to get all contacts in a list view
    public List<Medicine> getAllMedicine() {
        List<Medicine> contactList = new ArrayList<Medicine>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEDICINE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Medicine medicine = new Medicine();
                medicine.setID(Integer.parseInt(cursor.getString(0)));
                medicine.setName(cursor.getString(1));
                medicine.setday(cursor.getString(2));
                medicine.settime(cursor.getString(3));

                // Adding contact to list
                contactList.add(medicine);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return contact list
        return contactList;
    }

    // code to update the single contact
    public int updateContact(Medicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, medicine.getName());
        values.put(KEY_DAY, medicine.getday());
        values.put(KEY_TIME, medicine.gettime());


        // updating row
        return db.update(TABLE_MEDICINE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(medicine.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Medicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEDICINE, KEY_ID + " = ?",
                new String[] { String.valueOf(medicine.getID()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MEDICINE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();

        // return count
        return cursor.getCount();
    }

}
