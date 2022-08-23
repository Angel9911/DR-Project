import {Component, OnInit, PipeTransform} from '@angular/core';
import {Observable} from 'rxjs';
import {Packages} from '../../../models/Packages';
import {HttpCourierService} from '../../../service/courier/http-courier.service';
import {map, startWith} from 'rxjs/operators';
import {FormControl} from '@angular/forms';
import {DecimalPipe} from '@angular/common';
// tslint:disable-next-line:prefer-const
let deliveredPackages: Packages[];

function search(text: string, pipe: PipeTransform): Packages[] {
  return deliveredPackages.filter(packages => {
    const term = text.toLowerCase();
    // @ts-ignore
    return packages.statusPackage.status_type.toLowerCase().includes(term)
      || pipe.transform(packages.name_package).includes(term)
      || pipe.transform(packages.typePackage.type_name).toLowerCase().includes(term)
      || pipe.transform(packages.customer.name).includes(term);
  });
}

@Component({
  selector: 'app-delivered-packages',
  templateUrl: './delivered-packages.component.html',
  styleUrls: ['./delivered-packages.component.css']
})
export class DeliveredPackagesComponent implements OnInit {
  deliveredPackages$: Observable<Packages[]>;
  username: string;
  filter = new FormControl('');
  constructor(private httpService: HttpCourierService, private pipe: DecimalPipe) {
    this.username = sessionStorage.getItem('username');
    console.log(this.username);
    this.httpService.getDeliveredPackages(this.username).subscribe(data => {
      // @ts-ignore
      deliveredPackages = [...data];
      this.deliveredPackages$ = this.filter.valueChanges.pipe(
        startWith(''),
        map(text => search(text, pipe))
      );
    });
  }

  ngOnInit() {
  }

}
