import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {PageNotFoundComponent} from '../helpers/page-not-found/page-not-found.component';
import {AdministratorRoutingModule} from '../administrator/administrator-routing.module';
import {CustomerRoutingModule} from '../customers/customer-routing.module';

@NgModule({
  declarations: [
    PageNotFoundComponent
  ],
  imports: [
    AdministratorRoutingModule,
    CustomerRoutingModule,
  ],
  exports: [
    PageNotFoundComponent
  ]
})
export class SharedErrorPageModule { }
