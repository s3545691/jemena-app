package com.jemena.maintenance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jemena.maintenance.R;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        // Set attributes
        Button createFormButton = (Button) findViewById(R.id.create_form);
        createFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityCreateForm();
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

            Intent intent = new Intent(this, FormActivity.class);
            intent.putExtra("isNew", true);

            startActivity(intent);
        }

        private void openEditFormMenu() {
            Intent intent = new Intent(this, ExistingFormListActivity.class);
            intent.putExtra("isFill", false);
            startActivity(intent);
        }
    }

