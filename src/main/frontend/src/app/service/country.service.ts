import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/index";
import {Country} from "../model/country";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'}
  )
}

@Injectable()
export class CountryService {

  constructor(
    private http: HttpClient
  ) { }

  getCountries(): Observable<Country[]> {
    return this.http.get<Country[]>('api/country/list');
  }
}
