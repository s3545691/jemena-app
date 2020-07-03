package com.jemena.maintenance.view.form_component_factory;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.TextInput;

import java.util.Timer;

import static com.jemena.maintenance.view.form_component_factory.PrintConstants.PROMPT_SIZE;
import static com.jemena.maintenance.view.form_component_factory.PrintConstants.RESPONSE_SIZE;


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
        prompt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getComponent().setPrompt(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save the input to the data model
                textInputPrompt.setPromptNotify(prompt.getText().toString());
                textInputPrompt.setIsEditing(false);
            }
        });
        return view;
    }

    @Override
    public View inflatePrintView(TextInput component) {
        View view = inflateInputView(component);

        TextView promptText = view.findViewById(R.id.prompt);
        promptText.setTextSize(PROMPT_SIZE);

        EditText responseText = view.findViewById(R.id.input_field);
        responseText.setTextSize(RESPONSE_SIZE);

        return view;
    }

    @Override
    protected View inflateInputView(final TextInput formComponent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.text_input_prompt, null);

        TextView prompt = view.findViewById(R.id.prompt);
        final EditText input = view.findViewById(R.id.input_field);

        prompt.setText(formComponent.getPrompt());
        input.setText(formComponent.getResponse());

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                formComponent.setResponse(editable.toString());
            }
        });

        // Set the image if the component has one
        if (!formComponent.getImage().toString().isEmpty()) {
            ImageView image = view.findViewById(R.id.promptImage);
            image.setImageURI(formComponent.getImage());
            image.setVisibility(View.VISIBLE);
        }

        return view;
    }
}
