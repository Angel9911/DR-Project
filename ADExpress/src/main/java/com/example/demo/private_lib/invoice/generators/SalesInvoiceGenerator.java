package com.example.demo.private_lib.invoice.generators;

import com.example.demo.models.entity.Packages;
import com.example.demo.private_lib.invoice.InvoiceFormat;
import com.example.demo.private_lib.invoice.Supplier;
import com.example.demo.private_lib.invoice.models.InvoiceModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;

public class SalesInvoiceGenerator extends InvoiceFormat {
    @Override
    protected void addLogo(Document document, String logoPath) throws IOException, DocumentException {

    }

    @Override
    protected void addHeader(Document document, String title) throws DocumentException {

    }

    @Override
    protected void addCustomerInfo(Document document, InvoiceModel invoiceObject) throws DocumentException {
        Supplier supplierDataForInvoice = (Supplier) invoiceObject.getInvoiceDataFor();
    }

    @Override
    protected PdfPTable createTable() {
        return null;
    }

    @Override
    protected void addTableData(PdfPTable pdfPTable, Packages packages) {

    }
}
