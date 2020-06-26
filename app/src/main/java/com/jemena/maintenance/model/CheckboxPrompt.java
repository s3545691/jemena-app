package com.jemena.maintenance.model;

import android.content.Context;

import com.jemena.maintenance.view.form_component_factory.CheckboxPromptViewFactory;
import com.jemena.maintenance.view.form_component_factory.RadioPromptViewFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.jemena.maintenance.model.persistence.Constants.CHECKBOX;
import static com.jemena.maintenance.model.persistence.Constants.OPTIONS;
import static com.jemena.maintenance.model.persistence.Constants.PROMPT;
import static com.jemena.maintenance.model.persistence.Constants.RADIO;
import static com.jemena.maintenance.model.persistence.Constants.RESPONSE;
import static com.jemena.maintenance.model.persistence.Constants.TYPE;


public class CheckboxPrompt extends FormComponent<ArrayList<String>, ArrayList<Boolean>> {

    public CheckboxPrompt(Context context, String prompt, ArrayList<String> options,
                          Boolean isEditing) {
        super(context, prompt, isEditing, new CheckboxPromptViewFactory(context), options);
        setResponse(new ArrayList<Boolean>());

        if (options == null) {
            setOptions(new ArrayList<String>());
        }
    }

    @Override
    public String getStringResponse() {
        return null;
    }

    @Override
    protected ArrayList<String> instantiateEmptyOptions() {
        return new ArrayList<String>();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        try {
            obj.put(PROMPT, getPrompt());
            obj.put(RESPONSE, new JSONArray(getResponse()));
            obj.put(TYPE, CHECKBOX);

            JSONArray optionsJson = new JSONArray(getOptions());
            obj.put(OPTIONS, optionsJson);
        }
        catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    @Override
    public void setResponse(ArrayList<Boolean> response) {
        // Do nothing if an invalid index is given
        if (getOptions() == null || response.size() != getOptions().size()) {
            return;
        }
        this.response = response;
    }
}
