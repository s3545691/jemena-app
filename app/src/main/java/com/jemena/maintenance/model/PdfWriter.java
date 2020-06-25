package com.jemena.maintenance.model;

import android.graphics.pdf.PdfDocument;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfWriter {
    int height;
    int width;

    View view;
    PdfDocument doc;
    int currPageNum;
    int nextPageNum;

    public PdfWriter() {
        doc = new PdfDocument();
        currPageNum = 0;
        nextPageNum = 1;
    }

    // TODO: Create separate pdf pages for the page breaks
    public void write(View view, File file) {

        height = view.getHeight();
        width = view.getWidth();

        // Create the first page
        PdfDocument.Page page = createPage();
        view.layout(0, 0, view.getWidth(), view.getHeight());
        view.draw(page.getCanvas());
        page.getCanvas().translate(0, view.getHeight());
        doc.finishPage(page);

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

    private PdfDocument.Page createPage() {
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(height, width, nextPageNum).create();
        incrementPage();
        return doc.startPage(pageInfo);
    }

    private void incrementPage() {
        currPageNum += 1;
        nextPageNum += 1;
    }
}
