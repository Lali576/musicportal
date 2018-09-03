import { Component, OnInit } from '@angular/core';
import {Playlist} from "../../../model/playlist";
import {PlaylistService} from "../../../service/playlist.service";
import {AuthService} from "../../../service/auth.service";

@Component({
  selector: 'app-playlist-list',
  templateUrl: './playlist-list.component.html',
  styleUrls: ['./playlist-list.component.css']
})
export class PlaylistListComponent implements OnInit {

  playlist: Playlist[] = [];

  constructor(
    private playlistService: PlaylistService,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.playlistService.getAllPlaylist()
      .then(
        (playlist: Playlist[]) => {
          this.playlist = playlist;
        }
      )
  }

  delete(id: number) {
    //this.playlistService.deletePlaylist(id);
  }

}
