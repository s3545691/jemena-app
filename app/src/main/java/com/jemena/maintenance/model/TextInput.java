package com.jemena.maintenance.model;

import android.content.Context;

import com.jemena.maintenance.view.form_component_factory.TextInputViewFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class TextInput extends FormComponent<Void, String> {
    private String response;

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

    @Override
    public JSONObject toJSON() {
        JSONObject returnValue = new JSONObject();
        try {
            returnValue.put("type", "Text");
            returnValue.put("prompt", getPrompt());
            returnValue.put("response", response);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        return returnValue;
    }

}
