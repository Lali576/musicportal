import {Component, EventEmitter, Input, OnChanges, Output} from '@angular/core';
import {Album} from "../../../model/album";
import {Song} from "../../../model/song";

@Component({
  selector: 'app-album-form',
  templateUrl: './album-form.component.html',
  styleUrls: ['./album-form.component.css']
})
export class AlbumFormComponent implements OnChanges {

  @Input() album: Album;
  @Input() songs: Song[];
  modelAlbum: Album = null;
  modelSongs: Song[] = [];
  currentSong: Song = new Song();
  @Output() onSubmit = new EventEmitter<object>();

  constructor() { }

  ngOnChanges() {
    this.modelAlbum = Object.assign({}, this.album);
    this.modelSongs = Object.assign([], this.songs);
  }

  submitAlbum(form) {
    if(!form.valid) {
      return;
    }
    this.onSubmit.emit({album: this.modelAlbum, songs: this.modelSongs});
  }

  submitSong(form) {
    if (!form.valid) {
      return;
    }

    if (this.currentSong.id === 0) {
      var id = this.modelSongs.push(this.currentSong);
    }

    this.currentSong = new Song();
  }

  editSong(song: Song) {
    this.currentSong = song;
  }

  deleteSong(song: Song) {
    var index = this.modelSongs.indexOf(song, 0);
    if(index > -1) {
      this.modelSongs.splice(index, 1);
    }
  }
}
