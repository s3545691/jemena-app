package com.jemena.maintenance.model.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.jemena.maintenance.model.FormComponent;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper {

    private FormDbOpenHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public DbHelper(Context context) {
        dbHelper = new FormDbOpenHelper(context);
        this.context = context;
        db = dbHelper.getWritableDatabase();
    }

    public HashMap<String,String> getForm(long id) {

        HashMap<String,String> form = new HashMap<>();

        // Setup the WHERE filter
        String selection = DataStorage.FormEntry._ID + " = ?";
        String[] args = { String.valueOf(id) };

        Cursor cursor = db.query(
                DataStorage.FormEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                args,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        cursor.moveToNext();

        form.put("title",
                cursor.getString(
                        cursor.getColumnIndexOrThrow(DataStorage.FormEntry.COLUMN_NAME_TITLE)
                )
        );
        form.put("json",
                cursor.getString(
                        cursor.getColumnIndexOrThrow(DataStorage.FormEntry.COLUMN_NAME_JSON)
                )
        );
        form.put("id", String.valueOf(id));

        return form;
    }


    public ArrayList<HashMap<String,String>> getFormList() {
        ArrayList<HashMap<String, String>> formList = new ArrayList<>();

        String[] projection = {
                BaseColumns._ID,
                DataStorage.FormEntry.COLUMN_NAME_TITLE,
                DataStorage.FormEntry.COLUMN_NAME_JSON
        };

        String sortOrder =
                DataStorage.FormEntry.COLUMN_NAME_TITLE + " DESC";

        Cursor cursor = db.query(
                DataStorage.FormEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        while (cursor.moveToNext()) {

            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(DataStorage.FormEntry.COLUMN_NAME_TITLE)
            );
            long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(DataStorage.FormEntry._ID)
            );
            String json = cursor.getString(
                    cursor.getColumnIndexOrThrow(DataStorage.FormEntry.COLUMN_NAME_JSON)
            );

            HashMap<String, String> formMap = new HashMap<>();
            formMap.put("title", title);
            formMap.put("id", Long.toString(id));
            formMap.put("json", json);
            formList.add(formMap);
        }
        cursor.close();

        return formList;
    }


    // Returns the number of rows that were deleted
    public int deleteForm(long id) {

        String selection = DataStorage.FormEntry._ID + " LIKE ?";
        String selectionArgs[] = {String.valueOf(id)};

        return db.delete(DataStorage.FormEntry.TABLE_NAME, selection, selectionArgs);
    }


    public void saveForm(String formTitle, ArrayList<FormComponent> components) {
        JsonHelper jsonHelper = new JsonHelper(context);
        JSONArray JSONComponents = jsonHelper.arrayListToJson(components);

        ContentValues values = new ContentValues();
        values.put(DataStorage.FormEntry.COLUMN_NAME_TITLE, formTitle);
        values.put(DataStorage.FormEntry.COLUMN_NAME_JSON, JSONComponents.toString());

        db.insert(DataStorage.FormEntry.TABLE_NAME, null, values);
    }


    public void updateForm(long id, HashMap<String,String> newValues) {

        ContentValues values = new ContentValues();

        if (newValues.containsKey("title")) {
            values.put(DataStorage.FormEntry.COLUMN_NAME_TITLE, newValues.get("title"));
        }
        if (newValues.containsKey("json")) {
            values.put(DataStorage.FormEntry.COLUMN_NAME_JSON, newValues.get("json"));
        }

        String selection = DataStorage.FormEntry._ID + " LIKE ?";
        String selectionArgs[] = { String.valueOf(id) };

        db.update(
                DataStorage.FormEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }


    public void close() {
        dbHelper.close();
    }
}
