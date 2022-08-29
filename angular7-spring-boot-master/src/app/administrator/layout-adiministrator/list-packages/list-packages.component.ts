import {Component, OnInit, PipeTransform} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Router} from '@angular/router';
import {HttpAdiministratorService} from '../../../service/administrator/http-adiministrator.service';
import {DataService} from '../../../service/data.service';
import {DecimalPipe} from '@angular/common';
import {Observable} from 'rxjs';
import {Customer} from '../../../models/user';
import {Packages} from '../../../models/Packages';
import {map, startWith} from 'rxjs/operators';
// tslint:disable-next-line:prefer-const
let packages: Packages[];
// tslint:disable-next-line:label-position no-unused-expression

function search(text: string, pipe: PipeTransform): Packages[] {
  return packages.filter(packageSearch => {
    console.log(packageSearch);
    const term = text.toLowerCase();
    return packageSearch.name_package.toLowerCase().includes(term)
      || packageSearch.customer.name.toLowerCase().includes(term)
      || packageSearch.typePackage.type_name.toLowerCase().includes(term)
      || packageSearch.customer.phone.toLowerCase().includes(term);
  });
}

    @Component({
  selector: 'app-list-packages',
  templateUrl: './list-packages.component.html',
  styleUrls: ['./list-packages.component.css']
})
export class ListPackagesComponent implements OnInit {
  filter = new FormControl('');
  // users: Customer[];
  result$: Observable<Packages[]>;

  // tslint:disable-next-line:max-line-length
  constructor(private router: Router, private apiService: HttpAdiministratorService, private dataService: DataService, private pipe: DecimalPipe) {
    this.apiService.getAllPackages()
      .subscribe( data => {
        // @ts-ignore
        packages = [...data];
        console.log(packages);
        // this.users = data;
        this.result$ = this.filter.valueChanges.pipe(
          startWith(''),
          // tslint:disable-next-line:no-shadowed-variable
          map(text => search(text, pipe))
        );
      });
  }

  ngOnInit() {
  }

}
