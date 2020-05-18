package com.jemena.maintenance.view.form_component_factory;

import android.content.Context;
import android.view.View;

import com.jemena.maintenance.model.FormComponent;

public abstract class FormViewFactory<T extends FormComponent> {
    private Context context;
    private T component;

    public FormViewFactory(Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    // Inflates and returns the view of the component when the user is filling it out
    protected abstract View inflateInputView(T component);

    // Inflates and returns the view of the component when the user is editing it
    protected abstract View inflateEditView(T component);

    public View inflateView(T component) {
        View view;
        if(component.isEditing()) {
            view = inflateEditView(component);
        }
        else {
            view = inflateInputView(component);
        }
        return view;
    }

    public T getComponent() {
        return component;
    }

    public void setComponent(T component) {
        this.component = component;
    }
}
