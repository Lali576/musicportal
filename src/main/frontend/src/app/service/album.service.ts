import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Album} from "../model/album";
import {Genre} from "../model/genre";
import {AlbumKeyword} from "../model/keywords/albumkeyword";
import {tap} from "rxjs/internal/operators";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'}
  )
}

@Injectable()
export class AlbumService {

  album: Album;

  constructor(
    private http: HttpClient,
  ) { }

  addAlbum(album: Album, coverFile: File, genres: Genre[], keywords: AlbumKeyword[]): Promise<Album> {
    const formData = new FormData();
    formData.append("album", JSON.stringify(album));
    formData.append(coverFile.name, coverFile, coverFile.name);
    formData.append("genres", JSON.stringify(genres));
    formData.append("keywords", JSON.stringify(keywords));
    return this.http.post<Album>(
      `api/album/new`,
      formData
    ).toPromise();
  }

  getAlbum(id: number) {
    return this.http.get<Album>(`api/album/${id}`).subscribe(
      (album: Album) => {
        this.album = album;
      }
    );
  }

  getAllAlbums(): Promise<Album[]> {
    return this.http.get<Album[]>('api/album/list').toPromise()
  }

  getUserAlbums(): Promise<Album[]> {
    return this.http.get<Album[]>('api/album/by-user').toPromise();
  }

  updateAlbumDetails(id: number, album: Album, genres: Genre[], keywords: AlbumKeyword[]): Promise<Album> {
    const formData = new FormData();
    formData.append("album", JSON.stringify(album));
    formData.append("genres", JSON.stringify(genres));
    formData.append("keywords", JSON.stringify(keywords));
    return this.http.put<Album>(
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
    return this.http.put<Album>(
      `api/album/update/${id}/cover`,
      formData
    ).pipe(
      tap((album: Album) => {
        this.album = album;
      })
    ).toPromise();
  }

  deleteAlbum(id: number):void {;
    this.http.delete(`api/album/delete/${id}`).pipe(
      tap(() => {
        this.album = null;
      })
    );
  }
}
