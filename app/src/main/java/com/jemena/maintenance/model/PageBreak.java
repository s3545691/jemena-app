package com.jemena.maintenance.model;

import android.content.Context;

import com.jemena.maintenance.view.form_component_factory.PageBreakViewFactory;
import com.jemena.maintenance.view.form_component_factory.TextInputViewFactory;

import org.json.JSONException;
import org.json.JSONObject;

import static com.jemena.maintenance.model.persistence.Constants.PAGE_BREAK;
import static com.jemena.maintenance.model.persistence.Constants.PROMPT;
import static com.jemena.maintenance.model.persistence.Constants.RESPONSE;
import static com.jemena.maintenance.model.persistence.Constants.TEXT;
import static com.jemena.maintenance.model.persistence.Constants.TYPE;

public class PageBreak extends FormComponent<Void, String> {
    public PageBreak(Context context) {
        super(context, null, true, new PageBreakViewFactory(context), null);
    }

    @Override
    public String getStringResponse() {
        return null;
    }

    @Override
    protected Void instantiateEmptyOptions() {
        return null;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject returnValue = new JSONObject();
        try {
            returnValue.put(TYPE, PAGE_BREAK);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        return returnValue;
    }

}
