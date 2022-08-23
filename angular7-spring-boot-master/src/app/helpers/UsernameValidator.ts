import {HttpClientService} from '../service/customer/http-client.service';
import {AbstractControl, AsyncValidatorFn, ValidationErrors} from '@angular/forms';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Customer} from '../models/user';
import {error} from 'util';

export function existingUsernameValidator(userService: HttpClientService): AsyncValidatorFn {
  return (control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> => {
    console.log('in validator: ' + control.value);
    return userService.findUsername(control.value).pipe(map(
      (user: boolean) => {
        // tslint:disable-next-line:triple-equals
        if (user != false) {
          return {usernameExists: true};
        } else {
          return null;
        }      }
    ));
  };
}
export function existingEmailValidator(userService: HttpClientService): AsyncValidatorFn {
  return (control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> => {
    console.log('in email validator: ' + control.value);
    return userService.findEmail(control.value).pipe(map(
      (customer: boolean) => {
        // tslint:disable-next-line:triple-equals
        if (customer != false) {
          return {emailExists: true};
        } else {
          return null;
        }
      },
      // tslint:disable-next-line:no-shadowed-variable
      (error) => {
        console.log(error);
      }
    ));
  };
}
