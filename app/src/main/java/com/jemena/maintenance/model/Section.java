package com.jemena.maintenance.model;

import android.content.Context;

import com.jemena.maintenance.view.form_component_factory.SectionViewFactory;
import static com.jemena.maintenance.model.persistence.Constants.PROMPT;
import static com.jemena.maintenance.model.persistence.Constants.TYPE;
import static com.jemena.maintenance.model.persistence.Constants.RESPONSE;
import static com.jemena.maintenance.model.persistence.Constants.SECTION;

import org.json.JSONException;
import org.json.JSONObject;

public class Section extends FormComponent<Void, String> {
    private String response;

    public Section(Context context, String prompt, Boolean isEditing) {
        super(context, prompt, isEditing, new SectionViewFactory(context), null);
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
            returnValue.put(TYPE, SECTION);
            returnValue.put(PROMPT, getPrompt());
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        return returnValue;
    }

}
