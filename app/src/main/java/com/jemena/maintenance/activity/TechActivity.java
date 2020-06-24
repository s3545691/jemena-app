package com.jemena.maintenance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jemena.maintenance.R;

public class TechActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tech);

        Button fillNewForm = findViewById(R.id.fill_new_form);
        Button editFilledForm = findViewById(R.id.edit_filled_form);

        fillNewForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityFillNewForm();
            }
        });
        editFilledForm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openActivityEditFilledForm();
            }
        });
    }


    private void openActivityFillNewForm() {
        Intent intent = new Intent(this, ExistingFormListActivity.class);
        intent.putExtra("isFill", true);
        startActivity(intent);
    }


    private void openActivityEditFilledForm() {
        Intent intent = new Intent(this, FilledFormListActivity.class);
        startActivity(intent);
    }
}

