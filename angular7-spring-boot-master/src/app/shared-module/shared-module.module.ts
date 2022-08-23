import { NgModule } from '@angular/core';

import {HeaderLayoutUserComponent} from '../headers/header-layout-user/header-layout-user.component';
import {HeaderLoginUserComponent} from '../headers/header-login-user/header-login-user.component';
import {CustomerRoutingModule} from '../customers/customer-routing.module';


@NgModule({
  declarations: [
     HeaderLayoutUserComponent,
    HeaderLoginUserComponent,
  ],
  imports: [
    CustomerRoutingModule,
   // AdministratorRoutingModule
    /* MatAutocompleteModule,
     MatFormFieldModule,
     BrowserAnimationsModule,
     FormsModule,
     NgbModule,
     MatInputModule,
     ReactiveFormsModule, */
  ],
  exports: [
    HeaderLayoutUserComponent, HeaderLoginUserComponent, // HeaderLayoutAdiministratorComponent
  ]
})
export class SharedModuleModule { }
