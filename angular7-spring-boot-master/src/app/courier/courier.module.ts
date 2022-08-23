import { NgModule } from '@angular/core';
import {CommonModule, DecimalPipe} from '@angular/common';

import { CourierRoutingModule } from './courier-routing.module';
import {LoginCourierComponent} from './account-courier/login-courier/login-courier.component';
import {ListPackagesComponent} from './layout-courier/list-packages/list-packages.component';
import {UpdatePackageComponent} from './layout-courier/update-package/update-package.component';
import {SharedModuleCourierModule} from '../shared-module/shared-module-courier.module';
import {MatListModule, MatSidenavModule} from '@angular/material';
import {NgbModule, NgbTypeaheadModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ProblemPackagesComponent} from './layout-courier/problem-packages/problem-packages.component';
import {DeliveredPackagesComponent} from './layout-courier/delivered-packages/delivered-packages.component';
import {CommentDialogComponent} from '../helpers/comment-dialog/comment-dialog.component';

@NgModule({
  declarations: [
    LoginCourierComponent,
    ListPackagesComponent,
    UpdatePackageComponent,
    ProblemPackagesComponent,
    DeliveredPackagesComponent,
    CommentDialogComponent
  ],
  imports: [
    CommonModule,
    CourierRoutingModule,
    SharedModuleCourierModule,
    MatSidenavModule,
    MatListModule,
    NgbTypeaheadModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [DecimalPipe],
  bootstrap: [ListPackagesComponent],
  entryComponents: [CommentDialogComponent]
})
export class CourierModule { }
