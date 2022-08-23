import {Component, OnInit, PipeTransform} from '@angular/core';
import {DecimalPipe} from '@angular/common';
import {DataService} from '../../../service/data.service';
import {HttpCourierService} from '../../../service/courier/http-courier.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {map, startWith} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {Packages} from '../../../models/Packages';
import {PackageProblem} from '../../../models/PackageProblem';
import {FormControl} from '@angular/forms';
let courierPackages: PackageProblem[];
const statusType = 'получена';

function search(text: string, pipe: PipeTransform): PackageProblem[] {
  return courierPackages.filter(packageProblem => {
    const term = text.toLowerCase();
    // @ts-ignore
    return packageProblem.packages_problem.name_package.toLowerCase().includes(term)
       || packageProblem.packages_problem.customer.name.toLowerCase().includes(term)
       || packageProblem.packages_problem.customer.phone.toLowerCase().includes(term);
  });
}
@Component({
  selector: 'app-problem-packages',
  templateUrl: './problem-packages.component.html',
  styleUrls: ['./problem-packages.component.css']
})
export class ProblemPackagesComponent implements OnInit {
  username: string;
  packages$: Observable<PackageProblem[]>;
  filter = new FormControl('');

  // tslint:disable-next-line:max-line-length
  constructor(pipe: DecimalPipe, private dataService: DataService, private httpCourier: HttpCourierService, private modalService: NgbModal) {
    this.username = sessionStorage.getItem('username');
    console.log(this.username);
    this.httpCourier.getProblemPackages(this.username).subscribe(data => {
      // @ts-ignore
      courierPackages = [...data];
      console.log(courierPackages);
      this.packages$ = this.filter.valueChanges.pipe(
        startWith(''),
        map(text => search(text, pipe))
      );
    });
  }

  ngOnInit() {
  }
  updatePackage(packages: PackageProblem): void {
    if (packages.packages_problem.package_id != null && statusType != null) {
      // @ts-ignore
      this.httpCourier.updatePackageSuccessfull(statusType, packages.package_id).subscribe(data => {
        // tslint:disable-next-line:triple-equals
        // @ts-ignore
        alert('Пратката е доставена успешно!');
      });
    }
  }
}
