import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import { Router } from '@angular/router';
import {HttpClientService} from '../../../service/customer/http-client.service';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {state} from '@angular/animations';
import {AlertServiceService} from '../../../service/alert-service.service';
import {Customer} from '../../../models/user';
import {AuthenticationService} from '../../../authentication/authentication.service';
import {DataService} from '../../../service/data.service';
import {User_account} from '../../../models/user_account';
import {AuthService} from '../../../service/auth/auth.service';

@Component({
  selector: 'app-login-user',
  templateUrl: './login-user.component.html',
  styleUrls: ['./login-user.component.css']
})
export class LoginUserComponent implements OnInit {
  @Output() resultObject = new EventEmitter<Customer>();
  // for authenticate
  isLoggedin = false;
  isLoginFailed = false;
  isUsernameExist = false;
  errorMessage = '';
  // @ts-ignore
  customer = new Customer();
  // tslint:disable-next-line:variable-name
  user_acc = new User_account();
  // @ts-ignore
  result = new Customer();
  // legit ot tuk nadolu
  loginform: FormGroup;
  submitted: boolean;
  loading: boolean;
  // tslint:disable-next-line:max-line-length
  constructor(private auth: AuthService, private httpClientService: HttpClientService, private router: Router, private formBuilder: FormBuilder, private alertService: AlertServiceService, private authservice: AuthenticationService, private dataService: DataService) {
   /* if (this.authservice.loggedIn) {
      this.router.navigate(['layout']);
    } */
    this.CreateLoginForm();
  }
  ngOnInit() {
  }
  get username() {
    return this.loginform.get('username');
  }

  get password() {
    return this.loginform.get('password');
  }
  CreateLoginForm() {
    this.loginform = this.formBuilder.group({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)])
    });
  }
  LoginUser(user) {
    this.submitted = true;
    this.alertService.clear();
    if (this.loginform.invalid) {
      return;
    }
    this.loading = true;
    this.user_acc.username = this.username.value;
    this.user_acc.password = this.password.value;
    this.customer.user_account = this.user_acc;
   // console.log(this.customer.username + this.customer.password);
    this.httpClientService.findUsername(this.customer.user_account.username).subscribe(data => {
      // tslint:disable-next-line:triple-equals
      if (data != false) {
        this.isUsernameExist = true;
      }
    });
    this.auth.authenticate(this.customer.user_account.username, this.customer.user_account.password).subscribe(
      data => {
        this.isLoggedin = true;
        // tslint:disable-next-line:no-shadowed-variable
        this.httpClientService.loginCustomer(this.customer.user_account.username).subscribe(data => {
          this.result = data;
          this.dataService.customer = this.result;
        });
        this.router.navigate(['/customers/home']);
      }, error => {
        if (!this.isUsernameExist) {
          console.log(this.isUsernameExist);
          alert('Въвели сте грешно потребителско име.Моля опитайте отново');
        } else if (error != null) {
          alert('Въвели сте грешна парола.Моля опитайте отново');
        }
        console.log('test error: ' + error);
        this.errorMessage = error;
        this.isLoggedin = false;
        this.isLoginFailed = true;
      }
    );

    /* this.httpClientService.loginCustomer(this.customer.user_account.username, this.customer.user_account.password).subscribe(data => {
      this.result = data;
      if (this.result != null) {
       console.log(data);
       this.dataService.customer = this.result;
       console.log('tes send object' + this.dataService.customer.name);
       this.router.navigate(['/customers/home']);
      } else {
        this.loading = false;
        alert('Wrong username or password');
      }
    }); */
    console.log('test-stest: ' + this.result);
    /* if (this.authservice.login(this.customer.username, this.customer.password)) {
      this.router.navigate(['/customers/layout']);
    } else {
      alert('Wrong username or password');
  } */
  }
   sendObject() {
    this.resultObject.emit(this.result);
  }

  /*loginCustomer() {
    this.httpClientService.loginCustomer().subscribe(data => {
      this.employee = data;
      alert('Employee createeed successfully.');
    });
    this.router.navigate(['/register-user']);
  }
  findByname(searchInfo): void {
    this.httpClientService.findByname(this.name).subscribe(
      data => {
        this.employee = data;
        console.log(data);
      }
    );
  }
  searchForm(searchInfo) {
    this.model.name = this.Name.value;
    console.log('test(login-user.component)' , this.model.name);
    console.log(JSON.stringify(this.Name.value, null, 4));
    this.model.kod = this.Kod.value;
    this.httpClientService.GetAllExamples(this.model.name).subscribe(
      response => {this.employee = response;
                   // @ts-ignore
                   this.router.navigate(['/customer-form'], { state: { employee: this.employee} });
      }
    );
  }
  */
}
