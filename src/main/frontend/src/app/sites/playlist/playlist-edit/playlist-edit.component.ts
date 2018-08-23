import { Component, OnInit } from '@angular/core';
import {Playlist} from "../../../model/playlist";
import {Song} from "../../../model/song";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {SongService} from "../../../service/song.service";
import {Location} from "@angular/common";
import {PlaylistService} from "../../../service/playlist.service";
import {Album} from "../../../model/album";
import {switchMap} from "rxjs/internal/operators";
import {Observable, of} from "rxjs/index";
import {Keyword} from "../../../model/keyword";

@Component({
  selector: 'app-playlist-edit',
  templateUrl: './playlist-edit.component.html',
  styleUrls: ['./playlist-edit.component.css']
})
export class PlaylistEditComponent implements OnInit {

  playlist: Playlist;
  songs: Song[];

  constructor(
    private route: ActivatedRoute,
    private playlistService: PlaylistService,
    private songService: SongService,
    private location: Location
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = params.get('id');
      if(id !== null) {
        this.playlist = await this.playlistService.getPlaylist(+id);
        this.songs = await this.songService.getSongsByPlaylist(this.playlist);
      } else {
        this.playlist = new Playlist();
      }
      return of (Observable);
    })).subscribe();
  }

  async onFormSubmit(params: object) {
    var playlist: Playlist = params["playlist"];
    var songs: Song[] = params["songs"];
    var keywords: Keyword[] = params["keywords"];

    var playlistString = JSON.stringify(playlist);
    var songsString = JSON.stringify(songs);
    var stringKeywords = JSON.stringify(keywords);

    if(playlist.id > 0) {
      await this.playlistService.updatePlaylist(playlist.id, playlistString, songsString, stringKeywords);
    } else {
      await this.playlistService.addPlaylist(playlistString, songsString, null);
    }
    this.location.back();
  }
}
