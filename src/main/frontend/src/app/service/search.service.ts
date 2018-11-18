import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Album} from "../model/album";
import {AlbumTag} from "../model/tags/albumtag";
import {Genre} from "../model/genre";
import {User} from "../model/user";
import {Playlist} from "../model/playlist";
import {PlaylistTag} from "../model/tags/playlisttag";
import {UserTag} from "../model/tags/usertag";
import {Tag} from "../model/tags/tag";
import {Song} from "../model/song";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(
    private http: HttpClient
  ) { }

  getAlbumsByTitle(searchWord: string): Promise<Album[]> {
    return this.http.get<Album[]>(
      `api/search/albums/${searchWord}`
    ).toPromise();
  }

  getAlbumsByAlbumTag(tagWord: string): Promise<Album[]> {
    const formData = new FormData();
    formData.append("tag", tagWord);
    return this.http.post<Album[]>(
      "api/search/albums/tag",
      formData
    ).toPromise();
  }

  getAlbumsByGenre(genreWord: string): Promise<Album[]> {
    const formData = new FormData();
    formData.append("genre", genreWord);
    return this.http.post<Album[]>(
      "api/search/albums/genre",
      formData
    ).toPromise();
  }

  getSongsByTitle(searchWord: string): Promise<Song[]> {
    return this.http.get<Song[]>(
      `api/search/songs/${searchWord}`
    ).toPromise();
  }

  getPlaylistByName(searchWord: string): Promise<Playlist[]> {
    return this.http.get<Playlist[]>(
      `api/search/playlists/${searchWord}`
    ).toPromise();
  }

  getPlaylistByPlaylistTag(tagWord: string): Promise<Playlist[]> {
    const formData = new FormData();
    formData.append("tag", tagWord);
    return this.http.post<Playlist[]>(
      "api/search/playlists/tag",
      formData
    ).toPromise();
  }

  getUsersByUserTag(tagWord: string): Promise<User[]> {
    const formData = new FormData();
    formData.append("tag", tagWord);
    return this.http.post<User[]>(
      "api/search/users/tag",
      formData
    ).toPromise();
  }

  getUsersByGenre(genreWord: string): Promise<User[]> {
    const formData = new FormData();
    formData.append("genre", genreWord);
    return this.http.post<User[]>(
      "api/search/users/genre",
      formData
    ).toPromise();
  }
}
