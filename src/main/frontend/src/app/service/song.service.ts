import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Song} from "../model/song";
import {Observable} from "rxjs/index";
import {Album} from "../model/album";
import {Playlist} from "../model/playlist";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'}
  )
}

@Injectable()
export class SongService {

  constructor(
    private http: HttpClient
  ) { }

  getSongs(): Observable<Song[]> {
    return this.http.get<Song[]>('api/song');
  }

  getSongsByAlbum(album: Album): Observable<Song[]> {
     return this.http.post<Song[]>(
       `api/song/albumby`,
            album,
       httpOptions);
  }

  getSongsByPlaylist(playlist: Playlist): Observable<Song[]> {
    return this.http.post<Song[]>(
          `api/song/playlistby`,
              playlist,
              httpOptions);
  }

  getSong(id: number): Promise<Song> {
    return this.http.get<Song>(`api/song/${id}`).toPromise();
  }

  addSong(song: Song): Promise<Song> {
    return this.http.post<Song>(
      `api/song`,
      song,
      httpOptions
    ).toPromise();
  }

  updateSong(id: number, song: Song): Promise<Song> {
    return this.http.put<Song>(
      `api/song/${id}`,
      song,
      httpOptions
    ).toPromise();
  }

  deleteSong(id: number): void {
    this.http.delete(`api/song/${id}`);
  }
}
