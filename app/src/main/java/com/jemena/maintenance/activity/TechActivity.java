package com.jemena.maintenance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jemena.maintenance.R;

public class TechActivity extends AppCompatActivity {

    private Button createFormButton;
    private Button viewFormButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tech);

        // Set attributes
        viewFormButton = (Button) findViewById(R.id.view_form);

        viewFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityViewForm();
            }
        });
    }


        private void openActivityViewForm() {
            Intent intent = new Intent(this, ExistingFormListActivity.class);
            startActivity(intent);
        }
    }

    // TODO Close the database connection when the app closes

