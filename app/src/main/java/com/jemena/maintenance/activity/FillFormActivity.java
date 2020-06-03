package com.jemena.maintenance.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.persistence.DataStorage;
import com.jemena.maintenance.model.persistence.FormDbOpenHelper;

import java.util.HashMap;

public class FillFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_form);

        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 0);

        // Test for intent
        TextView formId = this.findViewById(R.id.formTitle);
        formId.setText(intent.getStringExtra("title"));

        TextView jsonText = findViewById(R.id.json);
        jsonText.setText(intent.getStringExtra("json"));
    }
}


