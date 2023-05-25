import { NgModule } from '@angular/core';
import {CommonModule, DecimalPipe} from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import {RegisterUserComponent} from './account-user/register-user/register-user.component';
import {LoginUserComponent} from './account-user/login-user/login-user.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {LayoutComponent} from './layout-user/layout/layout.component';

import {SharedModuleModule} from '../shared-module/shared-module.module';
import { UserAccountComponent } from './layout-user/user-account/user-account.component';

import {NgbTypeaheadModule} from '@ng-bootstrap/ng-bootstrap';
//import {MatFormFieldModule, MatListModule, MatSidenavModule, MatSortModule, MatTableModule} from '@angular/material';
import {ContactsComponent} from '../contacts/contacts.component';
import {BrowserModule} from '@angular/platform-browser';
import {ForgetPasswordComponent} from './account-user/forget-password/forget-password.component';
import {ResetPasswordComponent} from './reset-password/reset-password.component';
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {MatTableModule} from '@angular/material/table';
import {MatSortModule} from '@angular/material/sort';
import {FacebookLoginProvider, GoogleLoginProvider, SocialAuthServiceConfig, SocialLoginModule} from '@abacritt/angularx-social-login';
=======
>>>>>>> 96e6108617b064c9890a2c56099c02c04753ae58
=======
>>>>>>> 96e6108617b064c9890a2c56099c02c04753ae58
=======
>>>>>>> 96e6108617b064c9890a2c56099c02c04753ae58

@NgModule({
  declarations: [
    RegisterUserComponent,
    LoginUserComponent,
    LayoutComponent,
    UserAccountComponent,
    ContactsComponent,
    ForgetPasswordComponent,
    ResetPasswordComponent,
   // PageNotFoundComponent
  ],
    imports: [
        CommonModule,
        FormsModule,
        CustomerRoutingModule,
        ReactiveFormsModule,
        SharedModuleModule,
        // SharedErrorPageModule,
        NgbTypeaheadModule,
        MatSidenavModule,
        MatListModule,
        MatTableModule,
        MatSortModule,
        SocialLoginModule
    ],
  /* exports: [
    HeaderLayoutUserComponent
  ], */
  providers: [{
    provide: 'SocialAuthServiceConfig',
    useValue: {
      autoLogin: false,
      providers: [{
        id: GoogleLoginProvider.PROVIDER_ID,
        provider: new GoogleLoginProvider('420149659798-i5l97e59u7fblpjb5bku87tq9hcov4tq.apps.googleusercontent.com',/*{oneTapEnabled: false}*/)
      }
      ],
      onError: (err) => {
        console.error(err);
      }
    } as SocialAuthServiceConfig
  }/*,
    {provide: 'SocialAuthServiceConfig',
    useValue: {
      autoLogin: false,
      providers: [{
        id: FacebookLoginProvider.PROVIDER_ID,
        provider: new FacebookLoginProvider('1236372247083640')
      }],
      onError: (err) => {
        console.error(err);
      }
    } as SocialAuthServiceConfig} */
  ,DecimalPipe],
  bootstrap: [LayoutComponent]
})
export class CustomerModule { }
