import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
//import {MatAutocompleteModule, MatFormFieldModule, MatInputModule} from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { EditCourierComponent } from './administrator/layout-adiministrator/couriers/edit-courier/edit-courier.component';
import {AuthInterceptorService} from './service/auth/auth-interceptor.service';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
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
        NgbModule,
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
