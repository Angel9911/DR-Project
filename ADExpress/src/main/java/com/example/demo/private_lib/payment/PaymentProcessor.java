package com.example.demo.private_lib.payment;

import com.example.demo.models.PaymentOrder;

public class PaymentProcessor<R,T, P> {
    private final AbstractPaymentModel<R,T, P> paymentModel;

    public PaymentProcessor(AbstractPaymentModel<R,T, P> paymentModel) {
        this.paymentModel = paymentModel;
    }

    public String makePayment(PaymentOrder paymentOrder) throws Exception {
        return this.paymentModel.processPayment(paymentOrder);
    }
}
