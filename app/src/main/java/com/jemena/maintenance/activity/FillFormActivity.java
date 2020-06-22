package com.jemena.maintenance.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.FormComponent;
import com.jemena.maintenance.model.persistence.DbHelper;
import com.jemena.maintenance.model.persistence.JsonHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FillFormActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    ArrayList<FormComponent> components;
    private FormArrayAdapter adapter;
    boolean isNew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_form);

        dbHelper = new DbHelper(this);
        Intent intent = getIntent();
        isNew = intent.getBooleanExtra("isNew", true);

        // Load up the components

        if (isNew) {
            // Add the component views to the list
            HashMap<String,String> formMap = dbHelper.getForm(intent.getLongExtra("id", -1));
            String rawJson = formMap.get("json");
            JsonHelper jsonHelper = new JsonHelper(this);
            components = jsonHelper.getComponentList(rawJson);

            // Set the title
            TextView title = findViewById(R.id.form_title);
            title.setText(formMap.get("title"));
        }
        else {
            // TODO: Loading up an existing filled form
        }

        // Configure the array adapter
        adapter = new FormArrayAdapter(this, R.id.component_list,
                components);

        for (FormComponent component : components) {
            component.setArrayAdapter(adapter);
        }
        ListView componentListView = findViewById(R.id.component_list);
        componentListView.setAdapter(adapter);
    }


    // The class responsible for taking the data for each component and putting it into a view
    private class FormArrayAdapter extends ArrayAdapter<FormComponent> {

        public FormArrayAdapter(Context context, int textViewResourceId,
                                List<FormComponent> data) {
            super(context, textViewResourceId, data);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final FormComponent component = getItem(position);
            convertView = component.getView();

            return convertView;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}


