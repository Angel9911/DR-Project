package com.example.demo.private_lib.invoice;

import com.example.demo.models.entity.Customer;
import com.example.demo.models.entity.Packages;
import com.example.demo.private_lib.invoice.models.InvoiceModel;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.io.IOException;
import java.util.List;

public abstract class InvoiceFormat {

    public byte[] generateInvoice(String logoPath, String headerTitle, InvoiceModel invoiceObject, List<Packages> packagesList) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();

        addLogo(document, logoPath);
        addHeader(document, headerTitle);

        addCustomerInfo(document, invoiceObject);

        PdfPTable table = createTable();
        for (Packages item : packagesList) {
            addTableData(table, item);
        }

        document.add(table);

        document.close();

        return out.toByteArray();
    }

    protected abstract void addLogo(Document document, String logoPath) throws IOException, DocumentException;
    protected abstract void addHeader(Document document,String title) throws DocumentException;
    protected abstract void addCustomerInfo(Document document, InvoiceModel invoiceObject) throws DocumentException;
    protected abstract PdfPTable createTable();
    protected abstract void addTableData(PdfPTable pdfPTable, Packages packages);
}
