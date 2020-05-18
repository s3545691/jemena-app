package com.jemena.maintenance.model;

import android.content.Context;

import com.jemena.maintenance.view.form_component_factory.TextInputViewFactory;

public class TextInput extends FormComponent<Void, String> {
    String response;

    public TextInput(Context context, String prompt, Boolean isEditing) {
        super(context, prompt, isEditing, new TextInputViewFactory(context), null);
    }

    @Override
    public String getStringResponse() {
        return response;
    }

    @Override
    protected Void instantiateEmptyOptions() {
        return null;
    }

}
