package com.example.demo.private_lib.payment;

import com.example.demo.models.PaymentOrder;
import com.example.demo.models.entity.Customer;

import java.util.List;

public abstract class AbstractPaymentModel<R,T, P> implements PaymentModel{

    protected abstract R getRedirectsUrl();


    protected abstract List<T> getTransactionData(PaymentOrder paymentOrder);



    protected abstract P getPayerData(Customer customer);

}
