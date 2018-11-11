import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Album} from "../model/album";
import {AlbumTag} from "../model/tags/albumtag";
import {Genre} from "../model/genre";
import {User} from "../model/user";
import {Playlist} from "../model/playlist";
import {PlaylistTag} from "../model/tags/playlisttag";
import {UserTag} from "../model/tags/usertag";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(
    private http: HttpClient
  ) { }

  getAlbumByTitle(title: string): Promise<Album[]> {
    return this.http.get<Album[]>(
      `api/search/albums/${title}`
    ).toPromise();
  }

  getAlbumsByAlbumTag(albumTag: AlbumTag): Promise<Album[]> {
    const formData = new FormData();
    formData.append("albumTag", JSON.stringify(albumTag));
    return this.http.post<Album[]>(
      "api/search/albums/tag",
      formData
    ).toPromise();
  }

  getAlbumsByGenre(genre: Genre): Promise<Album[]> {
    const formData = new FormData();
    formData.append("genre", JSON.stringify(genre));
    return this.http.post<Album[]>(
      "api/search/albums/genre",
      formData
    ).toPromise();
  }

  getPlaylistByName(name: string): Promise<Playlist[]> {
    return this.http.get<Playlist[]>(
      `api/search/playlists/${name}`
    ).toPromise();
  }

  getPlaylistByPlaylistTag(playlistTag: PlaylistTag): Promise<Playlist[]> {
    const formData = new FormData();
    formData.append("playlistTag", JSON.stringify(playlistTag));
    return this.http.post<Playlist[]>(
      "api/search/playlists/tag",
      formData
    ).toPromise();
  }

  getUsersByUsername(username: string): Promise<User[]> {
    return this.http.get<User[]>(
      `api/search/users/${username}`
    ).toPromise()
  }

  getUsersByUserTag(userTag: UserTag): Promise<User[]> {
    const formData = new FormData();
    formData.append("userTag", JSON.stringify(userTag));
    return this.http.post<User[]>(
      "api/search/users/tag",
      formData
    ).toPromise();
  }
}
