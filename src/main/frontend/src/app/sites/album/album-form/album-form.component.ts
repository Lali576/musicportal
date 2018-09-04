import {Component, OnInit} from '@angular/core';
import {Album} from "../../../model/album";
import {Song} from "../../../model/song";
import {AlbumKeyword} from "../../../model/keywords/albumkeyword";
import {GenreService} from "../../../service/genre.service";
import {AlbumService} from "../../../service/album.service";
import {SongService} from "../../../service/song.service";
import {Genre} from "../../../model/genre";
import {Router} from "@angular/router";

@Component({
  selector: 'app-album-form',
  templateUrl: './album-form.component.html',
  styleUrls: ['./album-form.component.css']
})
export class AlbumFormComponent implements OnInit {

  album: Album = new Album();
  albumCoverFile: File = null;
  albumGenre: Genre[] = [];
  albumKeywords: AlbumKeyword[] = [];
  genres: Genre[] = [];
  albumSongs: object[][] = [];
  currentSong: Song = new Song();
  currentSongFile: File = null;
  message: string = "";

  constructor(
    private albumService: AlbumService,
    private songService: SongService,
    private genreService: GenreService,
    private route: Router
  ) {
    this.genreService.getGenres().subscribe(
      (genres: Genre[]) => {
        this.genres = genres;
      }
    )
  }

  ngOnInit() {

  }

  onAlbumCoverFileSelected(event) {
    this.albumCoverFile = <File>event.target.files[0];
    console.log(this.albumCoverFile);
  }

  async submitAlbum(form) {
    if(!form.valid) {
      return;
    }
    try {
      console.log(this.album);
      this.message = "Album feltöltése folyamatban";
      await this.albumService.addAlbum(this.album, this.albumCoverFile, this.albumGenre, this.albumKeywords);
      this.message = "Album feltöltése sikeres";
      this.route.navigate(['/album/list']);
    } catch (e) {
      this.message = "Album feltöltése sikertelen";
      console.log(e);
    }
  }

  onSongAudioFileSelected(event) {
    var file: File = <File>event.target.files[0];
    console.log(file);
  }

  submitSong(form) {
    if (!form.valid) {
      return;
    }

    if (this.currentSong.id === 0) {
      this.albumSongs.push(this.albumSongs);
      this.albumSongs[this.albumSongs.length-1].push(this.currentSongFile);
    }

    this.currentSong = new Song();
    this.currentSongFile = null;
  }

  editSong(song: Song) {
    this.currentSong = song;
  }

  deleteSong(song: Song) {
    var index = this.albumSongs.indexOf(Object.prototype.valueOf.call(song), 0);
    if(index > -1) {
      this.albumSongs.splice(index, 1);
    }
  }
}
