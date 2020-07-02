package com.jemena.maintenance.model.pdf;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.jemena.maintenance.model.FormComponent;

import java.util.ArrayList;

public class PrintAdapter {
    Context context;
    ArrayList<FormComponent> components;
    LinearLayout formView;


    public PrintAdapter(Context context, ArrayList<FormComponent> components, LinearLayout formView) {
        this.context = context;
        this.components = components;
        this.formView = formView;
    }

    public void drawViews() {
        for (FormComponent component : components) {
            formView.addView(component.getPrintView());
        }
    }
}
