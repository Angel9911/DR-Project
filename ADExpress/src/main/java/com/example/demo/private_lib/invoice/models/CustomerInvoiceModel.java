package com.example.demo.private_lib.invoice.models;

import com.example.demo.models.entity.Customer;

public class CustomerInvoiceModel extends InvoiceModel<Customer>{

    private final Customer customerData;

    public CustomerInvoiceModel(Customer customerData) {
        this.customerData = customerData;
    }


    @Override
    public Customer getInvoiceDataFor() {
        return customerData;
    }
}
