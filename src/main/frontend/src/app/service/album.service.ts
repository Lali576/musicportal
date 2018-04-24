import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Album} from "../model/album";
import {Observable} from "rxjs/Observable";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'}
  )
}

@Injectable()
export class AlbumService {

  albums: Album[];

  constructor(
    private http: HttpClient
  ) { }

  getAlbums(): Observable<Album[]> {
    return this.http.get<Album[]>('api/album');
  }

  getAlbum(id: number): Promise<Album> {
    return this.http.get<Album>(`api/album/${id}`).toPromise();
  }

  addAlbum(album: Album): Promise<Album> {
    return this.http.post<Album>(
      `api/album`,
      album,
      httpOptions
    ).toPromise();
  }

  updateAlbum(id: number, album: Album): Promise<Album> {
    return this.http.put<Album>(
      `api/album/${id}`,
      album,
      httpOptions
    ).toPromise();
  }

  deleteAlbum(id: number) {
    this.http.delete(`api/album/${id}`)
  }
}
