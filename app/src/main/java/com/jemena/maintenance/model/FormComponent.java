package com.jemena.maintenance.model;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import org.json.JSONObject;

import com.jemena.maintenance.view.form_component_factory.FormViewFactory;

public abstract class FormComponent<T, S> {
    // Holds the current state of the application
    private Context context;

    // Must be set in order to change the
    private ArrayAdapter arrayAdapter;

    private String prompt;
    protected S response;

    // Keeps track of whether or not the current component is in edit or input mode
    private Boolean isEditing;

    // Responsible for inflating the view
    private FormViewFactory viewFactory;

    // Options the prompt provides the user. For example for a multiple choice prompt, each choice
    // the user can choose would be an option
    private T options;

    public FormComponent(Context context, String prompt, Boolean isEditing,
                         FormViewFactory formViewFactory,
                         T options) {

        this.context = context;
        this.prompt = prompt;
        this.isEditing = isEditing;
        arrayAdapter = null;
        this.options = options == null ? instantiateEmptyOptions() : options;

        viewFactory = formViewFactory;
        formViewFactory.setComponent(this);
    }

    public Context getContext() {
        return context;
    }

    public String getPrompt() {
        return prompt;
    }

    public T getOptions() {
        return options;
    }

    public View getView() {
        return viewFactory.inflateView(this);
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;

        notifyAdapter();
    }

    public void setIsEditing(Boolean isEditing) {
        this.isEditing = isEditing;
        notifyAdapter();
    }

    public void setOptions(T options) {
        this.options = options;
        notifyAdapter();
    }

    public Boolean isEditing() {
        return isEditing;
    }

    public   S getResponse() {
        return response;
    }

    public void setResponseNotify(S response) {
        this.response = response;
        notifyAdapter();
    }

    public void setResponse(S response) {
        this.response = response;
    }

    public void setArrayAdapter(ArrayAdapter arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
    }

    public ArrayAdapter getArrayAdapter() {
        return arrayAdapter;
    }

    private void notifyAdapter() {
        if (arrayAdapter != null) {
            arrayAdapter.notifyDataSetChanged();
        }
    }

    // The user's response in String form. Should return null if empty
    public abstract String getStringResponse();

    protected abstract T instantiateEmptyOptions();

    public abstract JSONObject toJSON();

}
