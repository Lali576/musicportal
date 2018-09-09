import { Component, OnInit } from '@angular/core';
import {SongService} from "../../../service/song.service";
import {Song} from "../../../model/song";
import {ConfirmationService, MessageService} from "primeng/api";

@Component({
  selector: 'app-song-list',
  templateUrl: './song-list.component.html',
  styleUrls: ['./song-list.component.css'],
  providers: [ConfirmationService, MessageService]
})
export class SongListComponent implements OnInit {

  songs: Song[] = [];

  constructor(
    private songService: SongService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) { }

  ngOnInit() {
    this.loadSongs()
  }

  loadSongs() {
    this.songService.getSongsByUser()
      .then(
        (songs: Song[]) => {
          this.songs = songs;
        }
      )
  }

  deleteSongConfirm(song: Song) {
    this.confirmationService.confirm({
      message: "Biztos szeretné törölni az alábbi dalt: " + song.title + " ?",
      header: 'Dal törlés',
      icon: 'fas fa-exclamation-triangle',
      accept: () => {
        this.deleteSong(song);
      },
      reject: () => {
      }
    });
  }

  async deleteSong(song) {
    await this.songService.deleteSong(song);
    this.messageService.add({severity:'success', summary: song.title + ' című album sikeresen törölve', detail:''});
    this.loadSongs();
  }
}
