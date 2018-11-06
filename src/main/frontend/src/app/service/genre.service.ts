import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Genre} from "../model/genre";
import {Observable} from "rxjs/index";
import {Album} from "../model/album";
import {Song} from "../model/song";
import {Playlist} from "../model/playlist";

@Injectable()
export class GenreService {

  constructor(
    private http: HttpClient
  ) {}


  getGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>('api/genre/list');
  }

  getGenresByAlbum(album: Album): Promise<Genre[]> {
    const formData = new FormData();
    formData.append("album", JSON.stringify(album));
    return this.http.post<Genre[]>(
      'api/genre/list-by-album',
      formData).toPromise();
  }

  getGenresBySong(song: Song): Promise<Genre[]> {
    const formData = new FormData();
    formData.append("song", JSON.stringify(song));
    return this.http.post<Genre[]>(
      'api/genre/list-by-song',
      formData
    ).toPromise();
  }
}
