import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginCourierComponent} from './account-courier/login-courier/login-courier.component';
import {ListPackagesComponent} from './layout-courier/list-packages/list-packages.component';
import {DeliveredPackagesComponent} from './layout-courier/delivered-packages/delivered-packages.component';
import {ProblemPackagesComponent} from './layout-courier/problem-packages/problem-packages.component';
import {AuthGuardService} from '../service/auth/auth-guard.service';

const routes: Routes = [
  {
    path: 'login',
    component: LoginCourierComponent,
  },
  {
    path: 'home',
    component: ListPackagesComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'packages-delivered',
    component: DeliveredPackagesComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'packages-problem',
    component: ProblemPackagesComponent,
    canActivate: [AuthGuardService]
  },
 /* {
    path: 'update/package',
    component: UpdatePackageComponent
  } */
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CourierRoutingModule { }
