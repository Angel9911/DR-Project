import {AfterViewInit, Component, Inject, OnInit, PipeTransform, Renderer2} from '@angular/core';
import {Observable, of} from 'rxjs';
import {FormControl} from '@angular/forms';
import {map, startWith} from 'rxjs/operators';
import {DecimalPipe, DOCUMENT} from '@angular/common';
import {HttpClientService} from '../../../service/customer/http-client.service';
import {DataService} from '../../../service/data.service';
import {Packages} from '../../../models/Packages';
import {User_account} from '../../../models/user_account';
import {Sort} from '@angular/material';
import {PaymentServiceService} from '../../../service/payment-service.service';
import {PaymentOrder} from '../../../models/PaymentOrder';
import {ChatbotServiceService} from '../../../service/chatbot-service.service';
// tslint:disable-next-line:prefer-const
let customerPackages: Packages[];
// tslint:disable-next-line:prefer-const


function search(text: string, pipe: PipeTransform): Packages[] {
  return customerPackages.filter(custPackage => {
    console.log(custPackage);
    const term = text.toLowerCase();
    return custPackage.name_package.toLowerCase().includes(term)
      || custPackage.statusPackage.status_type.toLowerCase().includes(term)
      || custPackage.typePackage.type_name.toLowerCase().includes(term);
  });
}

@Component({
  // selector: 'app-header-layout-user',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent implements OnInit, AfterViewInit {
  payppalIcon = '../../assets/images/paypal_logo.png';
  script: HTMLScriptElement;
  // tslint:disable-next-line:ban-types
  result: Object;
  product = new PaymentOrder();

  packages$: Observable<Packages[]>;
  sortedData: Observable<Packages[]>;
  username: string;
  userAcc = new User_account();
  filter = new FormControl('');

  // tslint:disable-next-line:max-line-length
  constructor(private chatbotService: ChatbotServiceService, private paymentService: PaymentServiceService, private renderer2: Renderer2, @Inject(DOCUMENT) private document: Document, pipe: DecimalPipe, private httpClientService: HttpClientService, private dataService: DataService) {
     this.username = sessionStorage.getItem('username');
     console.log(this.username);
     this.httpClientService.getPackages(this.username).subscribe(data => {
      // @ts-ignore
      customerPackages = [...data];
      this.sortedData = of(customerPackages);
      // tslint:disable-next-line:only-arrow-functions
      this.packages$ = this.filter.valueChanges.pipe(
        startWith(''),
        map(text => search(text, pipe))
      );
    });

     this.product.customer_username = this.username;
     this.product.package_name = 'Пералня';
    // tslint:disable-next-line:no-unused-expression
     this.product.package_type = 'Колет';
     this.product.total_price = 133.00;
     this.product.package_price = 123.00;
     this.product.shipping = 5.00;
     this.product.tax_price = 5.00;

    /* this.paymentService.makePayment(this.product).toPromise().then(res => {
      this.result = res;
      paypalIframe.src = res;
      paypalIframe.style.display = 'block';
    }); */
  }

  // tslint:disable-next-line:use-life-cycle-interface
  ngAfterViewInit() {
    /*payButton.addEventListener('click', function() {
      // tslint:disable-next-line:no-shadowed-variable
      this.paymentService.makePayment().toPromise().then(res => {
        this.result = res;
        console.log(this.result);
        if (typeof this.result === 'string') {
          paypalEndpoint = this.result;

        }
      });
    }); */
  }

  ngOnInit() {

  }

  makePayment() {
    const paypalIframe = document.getElementById('paypal-iframe') as HTMLIFrameElement;

    this.paymentService.makePayment(this.product).toPromise().then(res => {
      this.result = res;
      console.log(this.result);
      if (typeof this.result === 'string') {
        console.log(this.result);
        window.open(this.result, '_blank', 'width=600, height=400');
      }
    });
   /* const paypalIframe = document.getElementById('paypal-iframe') as HTMLIFrameElement;

    this.paymentService.makePayment(this.product).toPromise().then(res => {
      paypalIframe.src = res;
      paypalIframe.style.display = 'block';
      }); */

  }

  generateInvoice() {
    this.httpClientService.generateInvoice();
  }
  sortData(sort: Sort) {
    const data = customerPackages.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = of(data);
      return;
    }

    this.sortedData = of(data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'name':
          return this.compare(a.name_package, b.name_package, isAsc);
        case 'price':
          return this.compare(a.package_price, b.package_price, isAsc);
        case 'total_price':
          return this.compare(a.total_cost, b.total_cost, isAsc);
        default:
          return 0;
      }
    }));
    console.log(this.sortedData);
  }

// @ts-ignore
  compare(a: number | string, b: number | string, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  getTotalPackagePrice() {
    return customerPackages.map(t => t.package_price).reduce((a, b) => a + b, 0);
  }

  getTotalCost() {
    return customerPackages.map(t => t.total_cost).reduce((a, b) => a + b, 0);
  }

}
