package com.jemena.maintenance.view.form_component_factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.FormComponent;

import java.util.ArrayList;

public class RadioPromptViewFactory extends FormViewFactory {
    public RadioPromptViewFactory(Context context) {
        super(context);
    }

    @Override
    protected View inflateEditView(FormComponent radioPrompt) {
        return null;
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
        return view;
    }
}
