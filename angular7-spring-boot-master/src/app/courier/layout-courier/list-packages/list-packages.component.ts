import {Component, OnInit, PipeTransform} from '@angular/core';
import {Observable} from 'rxjs';
import {FormArray, FormControl} from '@angular/forms';
import {DecimalPipe} from '@angular/common';
import {map, startWith} from 'rxjs/operators';
import {DataService} from '../../../service/data.service';
import {HttpCourierService} from '../../../service/courier/http-courier.service';
import {Packages} from '../../../models/Packages';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {CommentDialogComponent} from '../../../helpers/comment-dialog/comment-dialog.component';
// tslint:disable-next-line:prefer-const
let courierPackages: Packages[];
const statusType = 'получена';
const statusTypeProblem = 'Проблем с пратката';

function search(text: string, pipe: PipeTransform): Packages[] {
  return courierPackages.filter(packages => {
    const term = text.toLowerCase();
    // @ts-ignore
    return packages.name_package.toLowerCase().includes(term)
      || packages.customer.name.toLowerCase().includes(term)
      || packages.customer.phone.toLowerCase().includes(term);
  });
}
@Component({
  selector: 'app-list-packages',
  templateUrl: './list-packages.component.html',
  styleUrls: ['./list-packages.component.css']
})
export class ListPackagesComponent implements OnInit {
  packages$: Observable<Packages[]>;
  username: string;
  getComment: string;
  comment: string;
  filter = new FormControl('');
  // tslint:disable-next-line:max-line-length
  constructor(pipe: DecimalPipe, private dataService: DataService, private httpCourier: HttpCourierService, private modalService: NgbModal) {
    this.username = sessionStorage.getItem('username');
    console.log(this.username);
    this.httpCourier.getPackages(this.username).subscribe(data => {
      // @ts-ignore
      courierPackages = [...data];
      // tslint:disable-next-line:only-arrow-functions
      this.packages$ = this.filter.valueChanges.pipe(
        startWith(''),
        map(text => search(text, pipe))
      );
    });
  }
  ngOnInit() {
  }
  updatePackage(packages: Packages): void {
    console.log(packages.package_id);
    if (packages.package_id != null && statusType != null) {
      // @ts-ignore
      this.httpCourier.updatePackageSuccessfull(statusType, packages.package_id).subscribe(data => {
        // tslint:disable-next-line:triple-equals
        // @ts-ignore
        alert('Пратката е доставена успешно!');
      });
    }
  }
  updateProblemPackage(packages: Packages): void {
    const dialogRef = this.modalService.open(CommentDialogComponent);
    dialogRef.result.then((result) => {
      if ( result.event === true ) {
        this.dataService.packagesId = packages.package_id;
        this.httpCourier.updateProblemPackage(statusTypeProblem, packages.package_id, result.data).subscribe(data => {
          alert('Проблемът на пратката е регистриран успешно!');
        });
        console.log('dada' + result.data); // Refresh Data in table grid
      } else {

      }
    }, (reason) => {
    });
    console.log(dialogRef.result);
  }
}
