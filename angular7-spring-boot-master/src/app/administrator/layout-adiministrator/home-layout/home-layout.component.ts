import {Component, OnInit, PipeTransform} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {DecimalPipe} from '@angular/common';
import {map, startWith} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {HttpAdiministratorService} from '../../../service/administrator/http-adiministrator.service';
import {existingEmailValidator, existingUsernameValidator} from '../../../helpers/UsernameValidator';
import {Customer} from '../../../models/user';
import {Packages} from '../../../models/Packages';
import {TypePackage} from '../../../models/TypePackage';
import {StatusPackage} from '../../../models/StatusPackage';
const getTypes: any = [];

@Component({
  selector: 'app-home-layout',
  templateUrl: './home-layout.component.html',
  styleUrls: ['./home-layout.component.css']
})
export class HomeLayoutComponent implements OnInit {
  types: any = [];
  typeList: any = [];
  registerPackageForm: FormGroup;
  customer = new Customer();
  ReceiverNames: AbstractControl;
  packages = new Packages();
  typePackage = new TypePackage();
  statusPackage = new StatusPackage();
  constructor(pipe: DecimalPipe, private httpAdministrator: HttpAdiministratorService, private formBuilder: FormBuilder) {
    this.CreateHomeForm();
  }

  ngOnInit() {
    this.httpAdministrator.getAllTypes().subscribe(data => {
      this.types = data;

      // tslint:disable-next-line:only-arrow-functions
      this.types.forEach(function(item) {
        getTypes.push(item.type_name);
      });
    });
    this.typeList = getTypes;
  }
  get receiverNames() {
    return this.registerPackageForm.get('receiverNames');
  }
  get phone() {
    return this.registerPackageForm.get('receiverPhone');
  }
  RegisterPackage(packageCustomer) {
    this.packages.name_package = this.registerPackageForm.get('packageName').value;
    this.ReceiverNames = this.receiverNames;
    // tslint:disable-next-line:prefer-const
    let splittedNames = this.receiverNames.value.split(' ', 2);
    this.customer.name = splittedNames[0];
    this.customer.last_name = splittedNames[1];
    // tslint:disable-next-line:no-unused-expression
    this.customer.phone = this.phone.value;
    this.customer.address = this.registerPackageForm.get('to').value;

    this.typePackage.type_name = this.registerPackageForm.get('type').value;
    this.packages.typePackage = this.typePackage;
    this.packages.customer = this.customer;
    this.packages.weight_package = null;
    this.packages.size_height = null;
    this.packages.size_width = null;
    this.packages.review_package = false;
    console.log(this.packages);
    this.httpAdministrator.registerPackage(this.packages).subscribe(data => {
      alert('uspeshmp maina da go duhash');
    });
    console.log('test' + splittedNames[0]);
    console.log('test type' + this.typePackage.type_name);
  }
  CreateHomeForm() {
    this.registerPackageForm = this.formBuilder.group({
      packageName: new FormControl('', [Validators.required]),
      type: new FormControl('', [Validators.required]),
      senderNames: new FormControl('', [Validators.required]),
      senderPhone: new FormControl('', [Validators.required]),
      receiverNames: new FormControl('', [Validators.required]),
      // tslint:disable-next-line:max-line-length
      receiverPhone: new FormControl('', [Validators.required]),
      from: new FormControl('', [Validators.required]),
      to: new FormControl('', [Validators.required]),
      weight: new FormControl('', [Validators.required]),
      height: new FormControl('', [Validators.required]),
      width: new FormControl('', [Validators.required]),
    });
  }
}
