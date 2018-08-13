import { Component, OnInit } from '@angular/core';
import {SongService} from "../../../service/song.service";
import {AuthService} from "../../../auth.service";
import {Song} from "../../../model/song";

@Component({
  selector: 'app-song-list',
  templateUrl: './song-list.component.html',
  styleUrls: ['./song-list.component.css']
})
export class SongListComponent implements OnInit {

  songs: Song[] = [];

  constructor(
    private songService: SongService,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.songService.getSongs()
      .subscribe(songs => {
        this.songs = songs;
      });
  }
}
