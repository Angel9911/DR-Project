import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Administrator} from '../../../models/administrator';
import {HttpAdiministratorService} from '../../../service/administrator/http-adiministrator.service';
import {Router} from '@angular/router';
import {User_account} from '../../../models/user_account';
import {AuthService} from '../../../service/auth/auth.service';
import {DataService} from '../../../service/data.service';

@Component({
  selector: 'app-login-administrator',
  templateUrl: './login-administrator.component.html',
  styleUrls: ['./login-administrator.component.css']
})
export class LoginAdministratorComponent implements OnInit {
  loginAdministratorForm: FormGroup;
  invalidLogin = false;
  isLoggedin = false;
  isLoginFailed = false;
  errorMessage = '';
  // tslint:disable-next-line:variable-name
  adminAccount = new User_account();
  result = new Administrator();

  // tslint:disable-next-line:max-line-length
  constructor(private form: FormBuilder, private httpService: HttpAdiministratorService, private router: Router, private auth: AuthService, private dataService: DataService) {
    this.createForm();
  }

  ngOnInit() {
  }
 /* get username() {
    return this.loginAdministratorForm.get('username');
  }

  get password() {
    return this.loginAdministratorForm.get('password');
  } */
  LoginAdministrator() {
    if (this.loginAdministratorForm.invalid) {
      return;
    }
   // console.log(this.username.value + this.password.value);
    this.auth.authenticate(this.adminAccount.username, this.adminAccount.password).subscribe(
      data => {
        this.isLoggedin = true;
        this.httpService.loginAdministrator(this.adminAccount.username).subscribe(result => {
          this.result = result;
          this.dataService.administrator = this.result;
        });
        // tslint:disable-next-line:no-shadowed-variable
        this.router.navigate(['/administrator/home']);
      }, error => {
        console.log(error);
        this.errorMessage = error;
        this.isLoggedin = false;
        this.isLoginFailed = true;
      }
    );
   /* this.administrator_account.username = this.username.value;
    this.administrator_account.password = this.password.value;
    console.log(this.administrator_account.username + this.administrator_account.password); */
    /*

            this.httpService.loginAdministrator(this.adminAccount.username).subscribe(data => {
          this.result = data;
          this.dataService.administrator = this.result;
        });

    this.httpService.loginAdministrator(this.username.value, this.password.value).subscribe(data => {
      this.administrator_account = data;
      if (this.administrator_account != null) {
        this.router.navigate(['/administrator/home']);
      } else {
        this.invalidLogin = true;
        alert('error');
      }
    }); */
  }
  createForm() {
    this.loginAdministratorForm = this.form.group({
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.required]
    });
  }
}
