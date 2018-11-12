import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../model/user";
import {Genre} from "../model/genre";
import {UserMessage} from "../model/usermessage";
import {AuthService} from "./auth.service";
import {tap} from "rxjs/internal/operators";
import {UserTag} from "../model/Tags/usertag";

@Injectable()
export class UserService {

  user: User = new User();

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  updateUser(id: number, fullName: string, favGenre: Genre, userTags: UserTag[]): Promise<User> {
    console.log("Try to update details for user named " +
      this.authService.loggedInUser.username + " with new details");
    const formData = new FormData();
    formData.append("fullName", fullName);
    formData.append("favGenre", JSON.stringify(favGenre));
    formData.append("userTags", JSON.stringify(userTags));
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

  updatePassword(id: number, oldPassword: string, newPassword: string): Promise<User> {
    const formData = new FormData();
    formData.append("oldPassword", oldPassword);
    formData.append("newPassword", newPassword);
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
    if(iconFile !==  null) {
      formData.append(iconFile.name, iconFile, iconFile.name);
    }
    return this.http.post<User>(
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
    return this.http.post<User>(
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

  deleteUser(id: number) {
    console.log("Try to delete user named " + this.authService.loggedInUser.username);
    return this.http.delete(`api/user/delete/${id}`).pipe(
      tap(() => {
        console.log("Deleting user named " + this.authService.loggedInUser.username + " was successful");
        this.authService.isLoggedIn = false;
        this.authService.loggedInUser = new User();
      })
    ).toPromise();
  }

  getUserMessages(id: number): Promise<UserMessage[]> {
    return this.http.get<UserMessage[]>(
      `api/user/messages/${id}`
    ).toPromise();
  }

  addUserMessages(id: number, userMessage: UserMessage): Promise<UserMessage[]> {
    const formData = new FormData();
    formData.append("userMessage", JSON.stringify(userMessage));
    return this.http.post<UserMessage[]>(
      `api/user/messages/new/${id}`,
      formData
    ).toPromise();
  }
}
