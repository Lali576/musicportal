import { Component, OnInit } from '@angular/core';
import {Playlist} from "../../../model/playlist";
import {PlaylistService} from "../../../service/playlist.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-playlist-list',
  templateUrl: './playlist-list.component.html',
  styleUrls: ['./playlist-list.component.css']
})
export class PlaylistListComponent implements OnInit {

  playlists: Playlist[] = [];

  constructor(
    private playlistService: PlaylistService,
    private location: Location
  ) { }

  ngOnInit() {
    this.loadPlaylists();
  }

  loadPlaylists() {
    this.playlistService.getAllPlaylist()
      .then(
        (playlists: Playlist[]) => {
          this.playlists = playlists;
        }
      );
  }

  goBack() {
    this.location.back();
  }
}
