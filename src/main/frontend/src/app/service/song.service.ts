import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Song} from "../model/song";
import {Observable} from "rxjs/index";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'}
  )
}

@Injectable()
export class SongService {

  songs: Song[];

  constructor(
    private http: HttpClient
  ) { }

  getSongs(): Observable<Song[]> {
    return this.http.get<Song[]>('api/song');
  }

  getSong(id: number): Promise<Song> {
    return this.http.get<Song>(`api/song/${id}`).toPromise();
  }

  addSong(song: Song): Promise<Song> {
    return this.http.post<Song>(
      `api/song`,
      song,
      httpOptions
    ).toPromise();
  }

  updateSong(id: number, song: Song): Promise<Song> {
    return this.http.put<Song>(
      `api/song/${id}`,
      song,
      httpOptions
    ).toPromise();
  }

  deleteSong(id: number): void {
    this.http.delete(`api/song/${id}`);
  }
}
