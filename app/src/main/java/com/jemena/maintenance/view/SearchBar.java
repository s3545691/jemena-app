package com.jemena.maintenance.view;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.persistence.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchBar {
    private boolean isSearch; // Determines if the button is in search mode or clear mode
    private EditText searchBar;
    private ImageButton searchButton;
    private ImageButton clearButton;
    private ArrayAdapter<ArrayList<HashMap<String,String>>> adapter;
    private ArrayList<HashMap<String,String>> forms;
    private DbHelper dbHelper;

    public SearchBar(Activity context, ArrayAdapter adapter, ArrayList forms, DbHelper dbHelper) {
        isSearch = true;
        this.searchBar = context.findViewById(R.id.searchbar);
        this.searchButton = context.findViewById(R.id.searchButton);
        this.clearButton = context.findViewById(R.id.clearButton);
        this.adapter = adapter;
        this.forms = forms;
        this.dbHelper = dbHelper;

        clearButton.setVisibility(View.GONE);
        configSearchButton();
        configClearButton();
    }

    private void configSearchButton() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String searchTerm = searchBar.getText().toString().toLowerCase();

                if (searchTerm.isEmpty()) {
                    adapter.notifyDataSetChanged();
                    return;
                }
                filterForms(searchTerm);
                toggleButton();
            }
        });
    }


    private void configClearButton() {
        clearButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resetFormsList();
                        searchBar.setText("");
                        toggleButton();
                    }
                }
        );
    }


    private void filterForms(String searchTerm) {
        ArrayList<HashMap> toRemove = new ArrayList();
        for (HashMap<String,String> formMap : forms) {
            String formTitle = formMap.get("title").toLowerCase();

            if (!formTitle.contains(searchTerm)) {
                toRemove.add(formMap);
            }
        }
        for (HashMap map : toRemove) {
            forms.remove(map);
        }
        adapter.notifyDataSetChanged();
    }


    private void resetFormsList() {
        ArrayList<HashMap<String,String>> formsBackup = dbHelper.getFormList();

        for (HashMap form : forms) {
            forms.remove(form);
        }

        for (HashMap<String,String> form : formsBackup) {
            forms.add(form);
        }
        adapter.notifyDataSetChanged();
    }


    private void toggleButton() {
        isSearch = isSearch ? false : true;

        if (isSearch) {
            clearButton.setVisibility(View.GONE);
            searchButton.setVisibility(View.VISIBLE);
        }
        else {
            searchButton.setVisibility(View.GONE);
            clearButton.setVisibility(View.VISIBLE);
        }
    }
}
