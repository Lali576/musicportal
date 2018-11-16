import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Playlist} from "../model/playlist";
import {Song} from "../model/song";
import {PlaylistTag} from "../model/Tags/playlisttag";
import {tap} from "rxjs/internal/operators";
import {Album} from "../model/album";
import {AlbumTag} from "../model/tags/albumtag";

@Injectable()
export class PlaylistService {

  playlist: Playlist = new Playlist();

  constructor(
    private http: HttpClient,
  ) { }

  addPlaylist(playlist: Playlist, songs: Song[], playlistTags: PlaylistTag[]): Promise<Playlist> {
    const formData = new FormData();
    formData.append("playlist", JSON.stringify(playlist));
    formData.append("songs", JSON.stringify(songs));
    formData.append("playlistTags", JSON.stringify(playlistTags));
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

  getAllPlaylist(): Promise<Playlist[]> {
    return this.http.get<Playlist[]>('api/playlist/list').toPromise();
  }

  getUserPlaylist(id: number): Promise<Playlist[]> {
    return this.http.get<Playlist[]>(`api/playlist/by-user/${id}`).toPromise();
  }

  getFirstFivePlaylists(): Promise<Playlist[]> {
    return this.http.get<Playlist[]>('api/playlist/list-first-five').toPromise();
  }

  updatePlaylist(id: number, playlist: Playlist, playlistName: string, songs: Song[], playlistTags: PlaylistTag[]): Promise<Playlist> {
    playlist.name = playlistName;
    const formData = new FormData();
    formData.append("playlist", JSON.stringify(playlist));
    formData.append("songs", JSON.stringify(songs));
    formData.append("playlistTags", JSON.stringify(playlistTags));
    return this.http.post<Playlist>(
      `api/playlist/update/${id}`,
      formData
    ).pipe(
      tap((playlist: Playlist) => {
        this.playlist = playlist;
      })
    ).toPromise();
  }

  deletePlaylist(playlist: Playlist) {
    console.log("Try to delete playlist named " + playlist.name);
    let id: number = playlist.id;
    return this.http.delete(`api/playlist/delete/${id}`).pipe(
      tap(() => {
        console.log("Deleting playlist named " + playlist.name + " was successful");
        this.playlist = null;
      })
    ).toPromise();
  }
}
