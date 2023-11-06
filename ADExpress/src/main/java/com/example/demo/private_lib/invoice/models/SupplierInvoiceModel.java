package com.example.demo.private_lib.invoice.models;

import com.example.demo.private_lib.invoice.Supplier;

public class SupplierInvoiceModel extends InvoiceModel<Supplier>{

    private final Supplier supplier;

    public SupplierInvoiceModel(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public Supplier getInvoiceDataFor() {
        return supplier;
    }
}
