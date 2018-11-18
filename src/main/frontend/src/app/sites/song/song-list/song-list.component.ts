import { Component, OnInit } from '@angular/core';
import {SongService} from "../../../service/song.service";
import {Song} from "../../../model/song";
import {Location} from "@angular/common";

@Component({
  selector: 'app-song-list',
  templateUrl: './song-list.component.html',
  styleUrls: ['./song-list.component.css']
})
export class SongListComponent implements OnInit {

  songs: Song[] = [];

  constructor(
    private songService: SongService,
    private location: Location
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

  goBack() {
    this.location.back();
  }
}
