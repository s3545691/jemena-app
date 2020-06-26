package com.jemena.maintenance.view.form_component_factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.CheckboxPrompt;

import java.util.ArrayList;

public class CheckboxPromptViewFactory extends FormViewFactory<CheckboxPrompt> {
    public CheckboxPromptViewFactory(Context context) {
        super(context);
    }

    @Override
    protected View inflateEditView(final CheckboxPrompt checkboxPrompt) {
        // Set reference to model data
        ArrayList<String> options = getComponent().getOptions();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.checkbox_prompt_create, null);

        // Set references to UI components
        final LinearLayout optionsList = view.findViewById(R.id.option_list);
        final EditText prompt = view.findViewById(R.id.prompt);
        Button addOptionButton = view.findViewById(R.id.add_checkbox_button);
        Button saveButton = view.findViewById(R.id.save_button);

        // Configure prompt EditText
        prompt.setText(checkboxPrompt.getPrompt());

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
                        View checkboxListView = optionsList.getChildAt(i);
                        EditText checkboxEditText = checkboxListView.findViewById(R.id.removable_edit_text);
                        options.set(i, checkboxEditText.getText().toString());
                    }
                }
                getComponent().getResponse().add(false);


                options.add("Checkbox option text");
                getComponent().setOptions(options);
                getComponent().setPrompt(prompt.getText().toString());
                getComponent().getArrayAdapter().notifyDataSetChanged();
            }
        });

        // Configure the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Go through each EditText and set the data in the model accordingly
                getComponent().setPrompt(prompt.getText().toString());

                ArrayList<String> newOptions = new ArrayList<>();

                for (int i=0; i < optionsList.getChildCount(); i++) {
                    View option = optionsList.getChildAt(i);
                    EditText editText = option.findViewById(R.id.removable_edit_text);
                    newOptions.add(editText.getText().toString());
                }
                getComponent().setOptions(newOptions);

                checkboxPrompt.setIsEditing(false);
            }
        });
        return view;
    }

    @Override
    protected View inflateInputView(CheckboxPrompt checkboxPrompt) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        ArrayList<String> options = (ArrayList)checkboxPrompt.getOptions();
        View view = inflater.inflate(R.layout.checkbox_prompt, null);

        final LinearLayout checkboxGroup = view.findViewById(R.id.checkbox_group);
        TextView prompt = view.findViewById(R.id.prompt);
        prompt.setText(checkboxPrompt.getPrompt());

        // Create and add the radio buttons to the radio group
        if (options != null) {
            for (int i=0; i < options.size(); i++) {

                final CheckBox checkBox = new CheckBox(getContext());
                checkBox.setText(options.get(i));

                int buttonId = ViewCompat.generateViewId();
                checkBox.setId(buttonId);

                checkboxGroup.addView(checkBox);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            int index = checkboxGroup.indexOfChild(checkBox);
                            ArrayList<Boolean> response = getComponent().getResponse();
                            response.set(index, isChecked);
                            getComponent().setResponse(response);
                    }
                });
            }

            ArrayList<Boolean> response = getComponent().getResponse();

            for (int i = 0; i < response.size(); ++i) {
                CheckBox checkBox = (CheckBox) checkboxGroup.getChildAt(i);
                    checkBox.setChecked(response.get(i));
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
