import {Component, OnInit} from '@angular/core';
import {Playlist} from "../../../model/playlist";
import {Song} from "../../../model/song";
import {SongService} from "../../../service/song.service";
import {Message, MessageService} from "primeng/api";
import {PlaylistService} from "../../../service/playlist.service";
import {Router} from "@angular/router";
import {PlaylistTag} from "../../../model/Tags/playlisttag";

@Component({
  selector: 'app-playlist-form',
  templateUrl: './playlist-form.component.html',
  styleUrls: ['./playlist-form.component.css'],
  providers: [MessageService]
})
export class PlaylistFormComponent implements OnInit {
  playlist: Playlist = new Playlist();
  songs: Song[] = [];
  selectedSongs: Song[] = [];
  Tags: string[] = [];
  playlistTags: PlaylistTag[] = [];
  msgs: Message[] = [];

  constructor(
    private playlistServive: PlaylistService,
    private songService: SongService,
    private router: Router
  ) { }

  ngOnInit() {
    this.songService.getAllSongs()
      .then((songs: Song[]) => {
        this.songs = songs;
      });
  }

  async submitPlaylist(form) {
    if(!form.valid) {
      return;
    }
    try {
      console.log(this.playlist);
      this.showMsgInfo();
      this.playlist = await this.playlistServive.addPlaylist(this.playlist, this.selectedSongs, this.playlistTags);
      this.showMsgSuccess();
      await  new Promise( resolve => setTimeout(resolve, 1000) );
      this.router.navigate(['/playlist/list']);
    } catch (e) {
      this.showMsgError();
      console.log(e);
    }
  }

  showMsgInfo() {
    this.msgs = [];
    this.msgs.push({severity:'info', summary:'Lejátszási lista feltöltése folyamatban', detail:''});
  }

  showMsgSuccess() {
    this.msgs = [];
    this.msgs.push({severity:'success', summary:'Sikeres Lejátszási lista feltöltés', detail:''});
  }

  showMsgError() {
    this.msgs = [];
    this.msgs.push({severity:'error', summary:'Lejátszási lista feltöltése sikertelen', detail:''});
  }
}
