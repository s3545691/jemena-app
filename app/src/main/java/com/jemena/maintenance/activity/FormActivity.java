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
                this,
                "Is this a yes or no question",
                radioOptions,
                false
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

            convertView = radioPrompt.getView();
            return convertView;
        }
    }
}