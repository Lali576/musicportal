import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Album} from "../model/album";
import {Observable} from "rxjs/index";
import {Song} from "../model/song";
import {AuthGuard} from "../auth.guard";
import {AuthService} from "../auth.service";
import {User} from "../model/user";
import {Keyword} from "../model/keyword";
import {Genre} from "../model/genre";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'}
  )
}

@Injectable()
export class AlbumService {

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  getAlbums(): Observable<Album[]> {
    return this.http.get<Album[]>('api/album');
  }

  getAlbum(id: number): Promise<Album> {
    return this.http.get<Album>(`api/album/${id}`).toPromise();
  }

  addAlbum(album: Album, user: User, genres: Genre[], keywords: Keyword[]): Promise<Album> {
    return this.http.post<Album>(
      `api/album/new`,
      {
        "album": album,
        "user": user,
        "genres": genres,
        "keywords": keywords
      },
      httpOptions
    ).toPromise();
  }

  updateAlbum(id: number, album: Album): Promise<Album> {
    return this.http.put<Album>(
      `api/album/edit/${id}`,
      album,
      httpOptions
    ).toPromise();
  }

  deleteAlbum(id: number) {
    this.http.delete(`api/album/${id}`)
  }
}
