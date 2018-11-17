import { Component, OnInit } from '@angular/core';
import {Song} from "../../../model/song";
import {Playlist} from "../../../model/playlist";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {PlaylistService} from "../../../service/playlist.service";
import {SongService} from "../../../service/song.service";
import {switchMap} from "rxjs/internal/operators";
import {ConfirmationService, MessageService} from "primeng/api";
import {AuthService} from "../../../service/auth.service";
import {PlaylistTag} from "../../../model/tags/playlisttag";
import {TagService} from "../../../service/tag.service";
import {Location} from "@angular/common";
import {delay} from "q";
import {Genre} from "../../../model/genre";

@Component({
  selector: 'app-playlist-detail',
  templateUrl: './playlist-detail.component.html',
  styleUrls: ['./playlist-detail.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class PlaylistDetailComponent implements OnInit {

  playlist: Playlist = new Playlist();
  playlistTags: PlaylistTag[] = [];
  playlistSongs: Song[] = [];

  songs: Song[] = [];
  selectedSongs: Song[] = [];

  detailsEditDisplay: boolean = false;
  playlistEditName: string = "";
  playlistEditPlaylistTags: string[] = [];

  constructor(
    public authService: AuthService,
    private playlistService: PlaylistService,
    private songService: SongService,
    private tagService: TagService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private route: ActivatedRoute,
    private router: Router,
    private location: Location
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = +params.get('id');
      await this.playlistService.getPlaylist(id);
      this.playlist = this.playlistService.playlist;
      this.loadPlaylistSongs();
      this.loadPlaylistTags();
    })).subscribe();
  }

  loadPlaylistTags() {
    this.tagService.getTagsByPlaylist(this.playlist)
      .then(
        (playlistTags: PlaylistTag[]) => {
          this.playlistTags = playlistTags;
          this.loadEditPlaylistTags();
        }
      )
  }

  loadEditPlaylistTags() {
    this.playlistEditPlaylistTags = [];

    for(let playlisTag of this.playlistTags) {
      let word: string = playlisTag.word;
      this.playlistEditPlaylistTags.push(word);
    }
  }

  loadPlaylistSongs() {
    this.songService.getSongsByPlaylist(this.playlist)
      .then(
        (playlistSongs: Song[]) => {
          this.playlistSongs = playlistSongs;
        }
      );
  }

  async changeDetails() {
    let playlistTags: PlaylistTag[] = [];

    for(let tag of this.playlistEditPlaylistTags) {
      let playlistTag: PlaylistTag = new PlaylistTag();
      playlistTag.word = tag;
      playlistTags.push(playlistTag);
    }

    try {
      this.messageService.add({severity: 'info', summary: 'Adatok feltöltés alatt', detail: ''});
      await this.playlistService.updatePlaylist(this.playlist.id, this.playlist, this.playlistEditName, this.selectedSongs, playlistTags);
      this.playlist = this.playlistService.playlist;
      this.loadPlaylistSongs();
      this.loadPlaylistTags();
      this.messageService.add({severity: 'success', summary: 'Adatok feltöltése sikeres', detail: ''});
    } catch (e) {
      this.messageService.add({severity: 'error', summary: 'Adatok feltöltése sikertelen', detail: ''});
    }
  }

  deletePlaylistConfirm() {
    this.confirmationService.confirm({
      message: "Biztos szeretné törölni az alábbi lejátszási listát: " + this.playlist.name + " ?",
      header: 'Lej. lista törlés',
      icon: 'fas fa-exclamation-triangle',
      accept: () => {
        this.deletePlaylist(this.playlist);
      },
      reject: () => {
      }
    });
  }

  async deletePlaylist(playlist) {
    try {
      this.messageService.add({severity: 'info', summary: playlist.name + ' nevű lej. lista törlés alatt', detail: ''});
      await this.playlistService.deletePlaylist(playlist);
      this.messageService.add({severity: 'success', summary: playlist.name + ' nevű lej. lista törlése sikeres', detail: ''});
      await delay(1000);
      this.router.navigate(['/user', playlist.user.id]);
    } catch (e) {
      this.messageService.add({severity: 'error', summary: playlist.name + ' nevű lej. lista törlése sikertelen', detail: ''});
    }
  }

  showDetailsDialog() {
    this.playlistEditName = this.playlist.name;
    this.loadSongs();
    this.detailsEditDisplay = true;
  }

  loadSongs() {
    this.songService.getAllSongs()
      .then(
        (songs: Song[]) => {
          this.songs = songs;
        }
      )
  }

  goBack() {
    this.location.back();
  }

  getSongIndex(song: Song) {
    let index = this.playlistSongs.indexOf(song);
    return index;
  }
}
