package com.example.demo.private_lib.invoice.generators;

import com.example.demo.models.entity.Customer;
import com.example.demo.models.entity.Packages;
import com.example.demo.private_lib.invoice.InvoiceFormat;
import com.example.demo.private_lib.invoice.models.CustomerInvoiceModel;
import com.example.demo.private_lib.invoice.models.InvoiceModel;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;

public class CustomerInvoiceGenerator extends InvoiceFormat {

    @Override
    protected void addLogo(Document document, String logoPath) throws IOException, DocumentException {
        Image logo = Image.getInstance("src/main/resources/images/logo.png");
        logo.setAlignment(Element.ALIGN_CENTER);
        logo.scaleToFit(150f, 150f);
        document.add(logo);
    }

    @Override
    protected void addHeader(Document document, String title) throws DocumentException {
        // add header
        Paragraph header = new Paragraph("Invoice", new Font(Font.FontFamily.TIMES_ROMAN, 22));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
    }

    @Override
    protected void addCustomerInfo(Document document, InvoiceModel invoiceObject) throws DocumentException {
        // add order information
        Customer customerDataForInvoice = (Customer) invoiceObject.getInvoiceDataFor();

        String customerData = String.format(" Customer name: %s %s %n Customer address: City %s Address %s %n Customer phone: %s", customerDataForInvoice.getName(),customerDataForInvoice.getLastName(), customerDataForInvoice.getCity(), customerDataForInvoice.getAddress(),customerDataForInvoice.getPhone());

        Paragraph orderInfo = new Paragraph(customerData);

        document.add(orderInfo);
    }

    @Override
    protected PdfPTable createTable() {
        PdfPTable table = new PdfPTable(4);
        PdfPCell cell = new PdfPCell(new Phrase("Product Name"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Quantity"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Price"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Total"));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        return table;
    }

    @Override
    protected void addTableData(PdfPTable pdfPTable, Packages packages) {

        pdfPTable.addCell(packages.getPackageName());
        pdfPTable.addCell(String.valueOf(1));
        pdfPTable.addCell(String.valueOf(packages.getPackage_price()));
        pdfPTable.addCell(String.valueOf(packages.getTotal_cost()));

    }
}
