import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Genre} from "../model/genre";
import {Observable} from "rxjs/index";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'}
  )
}

@Injectable()
export class GenreService {

  constructor(
    private http: HttpClient
  ) {

  }

  getGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>('/api/genre/list');
  }
}
