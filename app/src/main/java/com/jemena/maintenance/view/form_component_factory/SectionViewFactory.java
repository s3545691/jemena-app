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
import android.widget.TextView;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.Section;

import java.util.Timer;


public class SectionViewFactory extends FormViewFactory<Section> {
    // Used to gauge when the user has finished typing
    Timer timer;

    public SectionViewFactory(Context context) {
        super(context);
    }

    @Override
    protected View inflateEditView(final Section textInputPrompt) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.section, null);

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
    protected View inflateInputView(final Section formComponent) {
        final Section textInputPrompt = (Section)formComponent;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.section_view, null);

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

        // Courtesy of https://stackoverflow.com/a/8063533
        // Save when focus is lost
        input.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event != null &&
                                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (event == null || !event.isShiftPressed()) {
                                // the user is done typing.
                                formComponent.getArrayAdapter().notifyDataSetChanged();

                                return true; // consume.
                            }
                        }
                        return false; // pass on to other listeners.
                    }
                }
        );

        return view;
    }
}
