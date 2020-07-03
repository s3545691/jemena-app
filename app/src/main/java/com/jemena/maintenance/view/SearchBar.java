package com.jemena.maintenance.view;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.FormComponent;
import com.jemena.maintenance.model.persistence.DbHelper;
import com.jemena.maintenance.model.persistence.JsonHelper;

import java.text.Normalizer;
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
    boolean isFilled;

    public SearchBar(Activity context, ArrayAdapter adapter, ArrayList forms, DbHelper dbHelper, boolean isFilled) {
        isSearch = true;
        this.searchBar = context.findViewById(R.id.searchbar);
        this.searchButton = context.findViewById(R.id.searchButton);
        this.clearButton = context.findViewById(R.id.clearButton);
        this.adapter = adapter;
        this.forms = forms;
        this.dbHelper = dbHelper;
        this.isFilled = isFilled;

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
        ArrayList<HashMap> inPrompt = new ArrayList<>();
        for (HashMap<String,String> formMap : forms) {
            String name = isFilled ? "type" : "title";
            String formTitle = formMap.get(name).toLowerCase();

            if (!formTitle.contains(searchTerm)) {
                toRemove.add(formMap);
                if (termInPrompt(formMap, searchTerm)) {
                    inPrompt.add(formMap);
                }
            }
        }
        for (HashMap map : toRemove) {
            forms.remove(map);
        }
        for (HashMap map : inPrompt) {
            forms.add(map);
        }
        adapter.notifyDataSetChanged();
    }

    private boolean termInPrompt(HashMap<String,String> formMap, String searchTerm) {
        String rawJson = formMap.get("json").toLowerCase();
        System.out.println(rawJson);
        return rawJson.contains(searchTerm);
    }

    private void resetFormsList() {
        ArrayList<HashMap<String,String>> formsBackup = isFilled ? dbHelper.getFilledFormList() : dbHelper.getFormList();

        forms.clear();
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
