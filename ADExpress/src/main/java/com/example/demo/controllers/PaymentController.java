package com.example.demo.controllers;

import com.example.demo.models.PaymentOrder;
import com.example.demo.services.Impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private PaymentOrder paymentOrder;
    @Autowired
    private PaymentServiceImpl paymentService;

    @RequestMapping(value="/make",method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<PaymentOrder> makePayment(){

    }
    @RequestMapping(value="/complete",method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<PaymentOrder> completePayment(){

    }

}
