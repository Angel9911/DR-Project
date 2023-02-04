import {Form, FormBuilder, FormGroup, Validators} from '@angular/forms';

export class UserDetails {

  constructor(protected form: FormBuilder, ) {
  }
  // @ts-ignore
  getUsername(form: FormGroup) {
    return form.get('username');
  }

  // @ts-ignore
  getPassword(form: FormGroup) {
    return form.get('password');
  }
  // @ts-ignore
  /*createUserForm() {
    this.loginForm = this.form.group({
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.required]
    });
  } */
}
