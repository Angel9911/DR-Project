import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginUserComponent} from './account-user/login-user/login-user.component';
import {RegisterUserComponent} from './account-user/register-user/register-user.component';
import {LayoutComponent} from './layout-user/layout/layout.component';
import {HeaderLayoutUserComponent} from '../headers/header-layout-user/header-layout-user.component';
import {HeaderComponent} from '../header/header.component';
import {FooterComponent} from '../footer/footer.component';
import {UserAccountComponent} from './layout-user/user-account/user-account.component';
import {AuthGuard} from '../helpers/AuthGuard';
import {PageNotFoundComponent} from '../helpers/page-not-found/page-not-found.component';
import {ContactsComponent} from '../contacts/contacts.component';
import {AuthGuardService} from '../service/auth/auth-guard.service';

const routes: Routes = [
  {
    path: 'login',
    component: LoginUserComponent,
    /* children: [
      {path: '/layout',
        component: LayoutComponent,
      }
    ] */
  },
  {
    path: 'register',
    component: RegisterUserComponent,
  },
  {
    path: 'home',
    // canActivate: [AuthGuard],
    component: LayoutComponent,
    canActivate: [AuthGuardService]
  /* children: [
     { path: 'account', component: HeaderLayoutUserComponent},
     { path: '', component: HeaderLayoutUserComponent}
      ], */
  },
  {
    path: 'account',
    // canActivate: [AuthGuard],
    component: UserAccountComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'contacts',
    // canActivate: [AuthGuard],
    component: ContactsComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: '**',
    // component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
