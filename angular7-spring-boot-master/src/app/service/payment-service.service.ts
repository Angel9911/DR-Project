import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {PaymentOrder} from '../models/PaymentOrder';
import {Customer} from '../models/user';

const paypalUrl = 'http://localhost:8082/paypal/';
const reqHeader = new HttpHeaders({
  'Content-Type': 'application/json',
  Authentication: 'Bearer ' + JSON.parse(localStorage.getItem('token'))
});

@Injectable({
  providedIn: 'root'
})
export class PaymentServiceService {
  private paypal: any;

  constructor(private httpPayment: HttpClient) { }

  makePayment(paymentOrder: PaymentOrder) {
    const url = paypalUrl + 'make/payment';
    // @ts-ignore
    return this.httpPayment.post(url, paymentOrder, {headers: reqHeader, responseType: 'text'});
   /* return this.httpPayment.post(url, { paymentOrder }, {headers: reqHeader, responseType: 'text'})
      .subscribe((paymentDetails: any) => {
        // Initiate the PayPal payment
        this.paypal.Buttons({
          createOrder: (data, actions) => {
            return actions.order.create({
              purchase_units: [{
                amount: {
                  value: paymentOrder.package_price
                }
              }]
            });
          },
          onApprove: (data, actions) => {
            // Handle the approved payment
            // ...
          }
        }).render('#paypal-button-container');
      }); */

  }
  cancelPayment() {
    const url = paypalUrl + 'cancel/payment';

  }
}
