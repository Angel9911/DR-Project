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
import {MatFormFieldModule, MatListModule, MatSidenavModule, MatSortModule, MatTableModule} from '@angular/material';
import {ContactsComponent} from '../contacts/contacts.component';
import {BrowserModule} from '@angular/platform-browser';
import {ForgetPasswordComponent} from './account-user/forget-password/forget-password.component';
import {ResetPasswordComponent} from './reset-password/reset-password.component';

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
    ],
  /* exports: [
    HeaderLayoutUserComponent
  ], */
   providers: [DecimalPipe],
  bootstrap: [LayoutComponent]
})
export class CustomerModule { }
