package com.jemena.maintenance.view.form_component_factory;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.FormComponent;
import com.jemena.maintenance.model.RadioPrompt;

import java.util.ArrayList;

public class RadioPromptViewFactory extends FormViewFactory<RadioPrompt> {
    public RadioPromptViewFactory(Context context) {
        super(context);
    }

    @Override
    protected View inflateEditView(final FormComponent radioPrompt) {
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

        // Add the removable radio options to the LinearLayout
        if (options != null) {
            for (int i=0; i < options.size(); i++) {
                optionsList.addView(getOptionView(i));
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
                options.add("Radio option text");
                getComponent().setOptions(options);
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

                radioPrompt.setIsEditing(false);
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

        // Layout parameters for newly added radio buttons
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.MATCH_PARENT
        );

        // Create and add the radio buttons to the radio group
        if (options != null) {
            for (int i=0; i < options.size(); i++) {

                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(options.get(i));
                radioButton.setId(ViewCompat.generateViewId());
                radioGroup.addView(radioButton, params);
            }
        }

        return view;
    }

    private View getOptionView(final int index) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.removable_item, null);


        final ArrayList<String> options = getComponent().getOptions();
        EditText editText = view.findViewById(R.id.removable_edit_text);
        ImageButton closeButton = view.findViewById(R.id.removable_imageButton);

        editText.setText(options.get(index));

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                options.remove(index);
                getComponent().setOptions(options);
            }
        });

        return view;
    }
}
