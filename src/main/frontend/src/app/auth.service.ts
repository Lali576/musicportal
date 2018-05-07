import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {User} from "./model/user";
import {tap} from "rxjs/operators";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'})
}

@Injectable()
export class AuthService {

  isLoggedIn: boolean = false;
  redirectUrl: string = "";
  user: User;

  constructor(
    private http: HttpClient
  ) { }

  register(user: string, password: string) {
    return this.http.post<User>(
      'api/user/register',
      {
        "user": user,
        "password": password
      },
      httpOptions
    ).pipe(
      tap((user: User) => {
        this.isLoggedIn = true;
        this.user = user;
      })
    ).toPromise();
  }

  login(username: string, password: string) {
    console.log(username + " " + password);
    return this.http.post<User>(
        'api/user/login',
      {
        "username": username,
        "password": password
      },
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
