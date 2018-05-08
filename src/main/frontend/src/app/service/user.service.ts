import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../model/user";
import {Observable} from "rxjs/index";
import {Genre} from "../model/genre";
import {UserMessage} from "../model/usermessage";
import {AuthService} from "../auth.service";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'}
  )
}

@Injectable()
export class UserService {

  user: User;
  authService: AuthService;

  constructor(
    private http: HttpClient,
    private authServicse: AuthService
  ) {
    this.authService = authServicse;
  }

  getGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>('/api/genre/list');
  }

  getUser(id: number): Promise<User> {
    return this.http.get<User>(`api/user/${id}`).toPromise();
  }

  addUser(user: User): Promise<User> {
    return this.http.post<User>(
      `api/user/register`,
      user,
      httpOptions
    ).toPromise();
  }

  updateUser(id: number, user: User): Promise<User> {
    return this.http.put<User>(
      `api/user/${id}`,
      user,
      httpOptions
    ).toPromise();
  }

  deleteUser(id: number): void {
    this.http.delete(`api/user/${id}`);
  }
}