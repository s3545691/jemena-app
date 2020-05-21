package com.jemena.maintenance.activity;

import android.content.Intent;
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
        createFormButton = (Button) findViewById(R.id.create_form);
        viewFormButton = (Button) findViewById(R.id.view_form);
        fillFormButton = (Button) findViewById(R.id.fill_form);
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

        fillFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityFillForm();
            }
        });
           }

        public void openActivityCreateForm() {
            Intent intent = new Intent(this, RadioPromptCreateActivity.class);
            startActivity(intent);
        }

        public void openActivityViewForm() {
            Intent intent = new Intent(this, RadioPromptCreateActivity.class);
            startActivity(intent);
        }

        public void openActivityFillForm() {
            Intent intent = new Intent(this, RadioPromptCreateActivity.class);
            startActivity(intent);
        }
    }

