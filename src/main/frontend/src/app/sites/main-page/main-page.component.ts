import { Component, OnInit } from '@angular/core';
import {Album} from "../../model/album";
import {Playlist} from "../../model/playlist";
import {Song} from "../../model/song";
import {AlbumService} from "../../service/album.service";
import {PlaylistService} from "../../service/playlist.service";
import {SongService} from "../../service/song.service";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  firstFiveAlbums: Album[] = [];
  firstFivePlaylists: Playlist[] = [];
  firstFiveSongs: Song[] = [];

  constructor(
    private albumService: AlbumService,
    private playlistService: PlaylistService,
    private songService: SongService
  ) { }

  ngOnInit() {
    this.loadAlbums();
    this.loadPlaylists();
    this.loadSongs();
  }

  loadAlbums() {
    this.albumService.getFirstFiveAlbums()
      .then(
        (albums: Album[]) => {
          this.firstFiveAlbums = albums;
        }
      );
  }

  loadPlaylists() {
    this.playlistService.getFirstFivePlaylists()
      .then(
        (playlists: Playlist[]) => {
          this.firstFivePlaylists = playlists;
        }
      );
  }

  loadSongs() {
    this.songService.getFirstFiveSongs()
      .then(
        (songs: Song[]) => {
          this.firstFiveSongs = songs;
        }
      );
  }
}
