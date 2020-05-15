package com.jemena.maintenance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jemena.maintenance.model.FormComponent;
import com.jemena.maintenance.R;
import com.jemena.maintenance.model.RadioPrompt;
import com.jemena.maintenance.model.TextInput;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormActivity extends AppCompatActivity {
    private ArrayList<FormComponent> components = new ArrayList<>();
    private LinearLayout addPromptList;
    private Button addPromptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        addPromptButton = findViewById(R.id.add_prompt_button);

        // Hide the add prompt list
        addPromptList = findViewById(R.id.add_prompt_list);
        addPromptList.setVisibility(View.GONE);

        // Initialise the list
        configInterface();
    }

    private void configInterface() {
        ListView componentList = this.findViewById(R.id.component_list);
        final FormArrayAdapter adapter = new FormArrayAdapter(this, R.id.component_list,
                components);
        componentList.setAdapter(adapter);

        // Config button that adds a new prompt
        Button addButton = findViewById(R.id.add_prompt_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPromptList.setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);
            }
        });

        // Config the buttons in the add new prompt list
        Button addRadioPrompt = findViewById(R.id.add_prompt_radio_button);
        addRadioPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new radio prompt

                List<String> options = Arrays.asList(
                        "Yes",
                        "No",
                        "Maybe"
                );

                ArrayList<String> radioOptions = new ArrayList();
                radioOptions.addAll(options);
                RadioPrompt radioPrompt = new RadioPrompt(
                        view.getContext(),
                        "Is this a yes or no question",
                        radioOptions,
                        false
                );
                components.add(radioPrompt);
                adapter.notifyDataSetChanged();

                addPromptList.setVisibility(View.GONE);
                addPromptButton.setVisibility(View.VISIBLE);
            }
        });

        Button cancelButton = findViewById(R.id.add_prompt_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPromptList.setVisibility(View.GONE);
                addPromptButton.setVisibility(View.VISIBLE);
            }
        });

        Button addTextInputPrompt = findViewById(R.id.add_prompt_text_button);
        addTextInputPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new TextInput prompt
                TextInput textInput = new TextInput(view.getContext(),
                        "Respond with some text",
                        false
                        );

                components.add(textInput);
                adapter.notifyDataSetChanged();

                addPromptList.setVisibility(View.GONE);
                addPromptButton.setVisibility(View.VISIBLE);
            }
        });
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