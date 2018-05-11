import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Song} from "../model/song";
import {Observable} from "rxjs/index";
import {Album} from "../model/album";
import {Playlist} from "../model/playlist";
import {Genre} from "../model/genre";
import {Keyword} from "../model/keyword";

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

  getAllSongs(): Observable<Song[]> {
    return this.http.get<Song[]>('/api/song/list');
  }

  getSongs(): Observable<Song[]> {
    return this.http.get<Song[]>('api/song');
  }

  getSongsByAlbum(album: Album): Promise<Song[]> {
     return this.http.post<Song[]>(
       `api/song/albumby`,
            album,
       httpOptions).toPromise();
  }

  getSongsByPlaylist(playlist: Playlist): Promise<Song[]> {
    return this.http.post<Song[]>(
          `api/song/playlistby`,
              playlist,
              httpOptions).toPromise();
  }

  getSong(id: number): Promise<Song> {
    return this.http.get<Song>(`api/song/${id}`).toPromise();
  }

  addSong(song: String, album: String, genres: String, keywords: String): Promise<Song> {
    return this.http.post<Song>(
      `api/song/new`,
      {
        "song": song,
        "album": album,
        "genres": genres,
        "keywords": keywords
      },
      httpOptions
    ).toPromise();
  }

  updateSong(id: number, song: String, album:String): Promise<Song> {
    return this.http.put<Song>(
      `api/song/edit/${id}`,
      {
        "song": song,
        "album": album
      },
      httpOptions
    ).toPromise();
  }

  deleteSong(id: number): void {
    this.http.delete(`api/song/${id}`);
  }
}
