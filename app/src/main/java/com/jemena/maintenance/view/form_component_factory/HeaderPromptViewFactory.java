package com.jemena.maintenance.view.form_component_factory;

import android.content.Context;
import android.preference.PreferenceActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.HeaderPrompt;

import java.util.ArrayList;

public class HeaderPromptViewFactory extends FormViewFactory<HeaderPrompt> {

    public HeaderPromptViewFactory(Context context) {
        super(context);
    }

    @Override
    protected View inflateInputView(HeaderPrompt component) {
        LayoutInflater inflater = getInflater();
        View view = inflater.inflate(R.layout.header_input, null);

        ArrayList<Integer> ids = component.getOptions();
        ArrayList<String> responses = component.getResponse();

        for (int i=0; i < ids.size(); i++) {
            EditText editText = view.findViewById(ids.get(i));
            editText.addTextChangedListener(new HeaderTextWatcher(component, i));
            editText.setText(responses.get(i));
        }

        return view;
    }

    @Override
    protected View inflateEditView(HeaderPrompt component) {
        LayoutInflater inflater = getInflater();
        View view = inflater.inflate(R.layout.header_edit, null);

        return view;
    }

    @Override
    public View inflatePrintView(HeaderPrompt component) {
        LayoutInflater inflater = getInflater();
        View view = inflater.inflate(R.layout.header_print, null);

        ArrayList<String> responses = component.getResponse();
        ArrayList<Integer> ids = component.getOptions();

        for (int i=0; i < ids.size(); i++) {
            EditText editText = view.findViewById(ids.get(i));
            editText.setText(responses.get(i));
        }

        return view;
    }

    private LayoutInflater getInflater() {
        return LayoutInflater.from(getContext());
    }
}
