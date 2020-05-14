package com.jemena.maintenance.view.form_component_factory;

import android.content.Context;
import android.view.View;

import com.jemena.maintenance.model.FormComponent;

public abstract class FormViewFactory {
    private Context context;

    public FormViewFactory(Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    // Inflates and returns the view of the component when the user is filling it out
    protected abstract View inflateInputView(FormComponent component);

    // Inflates and returns the view of the component when the user is editing it
    protected abstract View inflateEditView(FormComponent component);

    public View inflateView(FormComponent component) {
        View view;
        if(component.isEditing()) {
            view = inflateEditView(component);
        }
        else {
            view = inflateInputView(component);
        }
        return view;
    }
}
