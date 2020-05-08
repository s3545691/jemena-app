package com.jemena.maintenance.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jemena.maintenance.model.FormComponent;
import com.jemena.maintenance.R;
import com.jemena.maintenance.model.RadioPrompt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormActivity extends AppCompatActivity {
    private ArrayList<FormComponent> components = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        // Initialise the data
        initTestData();

        // Initialise the list
        configInterface();
    }

    private void initTestData() {
        components = new ArrayList<>();

        List<String> options = Arrays.asList(
                "Yes",
                "No",
                "Maybe"
        );

        ArrayList<String> radioOptions = new ArrayList();
        radioOptions.addAll(options);
        RadioPrompt testRadioPrompt = new RadioPrompt(
                "Is this a yes or no question",
                radioOptions
        );
        components.add(testRadioPrompt);
    }

    private void configInterface() {
        ListView componentList = this.findViewById(R.id.component_list);
        FormArrayAdapter adapter = new FormArrayAdapter(this, R.id.component_list,
                components);
        componentList.setAdapter(adapter);
    }

    // The class responsible for taking the data for each component and putting it into a view
    private class FormArrayAdapter extends ArrayAdapter<FormComponent> {
        private LayoutInflater inflater;

        public FormArrayAdapter(Context context, int textViewResourceId,
                                List<FormComponent> data) {
            super(context, textViewResourceId, data);
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // TODO: rework this once more prompt types have been added
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RadioPrompt radioPrompt = (RadioPrompt)getItem(position);
            Boolean isRecyclingView = convertView != null;
            RadioGroup radioGroup;
            TextView prompt;

            if (isRecyclingView) {

                radioGroup = (RadioGroup)convertView.findViewById(R.id.radio_group);
                // Remove any prompts that may be in the recycled view
                radioGroup.removeAllViews();
            }
            else {
                convertView = inflater.inflate(R.layout.radio_prompt, null);
                radioGroup = (RadioGroup)convertView.findViewById(R.id.radio_group);
            }

            prompt = convertView.findViewById(R.id.prompt);
            prompt.setText(radioPrompt.getPrompt());

            // Create and add the radio buttons
            radioGroup = (RadioGroup)convertView.findViewById(R.id.radio_group);
            for (int i=0; i < radioPrompt.getOptions().size(); i++) {

                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(radioPrompt.getOptions().get(i));
                radioButton.setId(ViewCompat.generateViewId());

                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT,
                        RadioGroup.LayoutParams.MATCH_PARENT
                );
                radioGroup.addView(radioButton, params);
            }
            return convertView;
        }
    }
}