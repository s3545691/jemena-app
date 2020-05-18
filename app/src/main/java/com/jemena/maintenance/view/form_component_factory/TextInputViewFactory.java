package com.jemena.maintenance.view.form_component_factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.TextInput;


public class TextInputViewFactory extends FormViewFactory<TextInput> {

    public TextInputViewFactory(Context context) {
        super(context);
    }

    @Override
    protected View inflateEditView(final TextInput textInputPrompt) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.text_input_prompt_create, null);

        final EditText prompt = view.findViewById(R.id.prompt_edit);
        prompt.setText(textInputPrompt.getPrompt());

        Button saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save the input to the data model
                textInputPrompt.setPrompt(prompt.getText().toString());
                textInputPrompt.setIsEditing(false);
            }
        });
        return view;
    }

    @Override
    protected View inflateInputView(final TextInput formComponent) {
        final TextInput textInputPrompt = (TextInput)formComponent;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.text_input_prompt, null);

        TextView prompt = view.findViewById(R.id.prompt);
        final EditText input = view.findViewById(R.id.input_field);

        prompt.setText(formComponent.getPrompt());
        return view;
    }
}
