package com.jemena.maintenance.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jemena.maintenance.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExistingFormListActivity extends AppCompatActivity {
    ArrayList<String> formTitles;
    HashMap<String, String> forms;
    FormListAdapter adapter;
    ListView list;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.existing_form_list);

        generateSampleData();
        configInterface();
    }

    private void generateSampleData() {
        formTitles.add("Form 1");
        formTitles.add("Form 2");
        formTitles.add("Form 3");

        forms.put("Form 1", "This is the first form");
        forms.put("Form 2", "This is the second form");
        forms.put("Form 3", "This is the third form");
    }

    private void configInterface() {
        list = this.findViewById(R.id.form_list);

        formTitles = new ArrayList();
        adapter = new FormListAdapter(this, R.id.form_list, formTitles);
        list.setAdapter(adapter);

        configureBackButton();
    }

    private void configureBackButton() {
        backButton = this.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
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

            TextView description = convertView.findViewById(R.id.form_description);
            description.setText(forms.get(formTitles.get(position)));

        }


        private void configureFillButton(View convertView, int position) {
            ImageButton closeButton = convertView.findViewById(R.id.fill_button);
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