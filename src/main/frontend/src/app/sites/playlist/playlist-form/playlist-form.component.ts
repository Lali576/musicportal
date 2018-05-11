import {Component, EventEmitter, Input, OnChanges, Output} from '@angular/core';
import {Playlist} from "../../../model/playlist";
import {Song} from "../../../model/song";
import {SongService} from "../../../service/song.service";

@Component({
  selector: 'app-playlist-form',
  templateUrl: './playlist-form.component.html',
  styleUrls: ['./playlist-form.component.css']
})
export class PlaylistFormComponent implements OnChanges {

  @Input() playlist: Playlist;
  @Input() songs: Song[] = [];
  modelPlaylist: Playlist = null;
  modelSelectedSongs: Song[] = [];
  allSongs: Song[] = [];
  @Output() onSubmit = new EventEmitter<object>();

  constructor(
    private songService: SongService
  ) { }

  ngOnChanges() {
    this.modelPlaylist = Object.assign({}, this.playlist);
    this.modelSelectedSongs = Object.assign([], this.songs);

    this.songService.getAllSongs()
      .subscribe(albums => {
        this.allSongs = albums;
      });
  }

  submitPlaylist(form) {
    if(!form.valid) {
      return;
    }
    this.onSubmit.emit({playlist: this.modelPlaylist, songs: this.modelSelectedSongs});
  }

  selectSong(song: Song) {
    var id = this.modelSelectedSongs.push(song);

    var index = this.allSongs.indexOf(song, 0);
    if(index > -1 ) {
      this.allSongs.splice(index, 1);
    }
  }

  deleteSong(song: Song) {
    var id = this.allSongs.push(song);

    var index = this.modelSelectedSongs.indexOf(song, 0);
    if(index > -1 ) {
      this.modelSelectedSongs.splice(index, 1);
    }
  }
}
