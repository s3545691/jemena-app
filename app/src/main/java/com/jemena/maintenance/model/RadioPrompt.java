package com.jemena.maintenance.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.jemena.maintenance.R;
import com.jemena.maintenance.view.form_component_factory.RadioPromptViewFactory;


import java.util.ArrayList;

public class RadioPrompt extends FormComponent<ArrayList<String>, Integer> {
    private int selectedIndex;

    public RadioPrompt(Context context, String prompt, ArrayList<String> options,
                       Boolean isEditing) {
        super(context, prompt, isEditing, new RadioPromptViewFactory(context), options);
        selectedIndex = -1;

        // Create the view for the component
    }

    public void select(int index) {
        // Do nothing if an invalid index is given
        if (index < -1 || index >= getOptions().size()) {
            return;
        }
        this.selectedIndex = index;
    }

    @Override
    public String getResponse() {
        if (selectedIndex == -1) {
            return null;
        }
        return getOptions().get(selectedIndex);
    }

    @Override
    public void setResponse(Integer response) {
        select(response);
    }

}
