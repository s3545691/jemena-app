package com.jemena.maintenance.view.form_component_factory;

import android.text.Editable;
import android.text.TextWatcher;

import com.jemena.maintenance.model.HeaderPrompt;

import java.util.ArrayList;

public class HeaderTextWatcher implements TextWatcher {
    HeaderPrompt prompt;
    int index;

    public HeaderTextWatcher(HeaderPrompt prompt, int index) {
        this.prompt = prompt;
        this.index = index;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        ArrayList<String> responses = prompt.getResponse();
        responses.set(index, s.toString());
        prompt.setResponse(responses);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
