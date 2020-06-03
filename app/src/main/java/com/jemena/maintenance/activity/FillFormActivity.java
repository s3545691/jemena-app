package com.jemena.maintenance.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.FormComponent;
import com.jemena.maintenance.model.persistence.DataStorage;
import com.jemena.maintenance.model.persistence.FormDbOpenHelper;
import com.jemena.maintenance.model.persistence.JsonHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FillFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_form);

        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 0);
        String jsonString = intent.getStringExtra("json");

        TextView formId = this.findViewById(R.id.formTitle);
        formId.setText(intent.getStringExtra("title"));

        // Add the component views to the list
        JsonHelper jsonHelper = new JsonHelper(this);
        ArrayList<FormComponent> formComponents = jsonHelper.getComponentList(jsonString);

        LinearLayout componentLinearLayout = findViewById(R.id.components);
        for (FormComponent component : formComponents) {
            if (component != null) {
                componentLinearLayout.addView(component.getView());
            }
        }
    }
}


