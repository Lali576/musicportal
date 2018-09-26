import { Component, OnInit } from '@angular/core';
import {Playlist} from "../../../model/playlist";
import {PlaylistService} from "../../../service/playlist.service";

@Component({
  selector: 'app-playlist-list',
  templateUrl: './playlist-list.component.html',
  styleUrls: ['./playlist-list.component.css']
})
export class PlaylistListComponent implements OnInit {

  playlists: Playlist[] = [];

  constructor(
    private playlistService: PlaylistService
  ) { }

  ngOnInit() {
    this.playlistService.getUserPlaylist()
      .then(
        (playlists: Playlist[]) => {
          this.playlists = playlists;
        }
      )
  }
}
