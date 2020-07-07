package com.jemena.maintenance.model;

import android.content.Context;

import com.jemena.maintenance.R;
import com.jemena.maintenance.view.form_component_factory.HeaderPromptViewFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.jemena.maintenance.model.persistence.Constants.HEADER;
import static com.jemena.maintenance.model.persistence.Constants.RESPONSE;
import static com.jemena.maintenance.model.persistence.Constants.TYPE;

// The options list will hold the ids of the EditTexts in order as they appear in the xml
public class HeaderPrompt extends FormComponent<ArrayList<Integer>, ArrayList<String>> {

    public HeaderPrompt(Context context, boolean editing) {
        super(context, null, editing, new HeaderPromptViewFactory(context), null);
        setOptions(buildOptions());
        setResponse(buildEmptyResponse());
    }

    public HeaderPrompt(Context context) {
        super(context, null, true, new HeaderPromptViewFactory(context), null);
        setOptions(buildOptions());
        setResponse(buildEmptyResponse());
    }

    @Override
    public String getStringResponse() {
        return null;
    }

    @Override
    protected ArrayList<Integer> instantiateEmptyOptions() {
        return new ArrayList<Integer>();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject returnValue = new JSONObject();

        try {
            JSONArray responses = new JSONArray(getResponse());
            returnValue.put(RESPONSE, responses);
            returnValue.put(TYPE, HEADER);
            return returnValue;
        }
        catch (JSONException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    private ArrayList<Integer> buildOptions() {
        ArrayList<Integer> ids = new ArrayList();

        ids.add(R.id.equipment_no_input);
        ids.add(R.id.equipment_name_input);
        ids.add(R.id.entered_in_sap_input);
        ids.add(R.id.weather_condition_input);
        ids.add(R.id.date_performed_input);
        ids.add(R.id.team_leader_in_charge_input);
        ids.add(R.id.maintenance_crew_input);
        ids.add(R.id.recipient_in_charge_input);
        ids.add(R.id.signature_input);

        return ids;
    }

    private ArrayList<String> buildEmptyResponse() {
        ArrayList<String> emptyResponse = new ArrayList();

        for (int i=0; i < 9; i++) {
            emptyResponse.add("");
        }

        return emptyResponse;
    }
}
