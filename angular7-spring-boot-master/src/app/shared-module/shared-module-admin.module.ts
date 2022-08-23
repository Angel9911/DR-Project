import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AdministratorRoutingModule} from '../administrator/administrator-routing.module';
import {HeaderLayoutAdiministratorComponent} from '../headers/header-layout-adiministrator/header-layout-adiministrator.component';
import {HeaderLoginAdministratorComponent} from '../headers/header-login-administrator/header-login-administrator.component';
import {ConfirmDialogComponent} from '../helpers/confirm-dialog/confirm-dialog.component';
import {ConfirmDialogService} from '../service/confirm-dialog.service';
import {MatButtonModule, MatDialogModule, MatFormFieldModule, MatInputModule} from '@angular/material';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    HeaderLayoutAdiministratorComponent,
    HeaderLoginAdministratorComponent,
    ConfirmDialogComponent
  ],
  imports: [
    AdministratorRoutingModule,
    MatDialogModule,
    MatFormFieldModule,
    FormsModule,
    MatButtonModule,
    MatInputModule
  ],
  providers: [ConfirmDialogService],
  exports: [
     HeaderLayoutAdiministratorComponent, HeaderLoginAdministratorComponent
  ],
  entryComponents: [ConfirmDialogComponent],
})
export class SharedModuleAdminModule { }
