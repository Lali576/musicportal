import { Injectable } from '@angular/core';
import {Observable} from "rxjs/index";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Playlist} from "../model/playlist";
import {AuthService} from "./auth.service";
import {Song} from "../model/song";
import {PlaylistKeyword} from "../model/keywords/playlistkeyword";
import {tap} from "rxjs/internal/operators";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'}
  )
}

@Injectable()
export class PlaylistService {

  playlist: Playlist = new Playlist();

  constructor(
    private http: HttpClient,
  ) { }

  addPlaylist(playlist: Playlist, songs: Song[], keywords: PlaylistKeyword[]): Promise<Playlist> {
    const formData = new FormData();
    formData.append("playlist", JSON.stringify(playlist));
    formData.append("songs", JSON.stringify(songs));
    formData.append("keywords", JSON.stringify(keywords));
    return this.http.post<Playlist>(
      `api/playlist/new`,
      formData
    ).toPromise();
  }

  getPlaylist(id: number) {
    return this.http.get<Playlist>(`api/playlist/${id}`).pipe(
      tap((playlist: Playlist) => {
        this.playlist = playlist;
      })
    ).toPromise();
  }

  getUserPlaylist(): Promise<Playlist[]> {
    return this.http.get<Playlist[]>('api/playlist/by-user').toPromise();
  }

  getAllPlaylist(): Promise<Playlist[]> {
    return this.http.get<Playlist[]>('api/playlist').toPromise();
  }

  updatePlaylist(id: number, playlist: Playlist, songs: Song[], keywords: PlaylistKeyword[]): Promise<Playlist> {
    const formData = new FormData();
    formData.append("playlist", JSON.stringify(playlist));
    formData.append("songs", JSON.stringify(songs));
    formData.append("keywords", JSON.stringify(keywords));
    return this.http.post<Playlist>(
      `api/playlist/update/${id}`,
      formData
    ).pipe(
      tap((playlist: Playlist) => {
        this.playlist = playlist;
      })
    ).toPromise();
  }

  deletePlaylist(id: number) {
    return this.http.delete(`api/playlist/delete/${id}`).pipe(
      tap(() => {
        this.playlist = null;
      })
    ).toPromise();
  }
}
