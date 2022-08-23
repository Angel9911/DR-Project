import {Component, OnInit, PipeTransform} from '@angular/core';
import {from, Observable, of} from 'rxjs';
import {FormControl} from '@angular/forms';
import {map, startWith} from 'rxjs/operators';
import {DecimalPipe} from '@angular/common';
import {HttpClientService} from '../../../service/customer/http-client.service';
import {DataService} from '../../../service/data.service';
import {Packages} from '../../../models/Packages';
import {User_account} from '../../../models/user_account';
import {Customer} from '../../../models/user';
import {Sort} from '@angular/material';
// tslint:disable-next-line:prefer-const
let customerPackages: Packages[];
// tslint:disable-next-line:prefer-const
let addressPackage: any;

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
export class LayoutComponent implements OnInit {
  packages$: Observable<Packages[]>;
  sortedData: Observable<Packages[]>;
  username: string;
  packageAddress = [];
  userAcc = new User_account();
  userAcc1: User_account;
  cust = new Customer();
  filter = new FormControl('');

  constructor(pipe: DecimalPipe, private httpClientService: HttpClientService, private dataService: DataService) {
   // this.userAcc.username = this.dataService.customer.user_account.username;
    // this.cust = this.dataService.customer;
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
  }
  ngOnInit() {
    // tslint:disable-next-line:only-arrow-functions
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
// @ts-ignore
}
