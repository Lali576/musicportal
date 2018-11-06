import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Album} from "../model/album";
import {Genre} from "../model/genre";
import {AlbumTag} from "../model/Tags/albumtag";
import {tap} from "rxjs/internal/operators";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'}
  )
}

@Injectable()
export class AlbumService {

  album: Album = new Album();

  constructor(
    private http: HttpClient,
  ) { }

  addAlbum(album: Album, coverFile: File, genres: Genre[], Tags: AlbumTag[]): Promise<Album> {
    const formData = new FormData();
    formData.append("album", JSON.stringify(album));
    if(coverFile !== null) {
      formData.append(coverFile.name, coverFile, coverFile.name);
    }
    formData.append("genres", JSON.stringify(genres));
    formData.append("Tags", JSON.stringify(Tags));
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

  updateAlbumDetails(id: number, album: Album, genres: Genre[], Tags: AlbumTag[]): Promise<Album> {
    const formData = new FormData();
    formData.append("album", JSON.stringify(album));
    formData.append("genres", JSON.stringify(genres));
    formData.append("Tags", JSON.stringify(Tags));
    return this.http.post<Album>(
      `api/album/update/${id}/details`,
      formData
    ).pipe(
      tap((album: Album) => {
        this.album = album;
      })
    ).toPromise();
  }

  updateAlbumCover(id: number, coverFile: File): Promise<Album> {
    const formData = new FormData();
    formData.append(coverFile.name, coverFile, coverFile.name);
    return this.http.post<Album>(
      `api/album/update/${id}/cover`,
      formData
    ).pipe(
      tap((album: Album) => {
        this.album = album;
      })
    ).toPromise();
  }

  deleteAlbum(album: Album) {
    console.log("Try to delete album titled " + album.title);
    var id: number = album.id;
    return this.http.delete(
      `api/album/delete/${id}`).pipe(
      tap(() => {
        console.log("Deleting album titled " + album.title + " was successful");
        this.album = null;
      })
    ).toPromise();
  }
}
