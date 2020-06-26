package com.jemena.maintenance.view.form_component_factory;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.RadioPrompt;

import java.util.ArrayList;

public class RadioPromptViewFactory extends FormViewFactory<RadioPrompt> {
    public RadioPromptViewFactory(Context context) {
        super(context);
    }

    @Override
    protected View inflateEditView(final RadioPrompt radioPrompt) {
        // Set reference to model data
        ArrayList<String> options = getComponent().getOptions();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.radio_prompt_create, null);

        // Set references to UI components
        final LinearLayout optionsList = view.findViewById(R.id.option_list);
        final EditText prompt = view.findViewById(R.id.prompt);
        Button addOptionButton = view.findViewById(R.id.add_radio_button);
        Button saveButton = view.findViewById(R.id.save_button);

        // Configure prompt EditText
        prompt.setText(radioPrompt.getPrompt());
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

        // Add the removable radio options to the LinearLayout
        if (options != null) {
            for (int i=0; i < options.size(); i++) {
                optionsList.addView(getOptionView(i, optionsList));
            }
        }

        // Configure the add option button
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList options = getComponent().getOptions();

                if (options == null) {
                    options = new ArrayList<String>();
                }
                else {
                    // Save all input so far
                    for (int i=0; i < options.size(); i++) {
                        View radioView = optionsList.getChildAt(i);
                        EditText radioEditText = radioView.findViewById(R.id.removable_edit_text);
                        options.set(i, radioEditText.getText().toString());
                    }
                }

                options.add("Radio option text");
                getComponent().setOptions(options);
                getComponent().setPromptNotify(prompt.getText().toString());

                getComponent().getArrayAdapter().notifyDataSetChanged();
            }
        });

        // Configure the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Go through each EditText and set the data in the model accordingly
                getComponent().setPromptNotify(prompt.getText().toString());

                ArrayList<String> newOptions = new ArrayList<>();

                for (int i=0; i < optionsList.getChildCount(); i++) {
                    View option = optionsList.getChildAt(i);
                    EditText editText = option.findViewById(R.id.removable_edit_text);
                    newOptions.add(editText.getText().toString());
                }
                getComponent().setOptions(newOptions);

                radioPrompt.setIsEditing(false);
            }
        });
        return view;
    }

    @Override
    protected View inflateInputView(RadioPrompt radioPrompt) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        ArrayList<String> options = (ArrayList)radioPrompt.getOptions();
        View view = inflater.inflate(R.layout.radio_prompt, null);

        final RadioGroup radioGroup = view.findViewById(R.id.radio_group);
        TextView prompt = view.findViewById(R.id.prompt);
        prompt.setText(radioPrompt.getPrompt());

        // Layout parameters for newly added radio buttons
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.MATCH_PARENT
        );

        // Create and add the radio buttons to the radio group
        if (options != null) {
            for (int i=0; i < options.size(); i++) {

                final RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(options.get(i));
                int buttonId = ViewCompat.generateViewId();
                radioButton.setId(buttonId);

                radioGroup.addView(radioButton, params);

                radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            int index = radioGroup.indexOfChild(radioButton);
                            getComponent().setResponse(index);
                        }
                    }
                });
            }

            // Check the right button according to the model
            int checkedIndex = getComponent().getResponse();
            if (checkedIndex != -1) {
                RadioButton button = (RadioButton)radioGroup.getChildAt(checkedIndex);
                button.setChecked(true);
            }
        }

        return view;
    }

    private View getOptionView(final int index, final LinearLayout optionsList) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View optionView = inflater.inflate(R.layout.removable_item, null);


        final ArrayList<String> options = getComponent().getOptions();
        EditText editText = optionView.findViewById(R.id.removable_edit_text);
        ImageButton closeButton = optionView.findViewById(R.id.removable_imageButton);

        editText.setText(options.get(index));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getComponent().getOptions().set(index, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save text of the options
                for (int i=0; i < optionsList.getChildCount(); i++) {
                    // No need to set the element that will be removed
                    if (i == index) {
                        continue;
                    }
                    View currOption = optionsList.getChildAt(i);
                    EditText editText = currOption.findViewById(R.id.removable_edit_text);
                    options.set(i, editText.getText().toString());
                }
                options.remove(index);
                getComponent().setOptions(options);
            }
        });
        return optionView;
    }
}
