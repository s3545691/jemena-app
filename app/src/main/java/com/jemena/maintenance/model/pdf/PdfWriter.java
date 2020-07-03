package com.jemena.maintenance.model.pdf;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jemena.maintenance.R;
import com.jemena.maintenance.model.FormComponent;
import com.jemena.maintenance.model.PageBreak;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PdfWriter {
    PdfDocument doc;
    int currPageNum;
    int nextPageNum;
    String title;

    public PdfWriter(String title) {
        doc = new PdfDocument();
        currPageNum = 0;
        nextPageNum = 1;
        this.title = title;
    }

    public PdfWriter() {
        doc = new PdfDocument();
        currPageNum = 0;
        nextPageNum = 1;
        this.title = null;
    }


    public void buildPdf(Context context, ArrayList<FormComponent> components) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View formView = inflater.inflate(R.layout.print_layout, null);
        LinearLayout formList = formView.findViewById(R.id.form);

        if (nextPageNum == 1 && this.title != null) {
            TextView titleText = formView.findViewById(R.id.form_title);
            titleText.setText(title);
            titleText.setVisibility(View.VISIBLE);
        }

        ArrayList<FormComponent> toRemove = new ArrayList();

        for (FormComponent component : components) {

            toRemove.add(component);
            if (component.getClass() == PageBreak.class) {
                break;
            }
            formList.addView(component.getPrintView());
        }

        // Build the list of remaining components to build
        ArrayList<FormComponent> remaining = new ArrayList();

        for (FormComponent component : components) {
            if (!toRemove.contains(component)) {
                remaining.add(component);
            }
        }

        drawPage(formView);

        if (!remaining.isEmpty()) {
            buildPdf(context, remaining);
        }
    }

    // TODO: Create separate pdf pages for the page breaks
    public void write(File file) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            doc.writeTo(outputStream);

            doc.close();
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawPage(View view) {

        view.measure(2480, 3508);
        view.layout(0, 0, 2480, 3508);

        int height = view.getHeight();
        int width = view.getWidth();

        PdfDocument.Page page = createPage(width, height);
        view.draw(page.getCanvas());
        doc.finishPage(page);
    }

    private PdfDocument.Page createPage(int width, int height) {
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(width, height, nextPageNum).create();
        incrementPage();
        return doc.startPage(pageInfo);
    }

    private void incrementPage() {
        currPageNum += 1;
        nextPageNum += 1;
    }
}
