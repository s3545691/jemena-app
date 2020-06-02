package com.jemena.maintenance.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.persistence.DataStorage;
import com.jemena.maintenance.model.persistence.FormDbHelper;

import java.util.HashMap;

public class FillFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_form);

        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 0);

        HashMap<String,String> formMap = getFormMap(id);


        // Test for intent
        TextView formId = this.findViewById(R.id.formTitle);
        formId.setText(formMap.get("title"));
    }


    private HashMap getFormMap(long id) {
        HashMap<String,String> formMap = new HashMap<>();
        FormDbHelper dbHelper = new FormDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

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

        formMap.put("title",
                cursor.getString(
                        cursor.getColumnIndexOrThrow(DataStorage.FormEntry.COLUMN_NAME_TITLE)
                )
        );
        formMap.put("json",
                cursor.getString(
                        cursor.getColumnIndexOrThrow(DataStorage.FormEntry.COLUMN_NAME_JSON)
                )
        );
        formMap.put("id", String.valueOf(id));

        return formMap;
    }
}


