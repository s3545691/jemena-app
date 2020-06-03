package com.jemena.maintenance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jemena.maintenance.R;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private Button createFormButton;
    private Button viewFormButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        // Set attributes
        createFormButton = (Button) findViewById(R.id.create_form);
        viewFormButton = (Button) findViewById(R.id.view_form);
        createFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityCreateForm();
            }
        });

        viewFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityViewForm();
            }
        });

        Button editButton = findViewById(R.id.edit_form);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditFormMenu();
            }
        });
    }

        private void openActivityCreateForm() {
            startActivity(new Intent(MenuActivity.this, FormActivity.class));
        }

        private void openActivityViewForm() {
            Intent intent = new Intent(this, ExistingFormListActivity.class);
            startActivity(intent);
        }

        private void openEditFormMenu() {
            Intent intent = new Intent(this, ExistingFormListActivity.class);
            intent.putExtra("isFill", false);
            startActivity(intent);
        }
    }

    // TODO Close the database connection when the app closes

