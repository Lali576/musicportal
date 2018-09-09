import { Component, OnInit } from '@angular/core';
import {Song} from "../../../model/song";
import {Playlist} from "../../../model/playlist";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {PlaylistService} from "../../../service/playlist.service";
import {SongService} from "../../../service/song.service";
import {switchMap} from "rxjs/internal/operators";

@Component({
  selector: 'app-playlist-detail',
  templateUrl: './playlist-detail.component.html',
  styleUrls: ['./playlist-detail.component.css']
})
export class PlaylistDetailComponent implements OnInit {

  playlist: Playlist = new Playlist();
  songs: Song[] = [];

  constructor(
    private route: ActivatedRoute,
    private playlistService: PlaylistService,
    private songService: SongService
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = +params.get('id');
      await this.playlistService.getPlaylist(id);
      this.playlist = this.playlistService.playlist;
      console.log(this.playlist.name);
      this.loadSongs();
    })).subscribe();
  }

  loadSongs() {
    this.songService.getSongsByPlaylist(this.playlist)
      .then(
        (songs: Song[]) => {
          this.songs = songs;
        }
      );
}
