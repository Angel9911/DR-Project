import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AppComponent} from './app.component';
import {HeaderComponent} from './header/header.component';

const routes: Routes = [
  {
    path: 'customers',
    loadChildren: () => import('./customers/customer.module').then(m => m.CustomerModule)
  },
  {
    path: 'courier',
    loadChildren: () => import('./courier/courier.module').then(m => m.CourierModule)
  },
  {
    path: 'administrator',
    loadChildren: () => import('./administrator/administrator.module').then(m => m.AdministratorModule)
  },
  {
    path: '',
    component: AppComponent,
    children: [
      { path: '' , component: HeaderComponent, outlet: 'header'},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
