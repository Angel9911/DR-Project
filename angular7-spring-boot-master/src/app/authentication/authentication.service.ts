import { Injectable } from '@angular/core';
import {Customer} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  currentCustomer: { password: string; username: string };
  redirctURL: string;

  constructor() { }

  login(username: string, password: string) {
    // tslint:disable-next-line:triple-equals
    if (username == 'admin' && password == 'admin123') {
      localStorage.setItem('currentUser', 'loggedin');
      return true;
    }
  }
  logout() {
    localStorage.removeItem('currentUser');
  }
  public get loggedIn(): boolean {
    return (localStorage.getItem('currentUser') !== null);
  }
}
