package com.jemena.maintenance.view.form_component_factory;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.PageBreak;
import com.jemena.maintenance.model.RadioPrompt;

import java.util.ArrayList;

public class PageBreakViewFactory extends FormViewFactory<PageBreak> {
    public PageBreakViewFactory(Context context) {
        super(context);
    }

    @Override
    protected View inflateInputView(PageBreak component) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        return inflater.inflate(R.layout.page_break, null);
    }

    @Override
    protected View inflateEditView(final PageBreak pageBreak) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        return inflater.inflate(R.layout.page_break, null);
    }

    @Override
    public View inflatePrintView(PageBreak component) {
        return null;
    }
}
