import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {DecimalPipe} from '@angular/common';
import {HttpAdiministratorService} from '../../../service/administrator/http-adiministrator.service';
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
  // tslint:disable-next-line:variable-name
  receiver_customer = new Customer();
  SenderNames: AbstractControl;
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

  get senderNames() {
    return this.registerPackageForm.get('senderNames');
  }

  get senderPhone() {
    return this.registerPackageForm.get('senderPhone');
  }

  get receiverNames() {
    return this.registerPackageForm.get('receiverNames');
  }

  get receiverPhone() {
    return this.registerPackageForm.get('receiverPhone');
  }

  get packagePrice() {
    return this.registerPackageForm.get('packagePrice');
  }

  RegisterPackage(packageCustomer) {
    this.packages.name_package = this.registerPackageForm.get('packageName').value;
    this.SenderNames = this.senderNames;
    this.ReceiverNames = this.receiverNames;
    // tslint:disable-next-line:prefer-const
    let splittedSenderNames = this.senderNames.value.split(' ', 2);
    const splittedReceiverNames = this.receiverNames.value.split(' ', 2);
    this.customer.name = splittedSenderNames[0];
    this.customer.last_name = splittedSenderNames[1];
    // tslint:disable-next-line:no-unused-expression
    this.customer.phone = this.senderPhone.value;
    this.customer.address = this.registerPackageForm.get('from').value;

    this.receiver_customer.name = splittedReceiverNames[0];
    this.receiver_customer.last_name = splittedReceiverNames[1];
    this.receiver_customer.phone = this.receiverPhone.value;
    this.receiver_customer.address = this.registerPackageForm.get('to').value;

    this.typePackage.type_name = this.registerPackageForm.get('type').value;
    this.packages.typePackage = this.typePackage;
    this.packages.customer = this.customer;
    this.packages.receiver = this.receiver_customer;
    this.packages.weight_package = null;
    this.packages.size_height = null;
    this.packages.size_width = null;
    this.packages.review_package = false;
    this.packages.package_price = this.registerPackageForm.get('packagePrice').value;
    console.log(packageCustomer.packagePrice);
    this.httpAdministrator.registerPackage(this.packages).subscribe(data => {
      alert('uspeshmp maina da go duhash');
    });
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
      packagePrice: new FormControl('', [Validators.required]),
    });
  }
}
