package com.example.demo.services;

import com.example.demo.models.Customer;
import com.example.demo.models.Packages;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public interface InvoiceService {
    byte[] generateInvoiceFor() throws Exception;
}
