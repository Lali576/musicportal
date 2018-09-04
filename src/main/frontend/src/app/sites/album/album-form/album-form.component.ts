import {Component, OnInit} from '@angular/core';
import {Album} from "../../../model/album";
import {Song} from "../../../model/song";
import {AlbumKeyword} from "../../../model/keywords/albumkeyword";
import {GenreService} from "../../../service/genre.service";
import {AlbumService} from "../../../service/album.service";
import {SongService} from "../../../service/song.service";
import {Genre} from "../../../model/genre";
import {Router} from "@angular/router";
import {forEach} from "@angular/router/src/utils/collection";

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
  albumSongs: Song[] = [];
  albumSongsFiles: File[] = [];
  currentSong: Song = new Song();
  currentSongFile: File = null;
  message: string = "";
  showNewSong: boolean = false;

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
      this.album = await this.albumService.addAlbum(this.album, this.albumCoverFile, this.albumGenre, this.albumKeywords);
      this.message = "Album feltöltése sikeres";
      for(var i = 0; i < this.albumSongs.length; i++) {
        console.log("Try to upload song titled " + this.albumSongs[i].title);
        await this.songService.addSong(this.albumSongs[i], this.albumSongsFiles[i], this.album, this.albumGenre,  this.albumKeywords);
        console.log("Uploading song titled " + this.albumSongs[i].title + " was successful");
      }
      this.route.navigate(['/album/list']);
    } catch (e) {
      this.message = "Album feltöltése sikertelen";
      console.log(e);
    }
  }

  onSongAudioFileSelected(event) {
    this.currentSongFile = <File>event.target.files[0];
    console.log(this.currentSongFile);
  }

  submitSong(form) {
    if (!form.valid) {
      return;
    }

    if (this.currentSong.id === 0 && !(this.albumSongs.includes(this.currentSong))) {
      this.albumSongs.push(this.currentSong);
      this.albumSongsFiles.push(this.currentSongFile);
    }

    console.log("Song titled " + this.currentSong.title + " with file " + this.currentSongFile.name + " has been saved");
    this.currentSong = new Song();
    this.currentSongFile = null;
    this.showNewSong = false;
  }

  editSong(song: Song) {
    this.currentSong = song;
    var index = this.albumSongs.indexOf(song, 0)
    this.currentSongFile = this.albumSongsFiles[index];
    this.showNewSong = true;
  }

  deleteSong(song: Song) {
    var index = this.albumSongs.indexOf(song, 0);
    if(index > -1) {
      this.albumSongs.splice(index, 1);
      this.albumSongsFiles.splice(index, 1);
    }
  }

  toggle() {
    this.showNewSong = !this.showNewSong;
  }
}
