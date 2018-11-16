import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Song} from "../model/song";
import {Album} from "../model/album";
import {Playlist} from "../model/playlist";
import {Genre} from "../model/genre";
import {tap} from "rxjs/internal/operators";
import {SongComment} from "../model/songcomment";
import {SongLike} from "../model/songlike";

@Injectable()
export class SongService {

  song: Song = null;
  audio;
  songComments: SongComment[];
  likeNumber: number;
  dislikeNumber: number;
  songCounterNumber: number;


  constructor(
    private http: HttpClient,
  ) {
    this.audio = new Audio();
  }

  addSong(song: Song, audioFile: File, album: Album): Promise<Song> {
    const formData = new FormData();
    formData.append("song", JSON.stringify(song));
    formData.append(audioFile.name, audioFile, audioFile.name);
    formData.append("album", JSON.stringify(album));
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

  getSongsByUser(id: number): Promise<Song[]> {
    return this.http.get<Song[]>(`api/song/by-user/${id}`).toPromise();
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

  getFirstFiveSongs(): Promise<Song[]> {
    return this.http.get<Song[]>('api/song/list-first-five').toPromise();
  }

  updateSong(id: number, song: Song, songTitle: string, audioFile: File, album:Album): Promise<Song> {
    song.title = songTitle;
    const formData = new FormData();
    formData.append("song", JSON.stringify(song));
    formData.append(audioFile.name, audioFile, audioFile.name);
    formData.append("album", JSON.stringify(album));
    return this.http.post<Song>(
      `api/song/update/${id}`,
      formData
    ).pipe(
      tap((song: Song) => {
        this.song = song;
      })
    ).toPromise();
  }

  updateLyrics(id: number, song: Song, lyrics: string): Promise<Song> {
    const formData = new FormData();
    formData.append("song", JSON.stringify(song));
    formData.append("lyrics", lyrics);
    return this.http.post<Song>(
      `api/song/update/${id}/lyrics`,
      formData
    ).toPromise();
  }

  deleteSong(song: Song) {
    console.log("Try to delete song titled " + song.title);
    let id: number = song.id;
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
        //this.songComments = songComments;
      })
    ).toPromise();
  }

  getSongComments(id: number, song: Song): Promise<SongComment[]> {
    const formData = new FormData();
    formData.append("song", JSON.stringify(song));
    return this.http.post<SongComment[]>(
      `api/song/comments/${id}`,
      formData
    ).toPromise();
  }

  addSongLike(songLike: SongLike, song: Song): Promise<number> {
    const formData = new FormData();
    formData.append("songLike", JSON.stringify(songLike));
    formData.append("song", JSON.stringify(song));
    return this.http.post<number>(
      'api/song/like/new',
      formData
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

  addSongCounter(song: Song) {
    const formData = new FormData();
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
