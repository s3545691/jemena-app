package com.jemena.maintenance.model;

import android.content.Context;

import com.jemena.maintenance.model.FormComponent;

import java.util.ArrayList;

public class RadioPrompt extends FormComponent {
    private ArrayList<String> options;
    private int selectedIndex;

    public RadioPrompt(String prompt, ArrayList<String> options) {
        this.setPrompt(prompt);
        this.options = options;
        selectedIndex = -1;
    }

    public void select(int index) {
        // Do nothing if an incorrect index is given
        if (index < -1 || index >= options.size()) {
            return;
        }
        this.selectedIndex = index;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    @Override
    public String getResponse() {
        if (selectedIndex == -1) {
            return null;
        }
        return options.get(selectedIndex);
    }
}
