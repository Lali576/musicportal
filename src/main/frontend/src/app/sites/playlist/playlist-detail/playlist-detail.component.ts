import { Component, OnInit } from '@angular/core';
import {Song} from "../../../model/song";
import {Playlist} from "../../../model/playlist";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {PlaylistService} from "../../../service/playlist.service";
import {SongService} from "../../../service/song.service";
import {switchMap} from "rxjs/internal/operators";
import {ConfirmationService, MessageService} from "primeng/api";
import {AuthService} from "../../../service/auth.service";
import {PlaylistTag} from "../../../model/tags/playlisttag";
import {TagService} from "../../../service/tag.service";

@Component({
  selector: 'app-playlist-detail',
  templateUrl: './playlist-detail.component.html',
  styleUrls: ['./playlist-detail.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class PlaylistDetailComponent implements OnInit {

  playlist: Playlist = new Playlist();
  playlistTags: PlaylistTag[] = [];
  songs: Song[] = [];

  constructor(
    private route: ActivatedRoute,
    private playlistService: PlaylistService,
    private songService: SongService,
    private tagService: TagService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    public authService: AuthService
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = +params.get('id');
      await this.playlistService.getPlaylist(id);
      this.playlist = this.playlistService.playlist;
      this.loadSongs();
      this.loadPlaylistTags;
    })).subscribe();
  }

  loadPlaylistTags() {
    this.tagService.getTagsByPlaylist(this.playlist)
      .then(
        (playlistTags: PlaylistTag[]) => {
          this.playlistTags = playlistTags;
        }
      )
  }

  loadSongs() {
    this.songService.getSongsByPlaylist(this.playlist)
      .then(
        (songs: Song[]) => {
          this.songs = songs;
        }
      );
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
}
