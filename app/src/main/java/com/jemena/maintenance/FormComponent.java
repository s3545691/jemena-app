package com.jemena.maintenance;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

public abstract class FormComponent extends ConstraintLayout {
    private String prompt;

    public FormComponent(Context context) {
        super(context);
    }

    public String getPrompt() {
        return prompt;
    }

    public  void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public abstract String getResponse();
}
