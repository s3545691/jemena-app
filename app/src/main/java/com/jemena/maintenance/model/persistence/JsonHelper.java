package com.jemena.maintenance.model.persistence;

import android.content.Context;

import com.jemena.maintenance.model.FormComponent;
import com.jemena.maintenance.model.PageBreak;
import com.jemena.maintenance.model.RadioPrompt;
import com.jemena.maintenance.model.TextInput;

import org.json.*;

import java.util.ArrayList;

public class JsonHelper {
    Context context;

    public JsonHelper(Context context) {
        this.context = context;
    }

    public JSONArray parseFormJson(String json) {

        JSONArray jsonArr = null;

        try {
            jsonArr = new JSONArray(json);
        }
        catch(JSONException e) {
            System.out.println(e.getMessage());
        }

        return jsonArr;
    }

    public RadioPrompt toRadio(JSONObject radio) {
        RadioPrompt radioPrompt = null;


        try {
            JSONArray optionsJson = radio.getJSONArray(Constants.OPTIONS);
            ArrayList<String> options = toArrayList(optionsJson);

            radioPrompt = new RadioPrompt(
                    context,
                    radio.getString(Constants.PROMPT),
                    options,
                    false
            );
            radioPrompt.setResponseNotify(radio.getInt(Constants.RESPONSE));
        }
        catch (JSONException e){
            System.out.println(e.getMessage());
        }

        return radioPrompt;
    }


    public TextInput toTextInput(JSONObject textInputJson) {
        TextInput textInput = null;

        try {
            textInput = new TextInput(
                    context,
                    textInputJson.getString(Constants.PROMPT),
                    false
            );

            textInput.setResponseNotify(textInputJson.getString(Constants.RESPONSE));
        }
        catch(JSONException e) {
            System.out.println(e.getMessage());
        }

        return textInput;
    }

    public PageBreak toPageBreak(JSONObject pageBreakJson) {
        return new PageBreak(context);
    }


    public ArrayList<String> toArrayList(JSONArray jsonArr) {

        ArrayList<String> array = new ArrayList<>();

        try {
            for (int i=0; i < jsonArr.length(); i++) {
                array.add(jsonArr.getString(i));
            }
        }
        catch (JSONException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return array;
    }


    public ArrayList<FormComponent> getComponentList(String json) {

        ArrayList<FormComponent> prompts = new ArrayList<>();
        JSONArray formJson = parseFormJson(json);

        for (int i=0; i < formJson.length(); i++) {

            try {
                JSONObject currObj = formJson.getJSONObject(i);

                switch(currObj.getString(Constants.TYPE)) {

                    case Constants.RADIO:
                        prompts.add(toRadio(currObj));
                        break;

                    case Constants.TEXT:
                        prompts.add(toTextInput(currObj));
                        break;

                    case Constants.PAGE_BREAK:
                        prompts.add(toPageBreak(currObj));
                        break;
                }
            }
            catch(JSONException e) {
                System.out.println(e.getMessage());
           }
        }

        return prompts;
    }


    public JSONArray arrayListToJson(ArrayList<FormComponent> components) {
        JSONArray JSONComponents = new JSONArray();
        for (FormComponent component : components) {
            JSONComponents.put(component.toJSON());
        }
        return JSONComponents;
    }
}
