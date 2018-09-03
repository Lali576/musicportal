import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {User} from "../model/user";
import {tap} from "rxjs/operators";
import {UserKeyword} from "../model/keywords/userkeyword";

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

  getUser() {
    console.log("Try to get possible logged in user");
    this.http.get<User>('api/user/get').subscribe(
      (user : User) => {
        if (user !== null) {
          console.log("Logged in user found with name " + user.username)
          this.loggedInUser = user;
          this.isLoggedIn = true;
        }
        console.log("There is no logged in user");
      }
    );
  }

  register(user: User, password: string, iconFile: File, keywords: UserKeyword[]) {
    console.log("Try to register user with name...");
    const formData = new FormData();
    formData.append("user", JSON.stringify(user));
    formData.append("password", password);
    formData.append(iconFile.name, iconFile, iconFile.name);
    formData.append("userKeywords", JSON.stringify(keywords));
    return this.http.post<User>(
      'api/user/register',
      formData
    ).pipe(
      tap((user: User) => {
        console.log("User named " + user.username + " has been successfully registered");
        this.loggedInUser = user;
        this.isLoggedIn = true;
      })
    ).toPromise();
  }

  login(username: string, password: string) {
    console.log("Try to login user with username " + username);
    const formData = new FormData();
    formData.append("username", username);
    formData.append("password", password);
    return this.http.post<User>(
      'api/user/login',
      formData
    ).pipe(
      tap((user: User) => {
        console.log("User with name... has been successfully found and been logged in");
        this.loggedInUser = user;
        this.isLoggedIn = true;
      })
    ).toPromise();
  }

  logout() {
    console.log("Try to log out user with username " + this.loggedInUser.username)
    return this.http.get(
      'api/user/logout'
    ).pipe(
      tap(() => {
        console.log("Logged in user has been successfully logged out")
        this.loggedInUser = new User();
        this.isLoggedIn = false;
      })
    ).toPromise();
  }
}
