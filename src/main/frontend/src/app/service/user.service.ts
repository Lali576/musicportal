import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../model/user";
import {Observable} from "rxjs/index";
import {Genre} from "../model/genre";
import {UserMessage} from "../model/usermessage";
import {AuthService} from "./auth.service";
import {tap} from "rxjs/internal/operators";
import {UserKeyword} from "../model/keywords/userkeyword";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'}
  )
}

@Injectable()
export class UserService {

  user: User;
  userMessages: UserMessage[];
  authService: AuthService;

  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) {
    this.authService = auth;
  }

  updateUser(id: number, fullName: string, favGenre: Genre, keywords: UserKeyword[]): Promise<User> {
    console.log("Try to update details for user named " +
      this.authService.loggedInUser.username + " with new details");
    const formData = new FormData();
    formData.append("fullName", fullName);
    formData.append("favGenre", JSON.stringify(favGenre));
    formData.append("keywords", JSON.stringify(keywords));
    return this.http.put<User>(
      `api/user/update/${id}/details`,
      formData
    ).pipe(
      tap((user: User) => {
        console.log("User named " +
          this.authService.loggedInUser + " has been successfully updated with new details")
        this.authService.isLoggedIn = true;
        this.authService.loggedInUser = user;
      })
    ).toPromise();
  }

  updateEmail(id: number, emailAddress: string): Promise<User> {
    const formData = new FormData();
    formData.append("emailAddress", emailAddress);
    return this.http.put<User>(
      `api/user/update/${id}/email`,
      formData
    ).pipe(
      tap((user: User) => {
        this.authService.isLoggedIn = true;
        this.authService.loggedInUser = user;
      })
    ).toPromise();
  }

  updatePassword(id: number, password: string): Promise<User> {
    const formData = new FormData();
    formData.append("password", password);
    return this.http.put<User>(
      `api/user/update/${id}/password`,
      formData
    ).pipe(
      tap((user: User) => {
        this.authService.isLoggedIn = true;
        this.authService.loggedInUser = user;
      })
    ).toPromise();
  }

  updateIconFile(id: number, iconFile: File): Promise<User> {
    const formData = new FormData();
    formData.append(iconFile.name, iconFile, iconFile.name);
    return this.http.put<User>(
      `api/user/update/${id}/icon`,
      formData
    ).pipe(
      tap((user: User) => {
        this.authService.isLoggedIn = true;
        this.authService.loggedInUser = user;
      })
    ).toPromise();
  }

  updateBiography(id: number, biography: string): Promise<User> {
    const formData = new FormData();
    formData.append("biography", biography);
    return this.http.put<User>(
      `api/user/update/${id}/biography`,
      formData
    ).pipe(
      tap((user: User) => {
        this.authService.isLoggedIn = true;
        this.authService.loggedInUser = user;
      })
    ).toPromise();
  }

  getUser(id: number): Promise<User> {
    return this.http.get<User>(`api/user/${id}`).toPromise();
  }

  deleteUser(id: number): void {
    this.http.delete(`api/user/delete/${id}`).pipe(
      tap(() => {
        this.authService.isLoggedIn = false;
        this.authService.loggedInUser = new User();
      })
    );
  }

  getUserMessages(id: number) {
    return this.http.get<UserMessage[]>(`api/user/messages/${id}`).subscribe(
      (userMessages: UserMessage[]) => {
        this.userMessages = userMessages;
      }
    );
  }

  addUserMessages(userMessage: UserMessage) {
    const formData = new FormData();
    formData.append("userMessage", JSON.stringify(userMessage));
    return this.http.post<UserMessage[]>(
      'api/user/messages/new',
      formData
    ).pipe(
      tap((userMessages: UserMessage[]) => {
        this.userMessages = userMessages;
      })
    ).toPromise();
  }
}
