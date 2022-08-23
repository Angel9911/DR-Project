import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginAdministratorComponent} from './account-administrator/login-administrator/login-administrator.component';
import {AddUsersComponent} from './layout-adiministrator/users/add-users/add-users.component';
import {EditUsersComponent} from './layout-adiministrator/users/edit-users/edit-users.component';
import {ListUsersComponent} from './layout-adiministrator/users/list-users/list-users.component';
import {HomeLayoutComponent} from './layout-adiministrator/home-layout/home-layout.component';
import {ListCouriersComponent} from './layout-adiministrator/list-couriers/list-couriers.component';
import {AuthGuardService} from '../service/auth/auth-guard.service';
import {AddCourierComponent} from './layout-adiministrator/couriers/add-courier/add-courier.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginAdministratorComponent,
  },
  {
    path: 'home',
    component: HomeLayoutComponent,
    canActivate: [AuthGuardService]
  },
  { path: 'add-user',
    component: AddUsersComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'add-courier',
    component: AddCourierComponent,
    canActivate: [AuthGuardService]
  },
  { path: 'list-user',
    component: ListUsersComponent,
    canActivate: [AuthGuardService]
  },
  { path: 'edit-user',
    component: EditUsersComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'list-courier',
    component: ListCouriersComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'list-packages',
    component: ListCouriersComponent,
    canActivate: [AuthGuardService]
  }
  /* {
    path: '**',
    component: PageNotFoundComponent
  } */
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdministratorRoutingModule { }
