package com.jemena.maintenance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jemena.maintenance.model.FormComponent;
import com.jemena.maintenance.R;
import com.jemena.maintenance.model.RadioPrompt;
import com.jemena.maintenance.model.TextInput;
import com.jemena.maintenance.model.persistence.DataStorage;
import com.jemena.maintenance.model.persistence.FormDbOpenHelper;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class FormActivity extends AppCompatActivity {
    private ArrayList<FormComponent> components;
    private Button addPromptButton;
    private Button saveButton;

    // Responsible for displaying each component in the form
    private FormArrayAdapter adapter;

    // The view that appears when the user wants to add a new component
    private LinearLayout addPromptList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_form);

        // TODO: Check extras to see if editing an existing form, in which case load up the necessary data from the database

        // Set attributes
        components = new ArrayList<>();
        addPromptList = findViewById(R.id.add_prompt_list);
        addPromptButton = findViewById(R.id.add_prompt_button);
        saveButton = findViewById(R.id.save_button);
        adapter = new FormArrayAdapter(this, R.id.component_list,
                components);

        configInterface();
    }

    private void configInterface() {
        // Set the adapter for the list view
        ListView componentList = this.findViewById(R.id.component_list);
        componentList.setAdapter(adapter);

        showAddPromptList(false);

        // Config button that adds a new prompt
        addPromptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddPromptList(true);
            }
        });

        // Config button that saves the form
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveForm();
            }
        });

        configAddPromptList();

    }

    private void saveForm() {
        // TODO check for input form already

        FormDbOpenHelper dbHelper = new FormDbOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        JSONArray JSONComponents = new JSONArray();

        for (FormComponent component : components) {
            JSONComponents.put(component.toJSON());
        }

        TextView title = findViewById(R.id.form_title);

        values.put(DataStorage.FormEntry.COLUMN_NAME_TITLE, title.getText().toString());
        values.put(DataStorage.FormEntry.COLUMN_NAME_DESCRIPTION, ""/* TODO add description to get */ );
        values.put(DataStorage.FormEntry.COLUMN_NAME_JSON, JSONComponents.toString());

        db.insert(DataStorage.FormEntry.TABLE_NAME, null, values);


        startActivity(new Intent(this, MenuActivity.class));

    }

    // TODO: Expand as more component types are created
    private void configAddPromptList() {
        Button addRadioPrompt = findViewById(R.id.add_prompt_radio_button);
        addRadioPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new radio prompt
                RadioPrompt radioPrompt = new RadioPrompt(
                        view.getContext(),
                        "Prompt text",
                        null,
                        true
                );
                radioPrompt.setArrayAdapter(adapter);
                components.add(radioPrompt);
                adapter.notifyDataSetChanged();

                showAddPromptList(false);
            }
        });

        Button addTextInputPrompt = findViewById(R.id.add_prompt_text_button);
        addTextInputPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new TextInput prompt
                TextInput textInput = new TextInput(view.getContext(),
                        "Prompt text",
                        true
                );
                textInput.setArrayAdapter(adapter);
                components.add(textInput);
                adapter.notifyDataSetChanged();

                showAddPromptList(false);
            }
        });

        Button cancelButton = findViewById(R.id.add_prompt_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddPromptList(false);
            }
        });
    }

    // Toggles between showing the add prompt list and the add prompt button
    private void showAddPromptList(Boolean showAddPromptList) {
        addPromptList.setVisibility(showAddPromptList ? View.VISIBLE : View.GONE);
        addPromptButton.setVisibility(showAddPromptList ? View.GONE : View.VISIBLE);
        saveButton.setVisibility(showAddPromptList ? View.GONE : View.VISIBLE);
    }

    // The class responsible for taking the data for each component and putting it into a view
    private class FormArrayAdapter extends ArrayAdapter<FormComponent> {

        public FormArrayAdapter(Context context, int textViewResourceId,
                                List<FormComponent> data) {
            super(context, textViewResourceId, data);
        }

        // TODO: rework this once more prompt types have been added and allow for recycling
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FormComponent component = getItem(position);

            convertView = component.getView();
            return convertView;
        }
    }
}