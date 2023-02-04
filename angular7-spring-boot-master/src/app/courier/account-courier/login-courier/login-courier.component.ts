import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {HttpCourierService} from '../../../service/courier/http-courier.service';
import {Courier} from '../../../models/Courier';
import {User_account} from '../../../models/user_account';
import {DataService} from '../../../service/data.service';
import {AuthService} from '../../../service/auth/auth.service';
import {UserDetails} from '../../../private_lib/UserDetails';

// @ts-ignore
@Component({
  selector: 'app-login-courier',
  templateUrl: './login-courier.component.html',
  styleUrls: ['./login-courier.component.css']
})
export class LoginCourierComponent /*extends UserDetails*/ implements OnInit {
  loginCourierForm: FormGroup;
  invalidLogin = false;
  isLoggedin = false;
  isLoginFailed = false;
  errorMessage = '';
  // tslint:disable-next-line:variable-name
  courier_account = new User_account();
  result = new Courier();

  // tslint:disable-next-line:max-line-length
  constructor(protected form: FormBuilder, private httpService: HttpCourierService, private router: Router, private dataService: DataService, private auth: AuthService) {
    this.createForm();
   // this.createUserForm();
  }

  ngOnInit() {
  }
 /* get username() {
    return this.loginCourierForm.get('username');
  }

  get password() {
    return this.loginCourierForm.get('password');
  } */
  LoginCourier() {
    if (this.loginCourierForm.invalid) {
      return;
    }
   // console.log(this.username.value + this.password.value);
    console.log(this.courier_account.username);

    this.auth.authenticate(this.courier_account.username, this.courier_account.password).subscribe(
      data => {
        this.isLoggedin = true;
        // tslint:disable-next-line:no-shadowed-variable
        this.httpService.loginCourier(this.courier_account.username).subscribe(data => {
          this.result = data;
          this.dataService.courier = this.result;
        });
        this.router.navigate(['/courier/home']);
      }, error => {
        console.log(error);
        this.errorMessage = error;
        this.isLoggedin = false;
        this.isLoginFailed = true;
      }
    );

    /* this.httpService.loginCourier(this.courier_account.username, this.courier_account.password).subscribe(data => {
      this.result = data;
      if (this.result != null) {
        this.router.navigate(['/courier/home']);
      } else {
        this.invalidLogin = true;
        alert('error');
      }
    }); */
  }
  createForm() {
    this.loginCourierForm = this.form.group({
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.required]
    });
  }
}
