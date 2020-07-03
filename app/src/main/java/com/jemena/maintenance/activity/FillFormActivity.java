package com.jemena.maintenance.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.FormComponent;
import com.jemena.maintenance.model.pdf.PdfWriter;
import com.jemena.maintenance.model.persistence.DbHelper;
import com.jemena.maintenance.model.persistence.JsonHelper;

import java.io.File;
import java.text.DateFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class FillFormActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    ArrayList<FormComponent> components;
    private FormArrayAdapter adapter;
    TextView title;
    boolean isNew;
    Intent intent;

    private static  final  int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_form);

        dbHelper = new DbHelper(this);
        intent = getIntent();
        isNew = intent.getBooleanExtra("isNew", true);
        title = findViewById(R.id.form_title);

        // Load up the components
        if (isNew) {
            // Add the component views to the list
            HashMap<String,String> formMap = dbHelper.getForm(intent.getLongExtra("id", -1));
            String rawJson = formMap.get("json");
            JsonHelper jsonHelper = new JsonHelper(this);
            components = jsonHelper.getComponentList(rawJson);

            // Set the title
            title = findViewById(R.id.form_title);
            title.setText(formMap.get("title"));
        }
        else {
            // Loading up an existing filled form
            long id = Long.valueOf(intent.getStringExtra("id"));
            HashMap<String,String> formMap = dbHelper.getFilledForm(id);
            String rawJson = formMap.get("json");

            JsonHelper jsonHelper = new JsonHelper(this);
            components = jsonHelper.getComponentList(rawJson);

            title.setText(formMap.get("type"));
        }

        // Configure the array adapter
        adapter = new FormArrayAdapter(this, R.id.component_list,
                components);

        for (FormComponent component : components) {
            component.setArrayAdapter(adapter);
        }
        ListView componentListView = findViewById(R.id.component_list);
        componentListView.setAdapter(adapter);

        // Configure the save button
        ImageButton saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                saveForm();
            }
        });

        // Configure pdf button
        ImageButton pdfButton = findViewById(R.id.pdfButton);
        if (isNew) {
            pdfButton.setVisibility(View.GONE);
        }
        else {
            pdfButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    savePdf();
                }
            });
        }
    }


    private void savePdf() {
        String fileName = title.getText().toString().replace(' ', '_') + ".pdf";
        File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File file = new File("sdcard/Download", fileName);

        // Overwrite if it exists already
        if (file.exists()) {
            file.delete();
        }

        // Permissions
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            PdfWriter writer = new PdfWriter(title.getText().toString());
            writer.buildPdf(this, components);
            writer.write(file);
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }


    private void saveForm() {
        if (isNew) {
            Date currDate = new Date();
            String dateTime = DateFormat.getDateInstance().format(currDate) + " " +
                    DateFormat.getTimeInstance().format(currDate);

            dbHelper.saveFilledForm(title.getText().toString(), dateTime, components);
        }
        else {
            JsonHelper jsonHelper = new JsonHelper(this);
            String jsonStr = jsonHelper.arrayListToJson(components).toString();

            HashMap formMap = new HashMap<String,String>();
            formMap.put("json", jsonStr);
            dbHelper.updateFilledForm(Long.valueOf(intent.getStringExtra("id")), formMap);
        }
        finish();
    }


    // The class responsible for taking the data for each component and putting it into a view
    private class FormArrayAdapter extends ArrayAdapter<FormComponent> {

        public FormArrayAdapter(Context context, int textViewResourceId,
                                List<FormComponent> data) {
            super(context, textViewResourceId, data);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final FormComponent component = getItem(position);
            convertView = component.getView();


            ImageButton addImageButton = findViewById(R.id.add_image_button);
            if (addImageButton != null) {
                addImageButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                    == PackageManager.PERMISSION_DENIED) {

                                requestPermissions(new String[]
                                                {Manifest.permission.READ_EXTERNAL_STORAGE},
                                        PERMISSION_CODE);
                            } else {
                                pickImage(position);
                            }
                        } else {
                            pickImage(position);
                        }
                    }
                });
            }
            return convertView;
        }
    }

    private void pickImage(int position) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra("Component index", position);
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
           int position = data.getIntExtra("Component index", -1);
           if (position != -1) {
               Uri imgUri = data.getData();

               FormComponent component = components.get(position);
           }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}


