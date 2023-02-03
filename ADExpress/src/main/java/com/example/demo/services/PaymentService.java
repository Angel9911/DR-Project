package com.example.demo.services;

import com.example.demo.models.Customer;
import com.example.demo.models.Packages;
import com.example.demo.models.PaymentOrder;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;

import java.net.URI;
import java.util.List;

public interface PaymentService {
    String makePayment(PaymentOrder paymentOrder) throws Exception;
    PaymentOrder executePayment();
    Payer getPayerInformation(Customer customer);
    List<Transaction> getTransactionInfo(PaymentOrder paymentOrder);
    RedirectUrls getRedirectsUrl();
    String approvalLink(Payment approvedPayment);
}
