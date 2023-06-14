package com.example.demo.services.Impl;

import com.example.demo.models.Customer;
import com.example.demo.models.Packages;
import com.example.demo.services.InvoiceService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final String invoice_template_path = "/jasper/invoice_template.jrxml";
    private final String logo_invoice = "/images/logo.png";

    private final CustomerServiceImpl customerService;

    public InvoiceServiceImpl(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @Override
    public byte[] generateInvoiceFor() throws Exception {
        // create document and output stream
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();

        // add logo
        Image logo = Image.getInstance("src/main/resources/images/logo.png");
        logo.setAlignment(Element.ALIGN_CENTER);
        logo.scaleToFit(150f, 150f);
        document.add(logo);

        // add header
        Paragraph header = new Paragraph("Invoice", new Font(Font.FontFamily.TIMES_ROMAN, 22));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        List<Packages> packagesList = customerService.getAllPackages("test-customer");
        Packages order = new Packages();

        for(Packages getOrder : packagesList) {
            Customer customer = new Customer();
            customer.setName(getOrder.getCustomer().getName());
            order.setPackage_id(getOrder.getPackage_id());
            order.setCustomer(customer);
            //order.getCustomer().setName(getOrder.getCustomer().getName());
            order.setDate_register_package(getOrder.getDate_register_package());
            order.setTotal_cost(getOrder.getTotal_cost());
            order.setPackage_price(getOrder.getPackage_price());
        }

        // add order information
        Paragraph orderInfo = new Paragraph("Order Number: " + order.getPackage_id() + "\n" +
                "Order Date: " + order.getDate_register_package() + "\n" +
                "Customer Name: " + order.getCustomer().getName() + "\n" +
                "Total: " + order.getTotal_cost() + "\n");
        document.add(orderInfo);

        // add table with order items
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

        for (Packages item : packagesList ){
            table.addCell(item.getName_package());
            table.addCell(String.valueOf(1));
            table.addCell(String.valueOf(item.getPackage_price()));
            table.addCell(String.valueOf(item.getTotal_cost()));
        }

        document.add(table);

        // close document
        document.close();

        return out.toByteArray();
    }

}
