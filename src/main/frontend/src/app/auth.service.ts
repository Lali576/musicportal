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

  register(uploadData: FormData) {
    return this.http.post<User>(
      'api/user/register',
      uploadData
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

  getUser() {
    console.log("Try to get possible logged in user");
    this.http.get<User>('api/user/get').subscribe(
      (user : User) => {
        this.user = user;
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
        this.user = new User();
      })
    ).toPromise();
  }
}
