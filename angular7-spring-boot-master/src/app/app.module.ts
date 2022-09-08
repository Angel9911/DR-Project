import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatAutocompleteModule, MatFormFieldModule, MatInputModule} from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { AddCourierComponent } from './administrator/layout-adiministrator/couriers/add-courier/add-courier.component';
import { EditCourierComponent } from './administrator/layout-adiministrator/couriers/edit-courier/edit-courier.component';
import {AuthInterceptorService} from './service/auth/auth-interceptor.service';
import { ForgetPasswordComponent } from './customers/account-user/forget-password/forget-password.component';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
     FooterComponent,
  //  AddCourierComponent,
    EditCourierComponent,
  ],
    imports: [
        BrowserModule,
        // HomeModule,
        // HomeRoutingModule,

        AppRoutingModule,
        // SharedModuleModule,
        // HomeModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        MatAutocompleteModule,
        // MatFormFieldModule,
        BrowserAnimationsModule,
        MatFormFieldModule,
        MatInputModule,
    ],
   providers: [{
     provide: HTTP_INTERCEPTORS,
     useClass: AuthInterceptorService,
     multi: true
   }],
   /* entryComponents: [
    HeaderComponent, FooterComponent,
  ], */
  exports: [
    HeaderComponent
  ],
  bootstrap: [AppComponent /* HomeComponent, HomeComponent, HeaderComponent*/]
})
export class AppModule { }
