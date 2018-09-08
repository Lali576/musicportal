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
import {Message} from "primeng/api";

@Component({
  selector: 'app-album-form',
  templateUrl: './album-form.component.html',
  styleUrls: ['./album-form.component.css']
})
export class AlbumFormComponent implements OnInit {
  msgs: Message[] = [];
  album: Album = new Album();
  albumCoverFile: File = null;
  albumGenre: Genre[] = [];
  albumKeywords: AlbumKeyword[] = [];
  genres: Genre[] = [];
  displayDialog: boolean;
  song: Song = new Song();
  songFile: File = null;
  selectedSong: Song;
  files: File[] = [];
  newSong: boolean;
  albumSongs: Song[] = [];
  albumSongsFiles: File[] = [];
  cols: any[];
  hu: any;

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
    this.hu = {
      firstDayOfWeek: 1,
      dayNames: [ "hétfő","kedd","szerda","csütörtök","péntek","szombat","vasárnap" ],
      dayNamesShort: [ "hét","ke","szer","csüt","pén","szo","vas" ],
      dayNamesMin: [ "H","K","SZ","CS","P","SZ","V" ],
      monthNames: [ "január","február","március","április","május","június","július","augusztus","szeptember","október","november","december" ],
      monthNamesShort: [ "jan","feb","már","ápr","máj","jún","júl","aug","szep","okt","nov","dec" ],
      today: 'Ma',
      clear: 'Törlés'
    }

    this.cols = [
      { field: 'title', header: 'Cím' },
    ];
  }

  showDialogToAdd() {
    this.newSong = true;
    this.song = new Song();
    this.songFile = null;
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
    this.song = null;
    this.songFile = null;
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
    this.files.push(this.albumSongsFiles[index]);
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
      console.log(this.album);
      this.showMsgInfo();
      this.album = await this.albumService.addAlbum(this.album, this.albumCoverFile, this.albumGenre, this.albumKeywords);
      this.showMsgSuccess();
      for(var i = 0; i < this.albumSongs.length; i++) {
        console.log("Try to upload song titled " + this.albumSongs[i].title);
        await this.songService.addSong(this.albumSongs[i], this.albumSongsFiles[i], this.album, this.albumGenre,  this.albumKeywords);
        console.log("Uploading song titled " + this.albumSongs[i].title + " was successful");
      }
      this.route.navigate(['/album/list']);
    } catch (e) {
      this.showMsgError();
      console.log(e);
    }
  }

  showMsgInfo() {
    this.msgs = [];
    this.msgs.push({severity:'info', summary:'Album feltöltés folyamatban', detail:''});
  }

  showMsgSuccess() {
    this.msgs = [];
    this.msgs.push({severity:'success', summary:'Sikeres album feltöltés', detail:''});
  }

  showMsgError() {
    this.msgs = [];
    this.msgs.push({severity:'error', summary:'Album feltöltés sikertelen', detail:''});
  }
}
