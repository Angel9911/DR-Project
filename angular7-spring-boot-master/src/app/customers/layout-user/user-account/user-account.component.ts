import { Component, OnInit } from '@angular/core';
import {Customer} from '../../../models/user';
import {DataService} from '../../../service/data.service';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {HttpClientService} from '../../../service/customer/http-client.service';
import {City} from '../../../models/City';
import {AlertServiceService} from '../../../service/alert-service.service';
import {HttpClient} from '@angular/common/http';
const getCity: any = [];

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css']
})
export class UserAccountComponent implements OnInit {
 receiveObject: Customer;
 updateObject: Customer;
  ProfileSrc = '../../assets/images/blue-user-icon-32.jpg';
  ngSelect: string;
  towns: any = [];
  FirstName: string;
  LastName: string;
  Email: string;
 cityList: any[];
 loading: boolean;
 userAccountForm: FormGroup;
  // tslint:disable-next-line:max-line-length
  constructor(private dataService: DataService, private form: FormBuilder, private httpClientService: HttpClientService, private alertService: AlertServiceService, private httpClient: HttpClient) {
     this.createForm();
    // this.getCities();
  }

  ngOnInit() {
    this.getCities();
    this.FirstName = this.dataService.customer.name;
    this.LastName = this.dataService.customer.last_name;
    this.Email = this.dataService.customer.email;
    this.userAccountForm.controls.firstname.setValue(this.dataService.customer.name);
    this.userAccountForm.controls.lastname.setValue(this.dataService.customer.last_name);
    this.userAccountForm.controls.email.setValue(this.dataService.customer.email);
    this.userAccountForm.controls.address.setValue(this.dataService.customer.address);
    this.userAccountForm.controls.phone.setValue(this.dataService.customer.phone);
    this.ngSelect = this.dataService.customer.city;
    this.userAccountForm.controls.city.setValue(this.ngSelect);
    console.log('user_account city: ' + this.ngSelect);

    // this.firstName = this.dataService.customer.name;
  }
  get fname() {
    return this.userAccountForm.get('firstname');
  }
  get lname() {
    return this.userAccountForm.get('lastname');
  }
  get city() {
    return this.userAccountForm.get('city');
  }
  get phone() {
    return this.userAccountForm.get('phone');
  }
  get address() {
    return this.userAccountForm.get('address');

  }
  get email() {
    return this.userAccountForm.get('email');

  }
  updateCustomer(customer) {
    this.loading = true;
    this.alertService.clear();
    this.updateObject = new Customer();
    this.updateObject = this.dataService.customer;
    this.updateObject.name = this.fname.value;
    this.updateObject.last_name = this.lname.value;
    this.updateObject.city = this.city.value;
    this.updateObject.phone = this.phone.value;
    this.updateObject.address = this.address.value;
    this.updateObject.email = this.email.value;
    console.log('test update object: ' + this.updateObject.user_account.username + this.updateObject.address);
   // console.log('test in update2: ' + this.updateObject.user_id);
    this.httpClientService.updateCustomer(this.updateObject).subscribe(data => {
      alert('Успешно подновихте вашата лична информация');
    });
   /* this.httpClientService.updateCustomerAddress(this.updateObject.address, this.dataService.customer.user_id).subscribe(data => {
      alert('User updated successfully');
      this.loading = false;
    }); */
  }
  createForm() {
    this.userAccountForm = this.form.group({
      firstname: new FormControl(),
      lastname: new FormControl(),
      email: new FormControl(),
      address: new FormControl(),
      phone: new FormControl(),
      city: new FormControl(),
      oldPassword: new FormControl(),
      newPassword: new FormControl()
    });
  }
  getCities() {
    this.httpClientService.getCities().subscribe(data => {
      this.towns = data;
      // tslint:disable-next-line:only-arrow-functions
      this.towns.forEach(function(item) {
        getCity.push(item.name);
      });
    });
    this.cityList = getCity;
  }
}
