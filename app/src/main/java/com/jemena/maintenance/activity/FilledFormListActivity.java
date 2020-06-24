package com.jemena.maintenance.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.persistence.DbHelper;
import com.jemena.maintenance.view.SearchBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilledFormListActivity extends AppCompatActivity {
    DbHelper dbHelper;
    ArrayList<HashMap<String,String>> forms;
    FilledFormListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.existing_form_list);

        dbHelper = new DbHelper(this);
        forms = dbHelper.getFilledFormList();

        adapter = new FilledFormListAdapter(this, R.layout.filled_form_item, forms);

        ListView formList = findViewById(R.id.form_list);
        formList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void configInterface() {
        ListView list = this.findViewById(R.id.form_list);

        adapter = new FilledFormListAdapter(this, R.id.form_list, forms);
        list.setAdapter(adapter);

        Button backButton = this.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SearchBar searchBar = new SearchBar(this, adapter, forms, dbHelper);
    }


    private class FilledFormListAdapter extends ArrayAdapter<HashMap<String,String>> {
        private LayoutInflater inflater;

        public FilledFormListAdapter(Context context, int textViewResourceId,
                               List<HashMap<String, String>> forms) {

            super(context, textViewResourceId, forms);
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.filled_form_item, null);
            }
            HashMap<String,String> form = getItem(position);
            configText(convertView, form.get("date"), form.get("type"));
            configButtons(convertView, form.get("id"), position);

            return convertView;
        }


        private void configText(View convertView, String date, String type) {
            TextView dateLabel = convertView.findViewById(R.id.date);
            dateLabel.setText(date);

            TextView typeLabel = convertView.findViewById(R.id.type);
            typeLabel.setText(type);
        }


        private void configButtons(View convertView, final String id, final int position) {
            Button fillButton = convertView.findViewById(R.id.fill_button);
            fillButton.setTag(id);
            fillButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), FillFormActivity.class);
                    intent.putExtra("id", (String)view.getTag());
                    intent.putExtra("isNew", false);
                    startActivity(intent);
                }
            });


            ImageButton deleteButton = convertView.findViewById(R.id.deleteButton);
            deleteButton.setTag(id);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long id = Long.valueOf((String)view.getTag());
                    dbHelper.deleteFilledForm(id);
                    forms.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
