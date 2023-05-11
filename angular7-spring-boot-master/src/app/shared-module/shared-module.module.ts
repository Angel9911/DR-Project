import { NgModule } from '@angular/core';

import {HeaderLayoutUserComponent} from '../headers/header-layout-user/header-layout-user.component';
import {HeaderLoginUserComponent} from '../headers/header-login-user/header-login-user.component';
import {CustomerRoutingModule} from '../customers/customer-routing.module';
import {ChatbotComponent} from '../chatbot/chatbot.component';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';


@NgModule({
  declarations: [
     HeaderLayoutUserComponent,
    HeaderLoginUserComponent,
    ChatbotComponent
  ],
  imports: [
    CustomerRoutingModule,
    CommonModule,
    FormsModule,
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
    HeaderLayoutUserComponent, HeaderLoginUserComponent, ChatbotComponent // HeaderLayoutAdiministratorComponent
  ]
})
export class SharedModuleModule { }
