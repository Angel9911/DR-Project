import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor(public authService: AuthService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let modifiedReq = req;
    console.log(this.authService.getToken());
    if (sessionStorage.getItem('username') && sessionStorage.getItem('token')) {
      console.log('With Token --- ' + sessionStorage.getItem('token'));
      modifiedReq = req.clone({
        setHeaders: {
          Authorization: this.authService.getToken()
        }
      });
    }
    return next.handle(modifiedReq);
  }
}
