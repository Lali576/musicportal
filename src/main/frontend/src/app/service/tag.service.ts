import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Album} from "../model/album";
import {AlbumTag} from "../model/tags/albumtag";
import {Playlist} from "../model/playlist";
import {PlaylistTag} from "../model/tags/playlisttag";
import {UserTag} from "../model/tags/usertag";

@Injectable({
  providedIn: 'root'
})
export class TagService {

  constructor(
    private http: HttpClient
  ) { }

  getTagsByAlbum(album: Album): Promise<AlbumTag[]> {
    const formData = new FormData();
    formData.append("album", JSON.stringify(album));
    return this.http.post<AlbumTag[]>(
      'api/tag/list-by-album',
      formData
    ).toPromise();
  }

  getTagsByPlaylist(playlist: Playlist): Promise<PlaylistTag[]> {
    const formData = new FormData();
    formData.append("playlist", JSON.stringify(playlist));
    return this.http.post<PlaylistTag[]>(
      'api/tag/list-by-playlist',
      formData
    ).toPromise()
  }

  getTagsByUser(id: number): Promise<UserTag[]> {
    return this.http.get<UserTag[]>(`api/tag/list-by-user/${id}`).toPromise();
  }
}
