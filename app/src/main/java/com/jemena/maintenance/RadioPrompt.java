package com.jemena.maintenance;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class RadioPrompt extends FormComponent {
    private ArrayList<String> options;
    private int selectedIndex;

    public RadioPrompt(Context context, String prompt, ArrayList<String> options) {
        super(context);
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
