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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.persistence.DataStorage;
import com.jemena.maintenance.model.persistence.DbHelper;
import com.jemena.maintenance.model.persistence.FormDbOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExistingFormListActivity extends AppCompatActivity {
    ArrayList<HashMap<String,String>> forms;
    DbHelper dbHelper;
    FormListAdapter adapter;

    private boolean isFill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.existing_form_list);

        isFill = getIntent().getBooleanExtra("isFill", true);

        // Change the heading if need be
        if (!isFill) {
            TextView header = findViewById(R.id.heading);
            header.setText("Edit Form");
        }

        dbHelper = new DbHelper(this);
        forms = dbHelper.getFormList();
        configInterface();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }


    private void configInterface() {
        ListView list = this.findViewById(R.id.form_list);

        adapter = new FormListAdapter(this, R.id.form_list, forms);
        list.setAdapter(adapter);

        Button backButton = this.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private class FormListAdapter extends ArrayAdapter<HashMap<String,String>> {

        private LayoutInflater inflater;
        private final Class activityToLaunch = isFill ? FillFormActivity.class : FormActivity.class;

        public FormListAdapter(Context context, int textViewResourceId,
                               List<HashMap<String, String>> forms) {

            super(context, textViewResourceId, forms);
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int layoutId = isFill ? R.layout.existing_form_item : R.layout.existing_form_item_edit;

            if (convertView == null) {
                convertView = inflater.inflate(layoutId, null);
            }

            HashMap<String, String> formMap = getItem(position);

            configureText(convertView, formMap);
            configureMainButton(convertView, formMap);

            if (!isFill) {
                configDeleteButton(convertView, formMap, position);
            }

            return convertView;
        }


        private void configureText(View convertView, HashMap<String, String> form) {
            TextView title = convertView.findViewById(R.id.form_title);
            title.setText(form.get("title"));

            TextView description = convertView.findViewById(R.id.form_description);
            description.setText(form.get("desc"));
        }


        private void configureMainButton(View convertView, final HashMap<String,String> form) {

            int buttonId = isFill ? R.id.fill_button : R.id.edit_button;
            Button button = convertView.findViewById(buttonId);
            button.setTag(form.get("id"));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long id = Long.valueOf((String)view.getTag());

                    Intent intent = new Intent(view.getContext(), activityToLaunch);
                    intent.putExtra("id", id);
                    intent.putExtra("isNew", false);
                    startActivity(intent);
                }
            });
        }

        private void configDeleteButton(View view, HashMap<String,String> form, final int position) {

            ImageButton button = view.findViewById(R.id.delete_button);
            button.setTag(form.get("id"));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long id = Long.valueOf((String)view.getTag());
                    dbHelper.deleteForm(id);
                    forms.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}