package com.jemena.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;

public class RadioPromptActivity extends AppCompatActivity {
    private TextView prompt;
    private RadioGroup radioGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radio_prompt);

        prompt = findViewById(R.id.prompt);
        radioGroup = findViewById(R.id.radio_group);

        // Retrieve the data from intent
        Intent intent = getIntent();
        String promptText = intent.getStringExtra("prompt");
        ArrayList<String> options = intent.getStringArrayListExtra("options");

        // Assign the data to the interface components
        Log.i("Main", "Prompt text: " + promptText);
        prompt.setText(promptText);

        for (int i=0; i < options.size(); i++) {

            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(options.get(i));
            radioButton.setId(ViewCompat.generateViewId());

            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.MATCH_PARENT
            );
            radioGroup.addView(radioButton, params);
        }
    }
}
