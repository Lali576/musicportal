import { Injectable } from '@angular/core';
import {Observable} from "rxjs/index";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Playlist} from "../model/playlist";
import {AuthService} from "../auth.service";
import {Keyword} from "../model/keyword";
import {Song} from "../model/song";
import {Album} from "../model/album";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'}
  )
}

@Injectable()
export class PlaylistService {

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  getPlaylists(): Observable<Playlist[]> {
    return this.http.get<Playlist[]>('api/playlist');
  }

  getPlaylist(id: number): Promise<Playlist> {
    return this.http.get<Playlist>(`api/playlist/${id}`).toPromise();
  }

  addPlaylist(playlist: Playlist, keywords: Keyword[]): Promise<Playlist> {
    return this.http.post<Playlist>(
      `api/playlist/new`,
      {
        "playlist": playlist,
        "keywords": keywords
      },
      httpOptions
    ).toPromise();
  }

  updatePlaylist(id: number, playlist: Playlist): Promise<Playlist> {
    return this.http.put<Playlist>(
      `api/playlist/edit/${id}`,
      playlist,
      httpOptions
    ).toPromise();
  }

  deletePlaylist(id: number): void {
    this.http.delete(`api/playlist/${id}`);
  }
}
