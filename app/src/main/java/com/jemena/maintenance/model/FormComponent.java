package com.jemena.maintenance.model;

import android.content.Context;
import android.view.View;

import com.jemena.maintenance.view.form_component_factory.FormViewFactory;

public abstract class FormComponent<T, S> {
    // Holds the current state of the application
    private Context context;

    private String prompt;

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
        viewFactory = formViewFactory;
        this.options = options;
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
    }

    public void setIsEditing(Boolean isEditing) {
        this.isEditing = isEditing;
    }

    public void setOptions(T options) {
        this.options = options;
    }

    public Boolean isEditing() {
        return isEditing;
    }

    // The user's response in String form. Should return null if empty
    public abstract String getResponse();

    public abstract void setResponse(S response);
}
