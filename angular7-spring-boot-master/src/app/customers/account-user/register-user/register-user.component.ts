import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpClientService} from '../../../service/customer/http-client.service';
import {Router} from '@angular/router';
import {AlertServiceService} from '../../../service/alert-service.service';
import {Customer} from '../../../models/user';
import {CheckMatch} from '../../../helpers/match-validator';
import {City} from '../../../models/City';
import {User_account} from '../../../models/user_account';
import {existingUsernameValidator, existingEmailValidator} from '../../../helpers/UsernameValidator';
import {HttpClient} from '@angular/common/http';
import {SendRequest} from '../../../models/SendRequest';

const getCit: any = [];
const getRole: any = [];

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {
  rolesArray: any = [];
  customerRegister = new Customer();
  customer = new Customer();
  sendRequest = new SendRequest();
  cityList: any = [];
  towns: any = [];
  getCity = new City();
  registerform: FormGroup;
  // tslint:disable-next-line:variable-name
  submitted: boolean;
  loading: boolean;
  emailPattern = `^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$`;
  phonePattern = '08[789]\\d{7}';
  passwordPattern = '^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$';
  // tslint:disable-next-line:variable-name
  /*error_messages = {
    confirm_password: [
      {type: 'mustmatch', message: 'Паролите трябва да се съвпадат!'}
    ]
  };*/
  // phonePattern = '^((\\+91-?)|0)?[0-9]{10}$';

  // tslint:disable-next-line:max-line-length
  constructor(private httpClientService: HttpClientService, private router: Router, private formBuilder: FormBuilder, private alertService: AlertServiceService, private httpClient: HttpClient) {
    this.CreateLoginForm();
    // this.getCities();
  }

  get username() {
    return this.registerform.get('username');
  }

  get email() {
    return this.registerform.get('email');
  }

  get city() {
    return this.registerform.get('city');
  }

  // @ts-ignore
  ngOnInit() {
    this.httpClientService.getCities().subscribe(data => {
      this.towns = data;
      // tslint:disable-next-line:only-arrow-functions
      this.towns.forEach(function(item) {
        getCit.push(item.name);
      });
    });
    this.cityList = getCit;
  }

  CreateLoginForm() {
    this.registerform = this.formBuilder.group({
      username: new FormControl('', [Validators.required], [existingUsernameValidator(this.httpClientService)]),
      password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.pattern(this.passwordPattern)]),
      confirm_password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.pattern(this.passwordPattern)]),
      firstname: new FormControl('', [Validators.required]),
      lastname: new FormControl('', [Validators.required]),
      // tslint:disable-next-line:max-line-length
      email: new FormControl('', [Validators.required, Validators.pattern(this.emailPattern)], [existingEmailValidator(this.httpClientService)]),
      city: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
      phone: new FormControl('', [Validators.required, Validators.pattern(this.phonePattern), Validators.maxLength(10)]),
    }, {
      validator: CheckMatch('password', 'confirm_password')
    });
  }

  get f() {
    return this.registerform.controls;
  }

  RegisterUser(user) {
     this.submitted = true;
     this.alertService.clear();
     if (this.registerform.invalid) {
       return;
     }
     this.loading = true;
     this.alertService.clear();
     console.log('test city ' + this.city.value);
   //  this.httpClientService.getCityIdByName(this.city.value).subscribe(data => {
     this.sendRequest.customer = this.customerRegister;
     this.sendRequest.customer.city = this.city.value;
     this.rolesArray.push('user');
       // tslint:disable-next-line:only-arrow-functions
     this.rolesArray.forEach(function(item) {
         getRole.push(item);
       });
     this.sendRequest.roles = getRole;
     console.log(this.sendRequest);
     this.httpClientService.registerCustomer(this.sendRequest).subscribe(response => {
         alert('Потребителят е създаден успешно');
         this.router.navigate(['/customers/login']);
       });
       }
   //  );
     /*console.log('get city: ' + this.getCity.city_id);
     this.sendRequest.customer = this.customerRegister;
    // this.customerRegister.city_id = this.getCity.city_id;
    // this.customerRegister.user_account = this.accountRegister;
    // this.customerRegister.city.city_id = this.getCity.city_id;
    // this.sendRequest.customer.city.city_id = this.getCity.city_id;
    // tslint:disable-next-line:only-arrow-functions
     this.rolesArray.push('user');
    // tslint:disable-next-line:only-arrow-functions
     this.rolesArray.forEach(function(item) {
       getRole.push(item);
     });
     this.sendRequest.roles = getRole;
     console.log(this.sendRequest);
     this.httpClientService.registerCustomer(this.sendRequest).subscribe(response => {
      alert('Потребителят е създаден успешно');
      this.router.navigate(['/customers/login']);
    }); */

  /* private getCitiesFromJson() {
    this.httpClient.get('assets/towns.json').subscribe(data => {
      this.towns = data;
      console.log(this.towns);
    });
  } */
}
  /*
  [existingEmailValidator(this.httpClientService)]
   CheckPassword(formGroup: FormGroup) {
    const { value: password } = formGroup.get('password');
    const { value: confirmPassword } = formGroup.get('confirm_password');
    return password === confirmPassword ? null : { passwordNotMatch: true};
  }
getCities(); {
    this.httpClientService.getCities().subscribe(data => {
      this.cityList = data;
    });
  } */
/*
function getCitiesFromJson() {
  this.httpClient.get('assets/towns.json').subscribe(data => {
    this.towns = data;
    console.log(this.towns);
  });
} */

// tslint:disable-next-line:align
