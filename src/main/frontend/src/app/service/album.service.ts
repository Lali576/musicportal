import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Album } from "../model/album";
import { Genre } from "../model/genre";
import { AlbumTag } from "../model/Tags/albumtag";
import { tap } from "rxjs/internal/operators";

@Injectable()
export class AlbumService {

  album: Album = new Album();

  constructor(
    private http: HttpClient
  ) { }

  addAlbum(album: Album, coverFile: File, albumGenres: Genre[], albumTags: AlbumTag[]): Promise<Album> {
    const formData = new FormData();
    formData.append("album", JSON.stringify(album));
    if(coverFile !== null) {
      formData.append(coverFile.name, coverFile, coverFile.name);
    }
    formData.append("albumGenres", JSON.stringify(albumGenres));
    formData.append("albumTags", JSON.stringify(albumTags));
    console.log(albumGenres);
    return this.http.post<Album>(
      `api/album/new`,
      formData
    ).toPromise();
  }

  getAlbum(id: number) {
    return this.http.get<Album>(`api/album/${id}`).pipe(
      tap((album: Album) => {
        this.album = album;
      })
    ).toPromise();
  }

  getAllAlbums(): Promise<Album[]> {
    return this.http.get<Album[]>('api/album/list').toPromise()
  }

  getUserAlbums(id: number): Promise<Album[]> {
    return this.http.get<Album[]>(`api/album/by-user/${id}`).toPromise();
  }

  getFirstFiveAlbums(): Promise<Album[]> {
    return this.http.get<Album[]>('api/album/list-first-five').toPromise();
  }

  updateAlbumDetails(id: number, album: Album, albumTitle: string, albumGenres: Genre[], albumTags: AlbumTag[]): Promise<Album> {
    album.title = albumTitle;
    const formData = new FormData();
    formData.append("album", JSON.stringify(album));
    formData.append("albumGenres", JSON.stringify(albumGenres));
    formData.append("albumTags", JSON.stringify(albumTags));
    return this.http.post<Album>(
      `api/album/update/${id}/details`,
      formData
    ).pipe(
      tap((album: Album) => {
        this.album = album;
      })
    ).toPromise();
  }

  updateAlbumCover(id: number, album: Album, coverFile: File): Promise<Album> {
    const formData = new FormData();
    formData.append("album", JSON.stringify(album));
    if(coverFile !== null) {
      formData.append(coverFile.name, coverFile, coverFile.name);
    }
    return this.http.post<Album>(
      `api/album/update/${id}/cover`,
      formData
    ).pipe(
      tap((album: Album) => {
        this.album = album;
      })
    ).toPromise();
  }

  updateAlbumType(id: number, album: Album) {
    const  formData = new FormData();
    formData.append("album", JSON.stringify(album));

    return this.http.post<Album>(
      `api/album/update/${id}/type`,
      formData
    ).pipe(
      tap((album: Album) => {
        this.album = album;
      })
    ).toPromise();
  }

  deleteAlbum(album: Album) {
    console.log("Try to delete album titled " + album.title);
    let id: number = album.id;
    return this.http.delete(
      `api/album/delete/${id}`).pipe(
      tap(() => {
        console.log("Deleting album titled " + album.title + " was successful");
        this.album = null;
      })
    ).toPromise();
  }
}
