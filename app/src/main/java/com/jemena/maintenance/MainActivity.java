package com.jemena.maintenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int NUM_TEST_FORMS = 5;
    private ArrayList<String> formNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTestData();
        initFormList();
    }

    private void initTestData() {
        for (int i=0; i < NUM_TEST_FORMS; i++) {
            formNames.add("form " + String.valueOf(i+1));
        }
    }

    private void initFormList() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.form_list);
        RecyclerView.Adapter adapter = new FormListAdapter(formNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
