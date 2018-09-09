import { Component, OnInit } from '@angular/core';
import {Album} from "../../../model/album";
import {Song} from "../../../model/song";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {AlbumService} from "../../../service/album.service";
import {switchMap} from "rxjs/internal/operators";
import {SongService} from "../../../service/song.service";
import {ConfirmationService, MessageService} from "primeng/api";

@Component({
  selector: 'app-album-detail',
  templateUrl: './album-detail.component.html',
  styleUrls: ['./album-detail.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class AlbumDetailComponent implements OnInit {
  album: Album = new Album();
  albumSongs: Song[] = [];

  constructor(
    private route: ActivatedRoute,
    private albumService: AlbumService,
    private songService: SongService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = +params.get('id');
      await this.albumService.getAlbum(id);
      this.album = this.albumService.album;
      this.loadSongs();
    })).subscribe();
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
    this.messageService.add({severity:'success', summary: song.title + ' című dal sikeresen törölve', detail:''});
    this.loadSongs();
  }

  loadSongs() {
    this.songService.getSongsByAlbum(this.album)
      .then(
        (songs: Song[]) => {
          this.albumSongs = songs;
        }
      );
  }
}
