package com.jemena.maintenance.model;

import android.content.Context;

import com.jemena.maintenance.view.form_component_factory.RadioPromptViewFactory;
import static com.jemena.maintenance.model.persistence.Constants.OPTIONS;
import static com.jemena.maintenance.model.persistence.Constants.RESPONSE;
import static com.jemena.maintenance.model.persistence.Constants.PROMPT;
import static com.jemena.maintenance.model.persistence.Constants.TYPE;
import static com.jemena.maintenance.model.persistence.Constants.RADIO;

import org.json.*;

import java.util.ArrayList;



public class RadioPrompt extends FormComponent<ArrayList<String>, Integer> {

    public RadioPrompt(Context context, String prompt, ArrayList<String> options,
                       Boolean isEditing) {
        super(context, prompt, isEditing, new RadioPromptViewFactory(context), options);
        setResponse(-1);

        if (options == null) {
            setOptions(new ArrayList<String>());
        }
    }

    @Override
    public String getStringResponse() {
        if (getResponse() == -1) {
            return null;
        }
        return getOptions().get(getResponse());
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
            obj.put(RESPONSE, getResponse());
            obj.put(OPTIONS, getOptions());
            obj.put(TYPE, RADIO);
        }
        catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    @Override
    public void setResponse(Integer index) {
        // Do nothing if an invalid index is given
        if (getOptions() == null || index < -1 || index >= getOptions().size()) {
            return;
        }
        this.response = index;
    }
}
