package com.example.demo.controllers;

import com.example.demo.models.Customer;
import com.example.demo.models.PaymentOrder;
import com.example.demo.services.Impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/paypal")
public class PaymentController {

    private PaymentOrder paymentOrder;
    @Autowired
    private PaymentServiceImpl paymentService;

    @RequestMapping(value="/make/payment",method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<String> makePayment(@RequestBody PaymentOrder paymentOrder){
        try{
            String authorizePayment = paymentService.makePayment(paymentOrder);
            System.out.println(authorizePayment);
            return new ResponseEntity<>(authorizePayment, HttpStatus.OK);
        }catch(Exception exception){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @RequestMapping(value="/complete/payment",method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<PaymentOrder> completePayment(){
        PaymentOrder paymentOrder1 = new PaymentOrder();
        return new ResponseEntity<>(paymentOrder1, HttpStatus.OK);

    }
    @RequestMapping(value="/cancel/payment",method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<PaymentOrder> cancelPayment(){
        PaymentOrder paymentOrder1 = new PaymentOrder();
        return new ResponseEntity<>(paymentOrder1, HttpStatus.OK);

    }

}
