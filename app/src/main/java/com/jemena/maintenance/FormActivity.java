package com.jemena.maintenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormActivity extends AppCompatActivity {
    private ArrayList<FormComponent> components = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        // TODO: Initialise test form more cleanly
        ArrayList<FormComponent> formComponents = new ArrayList<>();

        ArrayList<String> radioOptions = new ArrayList();
        radioOptions.add("Yes");
        radioOptions.add("No");
        radioOptions.add("Maybe");
        RadioPrompt testRadioPrompt = new RadioPrompt(
                this,
                "Is this a yes or no question",
                radioOptions
        );
        formComponents.add(testRadioPrompt);

        // TODO: Initialise the list more cleanly
        ListView componentList = this.findViewById(R.id.component_list);
        FormArrayAdapter adapter = new FormArrayAdapter(this, R.id.component_list, formComponents);
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