import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {HttpClientService} from '../service/customer/http-client.service';
import {AuthenticationService} from '../authentication/authentication.service';

export class AuthGuard implements CanActivate {
  constructor(private router: Router,
              private authService: AuthenticationService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.checkLogin(state.url);
   /* const user = this.accountService.adminValue;
    if (user) {
      // authorised so return true
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(['/account/login'], { queryParams: { returnUrl: state.url }});
    return false; */
  }
  checkLogin(url: string): boolean {
    // @ts-ignore
    if (this.authService.login()) {
      return true;
    }
    this.authService.redirctURL = url;
    this.router.navigate(['**']);
    return false;
  }

}
