package com.example.demo.services.Impl;

import com.example.demo.models.PaymentOrder;
import com.example.demo.private_lib.payment.AbstractPaymentModel;
import com.example.demo.private_lib.payment.PaymentProcessor;
import com.example.demo.private_lib.payment.providers.PayPalPaymentModel;
import com.example.demo.services.PaymentService;

import com.paypal.api.payments.Payer;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import org.springframework.stereotype.Service;


@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentProcessor<RedirectUrls,Transaction,Payer> paymentProcessor;

    public PaymentServiceImpl() {
        AbstractPaymentModel<RedirectUrls, Transaction, Payer> payPalModel = new PayPalPaymentModel();
        paymentProcessor = new PaymentProcessor<>(payPalModel);
    }


    @Override
    public String makePayment(PaymentOrder paymentOrder) throws Exception {

        return paymentProcessor.makePayment(paymentOrder);
    }


}
