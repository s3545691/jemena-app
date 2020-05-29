package com.jemena.maintenance.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.persistence.DataStorage;
import com.jemena.maintenance.model.persistence.FormDbHelper;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExistingFormListActivity extends AppCompatActivity {
    ArrayList<String> formTitles;
    HashMap<String, String> forms;
    FormDbHelper dbHelper;
    SQLiteDatabase formsDb;
    FormListAdapter adapter;
    ListView list;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.existing_form_list);

        dbHelper = new FormDbHelper(this);
        formsDb = dbHelper.getReadableDatabase();
        generateSampleData();
        configInterface();
    }

    private void generateSampleData() {
//        formTitles = new ArrayList<>();
//        forms = new HashMap<>();
//
//        formTitles.add("Form 1");
//        formTitles.add("Form 2");
//        formTitles.add("Form 3");
//
//        forms.put("Form 1", "This is the first form");
//        forms.put("Form 2", "This is the second form");
//        forms.put("Form 3", "This is the third form");
        String[] projection = {
                BaseColumns._ID,
                DataStorage.FormEntry.COLUMN_NAME_TITLE,
                DataStorage.FormEntry.COLUMN_NAME_DESCRIPTION
        };


        String sortOrder =
                DataStorage.FormEntry.COLUMN_NAME_TITLE + " DESC";



        Cursor cursor = formsDb.query(
                DataStorage.FormEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        formTitles = new ArrayList<>();
        while (cursor.moveToNext()) {
            String itemTitle = cursor.getString(
                    cursor.getColumnIndexOrThrow(DataStorage.FormEntry.COLUMN_NAME_TITLE));
            formTitles.add(itemTitle);
        }
        cursor.close();
    }

    private void configInterface() {

        list = this.findViewById(R.id.form_list);

        adapter = new FormListAdapter(this, R.id.form_list, formTitles);
        list.setAdapter(adapter);

        configureBackButton();
    }

    private void configureBackButton() {
        backButton = this.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    private void goBack() {
        startActivity(new Intent(ExistingFormListActivity.this, MenuActivity.class));
    }

    private class FormListAdapter extends ArrayAdapter<String> {
        private LayoutInflater inflater;

        public FormListAdapter(Context context, int textViewResourceId, List<String> forms) {
            super(context, textViewResourceId, forms);
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.existing_form_item, null);
            }

            configureText(convertView, position);
            configureFillButton(convertView, position);

            return convertView;
        }


        private void configureText(View convertView, int position) {
            TextView title = convertView.findViewById(R.id.form_title);
            title.setText(formTitles.get(position));
            title.setTag(position);
            // TODO Get Description from the database
            TextView description = convertView.findViewById(R.id.form_description);
            description.setText(forms.get(formTitles.get(position)));

        }


        private void configureFillButton(View convertView, int position) {
            Button closeButton = convertView.findViewById(R.id.fill_button);
            closeButton.setTag(position);

            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO
                }
            });
        }
    }
}