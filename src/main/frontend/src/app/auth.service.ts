import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "./model/user";
import {tap} from "rxjs/operators";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}

@Injectable()
export class AuthService {

  isLoggedIn: boolean = false;
  redirectUrl: string;
  user: User;

  constructor(
    private http: HttpClient
  ) { }

  login(user: User) {
    return this.http.post<User>(
      'api/user/login',
      user,
      httpOptions
    ).pipe(
      tap((user: User) => {
        this.isLoggedIn = true;
        this.user = user;
      })
    ).toPromise();
  }

  logout() {
    return this.http.post(
      'api/user/logout',
      {},
      httpOptions
    ).pipe(
      tap(res => {
        this.isLoggedIn = false;
        this.user = new User();
      })
    ).toPromise();
  }
}
