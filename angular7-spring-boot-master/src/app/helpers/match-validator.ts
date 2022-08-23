import { FormGroup } from '@angular/forms';
import {User_account} from '../models/user_account';
import {HttpClientService} from '../service/customer/http-client.service';

export function CheckMatch(password: string, confirmPassword: string) {
  // tslint:disable-next-line:no-shadowed-variable
  return (formGroup: FormGroup) => {
    const control = formGroup.controls[password];
    const matchingControl = formGroup.controls[confirmPassword];

    if (matchingControl.errors && !matchingControl.errors.mustMatch) {
      // return if another validator has already found an error on the matchingControl
      return;
    }

    // set error on matchingControl if validation fails
    if (control.value !== matchingControl.value) {
      matchingControl.setErrors({ mustMatch: true });
    } else {
      matchingControl.setErrors(null);
    }
  };
}

