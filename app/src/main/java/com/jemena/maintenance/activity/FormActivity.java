package com.jemena.maintenance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jemena.maintenance.model.FormComponent;
import com.jemena.maintenance.R;
import com.jemena.maintenance.model.RadioPrompt;
import com.jemena.maintenance.model.TextInput;
import com.jemena.maintenance.model.persistence.DbHelper;
import com.jemena.maintenance.model.persistence.JsonHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormActivity extends AppCompatActivity {


    private static final boolean DIRECTION_UP = true;
    private static final boolean DIRECTION_DOWN = false;

    private ArrayList<FormComponent> components;
    private Button addPromptButton;
    private Button saveButton;
    private DbHelper dbHelper;
    private boolean isNew;
    private EditText title;

    // Responsible for displaying each component in the form
    private FormArrayAdapter adapter;

    // The view that appears when the user wants to add a new component
    private LinearLayout addPromptList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_form);

        dbHelper = new DbHelper(this);

        // Set UI component references
        addPromptList = findViewById(R.id.add_prompt_list);
        addPromptButton = findViewById(R.id.add_prompt_button);
        saveButton = findViewById(R.id.save_button);
        title = findViewById(R.id.form_title);

        // Check if this form is being newly created
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            isNew = intent.getBooleanExtra("isNew", true);
        }

        if (!isNew) {
            // Load the components
            HashMap<String,String> formMap = dbHelper.getForm(intent.getLongExtra("id", -1));
            String rawJson = formMap.get("json");
            JsonHelper jsonHelper = new JsonHelper(this);
            components = jsonHelper.getComponentList(rawJson);

            title.setText(formMap.get("title"));
        }
        else {
            components = new ArrayList<>();
        }

        adapter = new FormArrayAdapter(this, R.id.component_list,
                components);

        if (!isNew) {
            // Set the adapter of all the form components
            for (FormComponent component : components) {
                component.setArrayAdapter(adapter);
            }
        }

        configInterface();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
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
                if (isNew) {
                    dbHelper.saveForm(title.getText().toString(), components);
                }
                else {
                    // By this point the intent checks should have been done so no need to redo
                    long id = getIntent().getLongExtra("id", -1);
                    assert(id != -1);

                    // TODO: Update the JSON and title of an existing form
                    HashMap<String,String> formMap = new HashMap<>();
                    formMap.put("title", title.getText().toString());
                    formMap.put("id", String.valueOf(id));

                    JsonHelper jsonHelper = new JsonHelper(view.getContext());
                    formMap.put("json", jsonHelper.arrayListToJson(components).toString());

                    dbHelper.updateForm(id, formMap);
                }
                finish();
            }
        });
        configAddPromptList();
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

    public void movePrompt(boolean direction, int position) {
        int targetPosition = (direction)?position-1:position+1;

        FormComponent original = components.get(position);
        FormComponent swapTarget = components.get(targetPosition);


        components.add(position, swapTarget);
        components.remove(position+1);
        components.add(targetPosition, original);
        components.remove(targetPosition+1);
        adapter.notifyDataSetChanged();
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

            // 'Add' the edit button to the component
            if (!component.isEditing()) {
                ImageButton editButton = convertView.findViewById(R.id.edit_button);
                editButton.setVisibility(View.VISIBLE);
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        component.setIsEditing(true);
                    }
                });
            } else {
                ImageButton upButton = convertView.findViewById(R.id.up_button);
                upButton.setVisibility((position==0)?View.GONE:View.VISIBLE);
                upButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        movePrompt(DIRECTION_UP, position);
                    }
                });
                ImageButton downButton = convertView.findViewById(R.id.down_button);
                downButton.setVisibility((position==components.size()-1)?View.GONE:View.VISIBLE);
                downButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        movePrompt(DIRECTION_DOWN, position);
                    }
                });

            }
            return convertView;
        }
    }
}