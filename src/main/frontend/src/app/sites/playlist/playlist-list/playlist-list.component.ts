import { Component, OnInit } from '@angular/core';
import {Playlist} from "../../../model/playlist";
import {PlaylistService} from "../../../service/playlist.service";
import {ConfirmationService, MessageService} from "primeng/api";

@Component({
  selector: 'app-playlist-list',
  templateUrl: './playlist-list.component.html',
  styleUrls: ['./playlist-list.component.css'],
  providers: [ConfirmationService, MessageService]
})
export class PlaylistListComponent implements OnInit {

  playlists: Playlist[] = [];

  constructor(
    private playlistService: PlaylistService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) { }

  ngOnInit() {
    this.playlistService.getUserPlaylist()
      .then(
        (playlists: Playlist[]) => {
          this.playlists = playlists;
        }
      )
  }

  deletePlaylistConfirm(playlist) {

  }
}
