import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs/index';
import { AuthService} from "./service/auth.service";

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(
    private autService: AuthService,
    private router: Router
  ) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    if(this.autService.isLoggedIn) {
      if(next.data.roles && next.data.roles.includes(this.autService.user.role)) {
        return true;
      } else {
        return false;
      }
    }
    this.autService.redirectUrl = state.url;
    this.router.navigate(['/login']);
    return false;
  }
}
