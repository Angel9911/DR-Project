import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HeaderLayoutUserComponent} from '../headers/header-layout-user/header-layout-user.component';
import {HomeComponent} from './home.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
  },
];

@NgModule({
  imports: [RouterModule],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
