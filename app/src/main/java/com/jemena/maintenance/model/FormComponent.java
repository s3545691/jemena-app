package com.jemena.maintenance.model;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

public abstract class FormComponent {
    private String prompt;

    public String getPrompt() {
        return prompt;
    }

    public  void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public abstract String getResponse();
}
