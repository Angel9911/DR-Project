import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HeaderCourierComponent} from '../headers/header-courier/header-courier.component';
import {CourierRoutingModule} from '../courier/courier-routing.module';
import {HeaderLoginCourierComponent} from '../headers/header-login-courier/header-login-courier.component';
import {MatButtonModule, MatDialogModule, MatFormFieldModule, MatInputModule} from '@angular/material';
import {FormsModule} from '@angular/forms';
import {ConfirmDialogService} from '../service/confirm-dialog.service';
import {CommentDialogComponent} from '../helpers/comment-dialog/comment-dialog.component';

@NgModule({
  declarations: [
    HeaderCourierComponent,
    HeaderLoginCourierComponent,
  ],
  imports: [
    CourierRoutingModule,
    MatDialogModule,
    MatFormFieldModule,
    FormsModule,
    MatButtonModule,
    MatInputModule
  ],
  providers: [],
  exports: [
    HeaderCourierComponent,
    HeaderLoginCourierComponent,
  ],
  entryComponents: []
})
export class SharedModuleCourierModule { }
