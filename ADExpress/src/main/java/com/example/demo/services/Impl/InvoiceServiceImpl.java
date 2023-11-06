package com.example.demo.services.Impl;

import com.example.demo.models.entity.Customer;
import com.example.demo.models.entity.Packages;
import com.example.demo.private_lib.invoice.generators.CustomerInvoiceGenerator;
import com.example.demo.private_lib.invoice.InvoiceFormat;
import com.example.demo.private_lib.invoice.models.CustomerInvoiceModel;
import com.example.demo.services.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final CustomerServiceImpl customerService;

    public InvoiceServiceImpl(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @Override
    public byte[] generateInvoiceFor() throws Exception {

        Customer customer = (Customer) customerService.Login(2L);
        CustomerInvoiceModel customerInvoiceModel = new CustomerInvoiceModel(customer);
        List<Packages> packagesList = customerService.getAllPackages("test-customer");
        // using template pattern
        InvoiceFormat customerInvoiceFormat = new CustomerInvoiceGenerator();

        byte[] invoice = customerInvoiceFormat.generateInvoice("src/main/resources/images/logo.png", "Invoice", customerInvoiceModel, packagesList);

        return invoice;
    }

}
