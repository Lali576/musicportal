import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {User} from "../model/user";
import {tap} from "rxjs/operators";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'})
}

@Injectable()
export class AuthService {

  isLoggedIn: boolean = false;
  redirectUrl: string = "";
  loggedInUser: User;

  constructor(
    private http: HttpClient
  ) { }

  register(uploadData: FormData) {
    return this.http.post<User>(
      'api/user/register',
      uploadData
    ).pipe(
      tap((user: User) => {
        this.isLoggedIn = true;
        this.loggedInUser = user;
      })
    ).toPromise();
  }

  login(uploadData: FormData) {
    return this.http.post<User>(
      'api/user/login',
      uploadData
    ).pipe(
      tap((user: User) => {
        this.isLoggedIn = true;
        this.loggedInUser = user;
      })
    ).toPromise();
  }

  getUser() {
    console.log("Try to get possible logged in user");
    this.http.get<User>('api/user/get').subscribe(
      (user : User) => {
        if (user !== null) {
          this.loggedInUser = user;
          this.isLoggedIn = true;
        }
      }
    );
  }

  logout() {
    return this.http.post(
      'api/user/logout',
      {},
      httpOptions
    ).pipe(
      tap(res => {
        this.isLoggedIn = false;
        this.loggedInUser = new User();
      })
    ).toPromise();
  }
}
