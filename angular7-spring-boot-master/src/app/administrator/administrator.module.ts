import { NgModule } from '@angular/core';
import {CommonModule, DecimalPipe} from '@angular/common';

import { AdministratorRoutingModule } from './administrator-routing.module';
import {LoginAdministratorComponent} from './account-administrator/login-administrator/login-administrator.component';
import {AddUsersComponent} from './layout-adiministrator/users/add-users/add-users.component';
import {EditUsersComponent} from './layout-adiministrator/users/edit-users/edit-users.component';
import {ListUsersComponent} from './layout-adiministrator/users/list-users/list-users.component';
import {SharedModuleAdminModule} from '../shared-module/shared-module-admin.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HomeLayoutComponent} from './layout-adiministrator/home-layout/home-layout.component';
import {MatListModule, MatSidenavModule} from '@angular/material';
import {ListCouriersComponent} from './layout-adiministrator/list-couriers/list-couriers.component';
import {ListPackagesComponent} from './layout-adiministrator/list-packages/list-packages.component';
import {NgbModule, NgbTypeaheadModule} from '@ng-bootstrap/ng-bootstrap';
import {AddCourierComponent} from './layout-adiministrator/couriers/add-courier/add-courier.component';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {AuthInterceptorService} from '../service/auth/auth-interceptor.service';
import {ConfirmDialogComponent} from '../helpers/confirm-dialog/confirm-dialog.component';

@NgModule({
  declarations: [
    // PageNotFoundComponent,
    LoginAdministratorComponent,
    AddUsersComponent,
    AddCourierComponent,
    ListCouriersComponent,
    HomeLayoutComponent,
    EditUsersComponent,
    ListUsersComponent,
    ListPackagesComponent
  ],
  imports: [
    CommonModule,
    AdministratorRoutingModule,
    SharedModuleAdminModule,
    // SharedErrorPageModule,
    MatSidenavModule,
    MatListModule,
    NgbTypeaheadModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [DecimalPipe/*, {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptorService,
    multi: true
  }*/],
 // entryComponents: [ConfirmDialogComponent],
  bootstrap: [HomeLayoutComponent]
})
export class AdministratorModule { }
