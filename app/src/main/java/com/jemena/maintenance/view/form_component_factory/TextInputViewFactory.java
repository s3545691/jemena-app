package com.jemena.maintenance.view.form_component_factory;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.FormComponent;
import com.jemena.maintenance.model.TextInput;

import org.w3c.dom.Text;

public class TextInputViewFactory extends FormViewFactory {

    public TextInputViewFactory(Context context) {
        super(context);
    }

    @Override
    protected View inflateEditView(final FormComponent textInputPrompt) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.text_input_prompt_create, null);

        EditText prompt = view.findViewById(R.id.prompt_edit);
        prompt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputPrompt.setPrompt(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textInputPrompt.setIsEditing(true);
            }
        });

        return view;
    }

    @Override
    protected View inflateInputView(final FormComponent formComponent) {
        final TextInput textInputPrompt = (TextInput)formComponent;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.text_input_prompt, null);

        // Allow the TextInput to be updated
        EditText input = view.findViewById(R.id.input_field);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputPrompt.setResponse(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        TextView prompt = view.findViewById(R.id.prompt);
        prompt.setText(formComponent.getPrompt());
        return view;
    }
}
