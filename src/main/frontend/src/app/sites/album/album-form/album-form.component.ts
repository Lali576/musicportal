import {Component, OnInit} from '@angular/core';
import {Album} from "../../../model/album";
import {Song} from "../../../model/song";
import {AlbumTag} from "../../../model/Tags/albumtag";
import {GenreService} from "../../../service/genre.service";
import {AlbumService} from "../../../service/album.service";
import {SongService} from "../../../service/song.service";
import {Genre} from "../../../model/genre";
import {Router} from "@angular/router";
import {Message, MessageService} from "primeng/api";

@Component({
  selector: 'app-album-form',
  templateUrl: './album-form.component.html',
  styleUrls: ['./album-form.component.css'],
  providers: [MessageService]
})
export class AlbumFormComponent implements OnInit {
  msgs: Message[] = [];
  album: Album = new Album();
  albumCoverFile: File = null;
  albumGenre: Genre[] = [];
  Tags: string[] = [];
  albumTags: AlbumTag[] = [];
  genres: Genre[] = [];
  selectedGenres: Genre[] = [];
  displayDialog: boolean;
  song: Song = new Song();
  songFile: File = null;
  selectedSong: Song;
  files: File[] = [];
  newSong: boolean;
  albumSongs: Song[] = [];
  albumSongsFiles: File[] = [];
  cols: any[];

  constructor(
    private albumService: AlbumService,
    private songService: SongService,
    private genreService: GenreService,
    private messageService: MessageService,
    private route: Router
  ) {
    this.genreService.getGenres().subscribe(
      (genres: Genre[]) => {
        this.genres = genres;
      }
    )
  }

  ngOnInit() {
    this.cols = [
      { field: 'title', header: 'Cím' },
    ];
  }

  showDialogToAdd() {
    this.newSong = true;
    this.song = new Song();
    this.songFile = null;
    this.files = [];
    this.displayDialog = true;
  }

  saveSong() {
    let songs = [...this.albumSongs];
    let files = [...this.albumSongsFiles];
    if(this.newSong) {
      songs.push(this.song);
      files.push(this.songFile);
    } else {
      let index = this.albumSongs.indexOf(this.selectedSong);
      songs[index] = this.song;
      files[index] = this.songFile;
    }
    this.albumSongs = songs;
    this.albumSongsFiles = files;
    this.song = new Song();
    this.songFile = null;
    this.files = [];
    this.displayDialog = false;
  }

  deleteSong() {
    let index = this.albumSongs.indexOf(this.selectedSong);
    this.albumSongs = this.albumSongs.filter((val, i) => i != index);
    this.albumSongsFiles = this.albumSongsFiles.filter((val, i) => i != index);
    this.song = null;
    this.songFile = null;
    this.displayDialog = false;
  }

  onRowSelect(event) {
    this.newSong = false;
    this.song = this.cloneSong(event.data);
    let index = this.albumSongs.indexOf(this.song);
    this.songFile = this.albumSongsFiles[index];
    this.files.push(this.songFile);
    this.displayDialog = true;
  }

  cloneSong(s: Song): Song {
    let song = new Song();
    for(let prop in s) {
      song[prop] = s[prop];
    }

    return song;
  }

  onFileRemove() {
    this.songFile = null;
  }

  onAlbumCoverFileSelected(event) {
    this.albumCoverFile = <File>event.files[0];
    console.log(this.albumCoverFile);
  }

  onSongAudioFileSelected(event) {
    this.songFile = <File>event.files[0];
    console.log(this.songFile);
  }

  async submitAlbum(form) {
    if(!form.valid) {
      return;
    }
    try {
      if(this.albumSongs.length <= 3) {
        this.album.type = "SINGLE";
      } else if (this.albumSongs.length >= 4 && this.albumSongs.length <= 6) {
        this.album.type = "EP";
      } else if (this.albumSongs.length >= 7) {
        this.album.type = "LP";
      }
      for(let tag of this.Tags) {
        let albumTag: AlbumTag = new AlbumTag();
        albumTag.word = tag;
        this.albumTags.push(albumTag);
      }
      this.showMsgInfo();
      this.messageService.add({key: 'toast', severity:'info', summary: this.album.title + ' című album feltöltés alatt', detail:''});
      this.album = await this.albumService.addAlbum(this.album, this.albumCoverFile, this.albumGenre, this.albumTags);
      this.messageService.add({key: 'toast', severity:'success', summary: this.album.title + ' című album sikeresen feltöltve', detail:''});
      for(let i = 0; i < this.albumSongs.length; i++) {
        try {
          this.messageService.add({key: 'toast', severity:'info', summary: this.albumSongs[i].title + ' című dal feltöltés alatt', detail:''});
          console.log("Try to upload song titled " + this.albumSongs[i].title);
          await this.songService.addSong(this.albumSongs[i], this.albumSongsFiles[i], this.album, this.albumGenre);
          this.messageService.add({key: 'toast', severity:'success', summary: this.albumSongs[i].title + ' című dal sikeresen feltöltve', detail:''});
          console.log("Uploading song titled " + this.albumSongs[i].title + " was successful");
        } catch (e) {
          this.messageService.add({key: 'toast', severity:'error', summary: this.albumSongs[i].title + ' című dal feltöltése sikertelen', detail:''});
          console.log(e);
        }
      }
      this.showMsgSuccess();
      await new Promise( resolve => setTimeout(resolve, 1000) );
      this.route.navigate(['/album/list']);
    } catch (e) {
      this.showMsgError();
      this.messageService.add({key: 'toast', severity:'error', summary: this.album.title + ' című album feltöltése sikertelen', detail:''});
      console.log(e);
    }
  }

  showMsgInfo() {
    this.msgs = [];
    this.msgs.push({severity:'info', summary:'Album és dalok feltöltése folyamatban', detail:''});
  }

  showMsgSuccess() {
    this.msgs = [];
    this.msgs.push({severity:'success', summary:'Sikeres album és dalai feltöltés', detail:''});
  }

  showMsgError() {
    this.msgs = [];
    this.msgs.push({severity:'error', summary:'Album és dalok feltöltése sikertelen', detail:''});
  }
}
