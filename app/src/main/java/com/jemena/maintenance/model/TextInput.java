package com.jemena.maintenance.model;

import android.content.Context;

import com.jemena.maintenance.view.form_component_factory.TextInputViewFactory;
import static com.jemena.maintenance.model.persistence.Constants.PROMPT;
import static com.jemena.maintenance.model.persistence.Constants.TYPE;
import static com.jemena.maintenance.model.persistence.Constants.RESPONSE;
import static com.jemena.maintenance.model.persistence.Constants.TEXT;

import org.json.JSONException;
import org.json.JSONObject;

public class TextInput extends FormComponent<Void, String> {
    private String response;

    public TextInput(Context context, String prompt, Boolean isEditing) {
        super(context, prompt, isEditing, new TextInputViewFactory(context), null);
        setResponseNotify("");
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
            returnValue.put(TYPE, TEXT);
            returnValue.put(PROMPT, getPrompt());
            returnValue.put(RESPONSE, getResponse());
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        return returnValue;
    }

}
