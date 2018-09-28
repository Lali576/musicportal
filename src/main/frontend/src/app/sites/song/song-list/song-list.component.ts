import { Component, OnInit } from '@angular/core';
import {SongService} from "../../../service/song.service";
import {Song} from "../../../model/song";

@Component({
  selector: 'app-song-list',
  templateUrl: './song-list.component.html',
  styleUrls: ['./song-list.component.css']
})
export class SongListComponent implements OnInit {

  songs: Song[] = [];

  constructor(
    private songService: SongService
  ) { }

  ngOnInit() {
    this.loadSongs()
  }

  loadSongs() {
    this.songService.getAllSongs()
      .then(
        (songs: Song[]) => {
          this.songs = songs;
        }
      )
  }
}
