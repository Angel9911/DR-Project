package com.example.demo.controllers;

import com.example.demo.models.Customer;
import com.example.demo.models.Packages;
import com.example.demo.services.Impl.CustomerServiceImpl;
import com.example.demo.services.Impl.InvoiceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_PDF;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceServiceImpl invoiceService;
    @Autowired
    private CustomerServiceImpl customerService;

    @RequestMapping(value = "/generator", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<Resource> generatePdf() throws Exception {

        byte[] pdfData = invoiceService.generateInvoiceFor();
        ByteArrayResource resource = new ByteArrayResource(pdfData);
        logger.info("Invoice generated successfully...");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfData.length)
                .body(resource);

    }

    private HttpHeaders getHttpHeaders(String code, String lang, File invoicePdf) {

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(APPLICATION_PDF);
        respHeaders.setContentLength(invoicePdf.length());
        respHeaders.setContentDispositionFormData("attachment", format("%s-%s.pdf", code, lang));
        return respHeaders;
    }
}
