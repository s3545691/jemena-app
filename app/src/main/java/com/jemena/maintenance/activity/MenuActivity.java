package com.jemena.maintenance.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jemena.maintenance.R;
import com.jemena.maintenance.view.FormListAdapter;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private Button createFormButton;
    private Button viewFormButton;
    private Button fillFormButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        // Set attributes
        createFormButton = findViewById(R.id.create_form);
        viewFormButton = findViewById(R.id.view_form);
        fillFormButton = findViewById(R.id.fill_form);

        configButtons();
    }

    private void configButtons() {
        createFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        viewFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        fillFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });
    }
}
