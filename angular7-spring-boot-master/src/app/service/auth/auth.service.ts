import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {catchError, map} from 'rxjs/operators';
import {throwError} from 'rxjs';
import {Router} from '@angular/router';

const headers = new HttpHeaders().set('Content-Type', 'application/json');

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient, private router: Router) {
  }

  authenticate(username: string, password: string) {
    // @ts-ignore
    // tslint:disable-next-line:max-line-length
    return this.httpClient.post<any>('http://localhost:8082/login', {
      username,
      password
    }, {headers}).pipe(catchError(this.handleError), map(userData => {
      sessionStorage.setItem('username', username);
      const tokenStr = 'Bearer ' + userData.token;
      console.log('Token' + tokenStr);
      sessionStorage.setItem('token', tokenStr);
      sessionStorage.setItem('roles', JSON.stringify(userData.roles));
      return userData;
    }));
  }

  public getToken() {
    return sessionStorage.getItem('token');
  }

  logout() {
    sessionStorage.clear();
    this.router.navigate(['']);
  }

  isLoggedIn(): boolean {
    return sessionStorage.getItem('username') !== null;
  }

  private handleError(httpError: HttpErrorResponse) {
    let message = '';
    if (httpError.error instanceof ProgressEvent) {
      console.log('in progrss event');
      message = 'Network error';
    } else {
      message = httpError.error.message;
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${httpError.status}, ` +
        `body was: ${httpError.error}`);
    }
    // Return an observable with a user-facing error message.
    return throwError(message);
  }
}
