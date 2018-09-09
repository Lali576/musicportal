import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Song} from "../model/song";
import {Observable} from "rxjs/index";
import {Album} from "../model/album";
import {Playlist} from "../model/playlist";
import {GenreService} from "./genre.service";
import {Genre} from "../model/genre";
import {SongKeyword} from "../model/keywords/songkeyword";
import {tap} from "rxjs/internal/operators";
import {SongComment} from "../model/songcomment";
import {SongLike} from "../model/songlike";
import {SongCounter} from "../model/songcounter";

const httpOptions = {
  headers: new HttpHeaders(
    {'Content-Type': 'application/json'}
  )
}

@Injectable()
export class SongService {

  song: Song = null;
  audio;
  songComments: SongComment[];
  likeNumber: number;
  dislikeNumber: number;
  songCounterNumber: number;


  constructor(
    private http: HttpClient
  ) {
    this.audio = new Audio();
  }

  addSong(song: Song, audioFile: File, album: Album, genres: Genre[], keywords: SongKeyword[]): Promise<Song> {
    const formData = new FormData();
    formData.append("song", JSON.stringify(song));
    formData.append(audioFile.name, audioFile, audioFile.name);
    formData.append("album", JSON.stringify(album));
    formData.append("genres", JSON.stringify(genres));
    formData.append("keywords", JSON.stringify(keywords));
    return this.http.post<Song>(
      'api/song/new',
      formData
    ).toPromise();
  }

  getSong(id: number) {
    return this.http.get<Song>(`api/song/${id}`).pipe(
      tap((song: Song) => {
        this.song = song;
      })
    ).toPromise();
  }


  getAllSongs(): Promise<Song[]> {
    return this.http.get<Song[]>('/api/song/list').toPromise();
  }

  getSongs(): Observable<Song[]> {
    return this.http.get<Song[]>('api/song/by-user');
  }

  getSongsByAlbum(album: Album): Promise<Song[]> {
    const formData = new FormData();
    formData.append("album", JSON.stringify(album));
     return this.http.post<Song[]>(
       'api/song/by-album',
            formData).toPromise();
  }

  getSongsByPlaylist(playlist: Playlist): Promise<Song[]> {
    const formData = new FormData();
    formData.append("playlist", JSON.stringify(playlist));
    return this.http.post<Song[]>(
          'api/song/by-playlist',
              formData).toPromise();
  }

  updateSong(id: number, song: Song, audioFile: File, album:Album): Promise<Song> {
    const formData = new FormData();
    formData.append("song", JSON.stringify(song));
    formData.append(audioFile.name, audioFile, audioFile.name);
    formData.append("album", JSON.stringify(album));
    return this.http.put<Song>(
      `api/song/update/${id}`,
      formData
    ).pipe(
      tap((song: Song) => {
        this.song = song;
      })
    ).toPromise();
  }

  deleteSong(song: Song) {
    console.log("Try to delete song titled " + song.title);
    var id: number = song.id;
    return this.http.delete(
      `api/song/delete/${id}`).pipe(
      tap(() => {
        console.log("Deleting song titled " + song.title + " was successful");
        this.song = null;
      })
    ).toPromise();
  }

  addSongComment(songComment: SongComment, song: Song) {
    const formData = new FormData();
    formData.append("songComment", JSON.stringify(songComment));
    formData.append("song", JSON.stringify(song));
    return this.http.post<SongComment[]>(
      'api/song/comments/new',
      formData
    ).pipe(
      tap((songComments: SongComment[]) => {
        this.songComments = songComments;
      })
    ).toPromise();
  }

  getSongComments(id: number, song: Song) {
    const formData = new FormData();
    formData.append("song", JSON.stringify(song));
    return this.http.post<SongComment[]>(
      `api/song/comments/${id}`,
      formData
    ).pipe(
      tap((songComments: SongComment[]) => {
        this.songComments = songComments;
      })
    ).toPromise();
  }

  addSongLike(songLike: SongLike, song: Song) {
    const formData = new FormData();
    formData.append("songLike", JSON.stringify(songLike));
    formData.append("song", JSON.stringify(song));
    return this.http.post<number>(
      'api/song/like/new',
      formData
    ).pipe(
      tap((likeTypeNumber: number) => {
        //
      })
    ).toPromise();
  }

  countSongLikes(id: number, song: Song) {
    const formData = new FormData();
    formData.append("song", JSON.stringify(song));
    return this.http.post<number[]>(
      `api/song/like/${id}`,
      formData
    ).pipe(
      tap((likeTypeNumbers: number[]) => {
        this.likeNumber = likeTypeNumbers[0];
        this.dislikeNumber = likeTypeNumbers[1];
      })
    ).toPromise();
  }

  addSongCounter(songCounter: SongCounter, song: Song) {
    const formData = new FormData();
    formData.append("songCounter", JSON.stringify(songCounter));
    formData.append("song", JSON.stringify(song));
    return this.http.post<number>(
      'api/song/counter/new',
      formData
    ).pipe(
      tap((counterNumber: number) => {
        this.songCounterNumber = counterNumber;
      })
    ).toPromise();
  }

  countSongCounters(id: number, song: Song) {
    const formData = new FormData();
    formData.append("song", JSON.stringify(song));
    return this.http.post<number>(
      `api/song/counter/${id}`,
      formData
    ).pipe(
      tap((counterNumber: number) => {
        this.songCounterNumber = counterNumber;
      })
    ).toPromise();
  }
}
