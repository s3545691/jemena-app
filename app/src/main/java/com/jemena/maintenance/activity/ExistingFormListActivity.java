package com.jemena.maintenance.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.persistence.DataStorage;
import com.jemena.maintenance.model.persistence.FormDbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExistingFormListActivity extends AppCompatActivity {
    ArrayList<String> formTitles;
    ArrayList<HashMap<String,String>> forms;
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
        initFormData();
        configInterface();
    }

    private void initFormData() {

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

        forms = new ArrayList<>();
        while (cursor.moveToNext()) {
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(DataStorage.FormEntry.COLUMN_NAME_TITLE)
            );
            String desc = cursor.getString(
                    cursor.getColumnIndexOrThrow(DataStorage.FormEntry.COLUMN_NAME_DESCRIPTION)
            );
            long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(DataStorage.FormEntry._ID)
            );

            HashMap<String,String> formMap = new HashMap<>();
            formMap.put("title", title);
            formMap.put("desc", desc);
            formMap.put("id", Long.toString(id));
            forms.add(formMap);
        }
        cursor.close();
    }

    private void configInterface() {

        list = this.findViewById(R.id.form_list);

        adapter = new FormListAdapter(this, R.id.form_list, forms);
        list.setAdapter(adapter);

        configureBackButton();
    }

    private void configureBackButton() {
        backButton = this.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private class FormListAdapter extends ArrayAdapter<HashMap<String,String>> {
        private LayoutInflater inflater;

        public FormListAdapter(Context context, int textViewResourceId, List<HashMap<String, String>> forms) {
            super(context, textViewResourceId, forms);
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.existing_form_item, null);
            }

            HashMap<String, String> formMap = getItem(position);

            configureText(convertView, formMap);
            configureFillButton(convertView, formMap);

            return convertView;
        }


        private void configureText(View convertView, HashMap<String, String> form) {
            TextView title = convertView.findViewById(R.id.form_title);
            title.setText(form.get("title"));

            TextView description = convertView.findViewById(R.id.form_description);
            description.setText(form.get("desc"));
        }


        private void configureFillButton(View convertView, HashMap<String,String> form) {
            Button fillButton = convertView.findViewById(R.id.fill_button);
            fillButton.setTag(form.get("id"));

            fillButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long id = Long.valueOf((String)view.getTag());

                    Intent intent = new Intent(view.getContext(), FillFormActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            });
        }
    }
}