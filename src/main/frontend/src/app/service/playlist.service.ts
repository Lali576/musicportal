import { Injectable } from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Playlist} from "../model/playlist";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'}
  )
}

@Injectable()
export class PlaylistService {

  playlist: Playlist[];

  constructor(
    private http: HttpClient
  ) { }

  getPlaylists(): Observable<Playlist[]> {
    return this.http.get<Playlist[]>('api/playlist');
  }

  getPlaylist(id: number): Promise<Playlist> {
    return this.http.get<Playlist>(`api/playlist/${id}`).toPromise();
  }

  addPlaylist(playlist: Playlist): Promise<Playlist> {
    return this.http.post<Playlist>(
      `api/playlist`,
      playlist,
      httpOptions
    ).toPromise();
  }

  updatePlaylist(id: number, playlist: Playlist): Promise<Playlist> {
    return this.http.put<Playlist>(
      `api/playlist/${id}`,
      playlist,
      httpOptions
    ).toPromise();
  }

  deletePlaylist(id: number): void {
    this.http.delete(`api/playlist/${id}`);
  }
}
