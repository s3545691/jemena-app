package com.jemena.maintenance.view.form_component_factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.FormComponent;
import com.jemena.maintenance.model.RadioPrompt;

import java.util.ArrayList;
import java.util.List;

public class RadioPromptViewFactory extends FormViewFactory {
    public RadioPromptViewFactory(Context context) {
        super(context);
    }

    @Override
    protected View inflateEditView(FormComponent radioPrompt) {
        RadioPrompt radio = (RadioPrompt)radioPrompt;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.radio_prompt_create, null);

        ArrayList<String> options = radio.getOptions();
        ListView optionsList = view.findViewById(R.id.option_list);

        // Create and add the EditTexts to the list
        if (options != null) {
            for (int i=0; i < options.size(); i++) {

                EditText editText = new EditText(getContext());
                editText.setText(options.get(i));
                editText.setId(ViewCompat.generateViewId());

                optionsList.addView(editText);
            }
        }

        // Configure the add option button
        Button addOptionButton = view.findViewById(R.id.add_radio_button);
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    @Override
    protected View inflateInputView(FormComponent radioPrompt) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        ArrayList<String> options = (ArrayList)radioPrompt.getOptions();
        View view = inflater.inflate(R.layout.radio_prompt, null);

        RadioGroup radioGroup = view.findViewById(R.id.radio_group);
        TextView prompt = view.findViewById(R.id.prompt);
        prompt.setText(radioPrompt.getPrompt());


        // Create and add the radio buttons to the radio group
        if (options != null) {
            for (int i=0; i < options.size(); i++) {

                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(options.get(i));
                radioButton.setId(ViewCompat.generateViewId());

                // Layout parameters for newly added radio buttons
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT,
                        RadioGroup.LayoutParams.MATCH_PARENT
                );

                radioGroup.addView(radioButton, params);
            }
        }

        return view;
    }
}
