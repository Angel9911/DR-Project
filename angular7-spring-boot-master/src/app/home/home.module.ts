import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import {SharedModuleModule} from '../shared-module/shared-module.module';
import {HomeComponent} from './home.component';
import {HeaderComponent} from '../header/header.component';
import {FooterComponent} from '../footer/footer.component';
import {MatAutocompleteModule, MatFormFieldModule, MatInputModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AppRoutingModule} from '../app-routing.module';
import {AppModule} from '../app.module';

@NgModule({
  declarations: [
    HomeComponent
   /* ,
    HeaderComponent,
    FooterComponent, */
  ],
  imports: [
    CommonModule,
    // HomeRoutingModule,
    // SharedModuleModule,
    /* MatAutocompleteModule,
     MatFormFieldModule,
     BrowserAnimationsModule,
     FormsModule,
     NgbModule,
     MatInputModule,
     ReactiveFormsModule,*/
    SharedModuleModule,
    AppModule
  ]
})
export class HomeModule { }
