import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Album} from "../model/album";
import {User} from "../model/user";
import {Playlist} from "../model/playlist";
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
    return this.http.get<Album[]>(
      `api/search/albums/tag/${tagWord}`
    ).toPromise();
  }

  getAlbumsByGenre(genreWord: string): Promise<Album[]> {
    return this.http.get<Album[]>(
      `api/search/albums/genre/${genreWord}`
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
    return this.http.get<Playlist[]>(
      `api/search/playlists/tag/${tagWord}`
    ).toPromise();
  }

  getUsersByUserTag(tagWord: string): Promise<User[]> {
    return this.http.get<User[]>(
      `api/search/users/tag/${tagWord}`
    ).toPromise();
  }

  getUsersByGenre(genreWord: string): Promise<User[]> {
    return this.http.get<User[]>(
      `api/search/users/genre/${genreWord}`
    ).toPromise();
  }
}
