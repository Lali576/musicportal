import { Component, OnInit } from '@angular/core';
import {Album} from "../../../model/album";
import {Song} from "../../../model/song";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {AlbumService} from "../../../service/album.service";
import {switchMap} from "rxjs/internal/operators";
import {SongService} from "../../../service/song.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {AuthService} from "../../../service/auth.service";
import {AlbumTag} from "../../../model/tags/albumtag";
import {TagService} from "../../../service/tag.service";
import {Location} from "@angular/common";
import {Genre} from "../../../model/genre";
import {GenreService} from "../../../service/genre.service";
import {UserTag} from "../../../model/tags/usertag";
import {delay} from "q";

@Component({
  selector: 'app-album-detail',
  templateUrl: './album-detail.component.html',
  styleUrls: ['./album-detail.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class AlbumDetailComponent implements OnInit {
  album: Album = new Album();
  albumGenres: Genre[] = [];
  albumTags: AlbumTag[] = [];
  albumSongs: Song[] = [];

  albumEditTitle: string = "";
  albumCoverFile: File = null;
  albumEditGenres: Genre[] = [];
  albumEditAlbumTags: string[] = [];

  coverEditDisplay: boolean = false;
  detailsEditDisplay: boolean = false;

  genres: Genre[] = [];

  constructor(
    private route: ActivatedRoute,
    private albumService: AlbumService,
    private songService: SongService,
    private genreService: GenreService,
    private tagService: TagService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    public authService: AuthService,
    private location: Location
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = +params.get('id');
      await this.albumService.getAlbum(id);
      this.album = this.albumService.album;
      this.loadAlbumGenres();
      this.loadAlbumTags();
      this.loadSongs();
    })).subscribe();
  }

  loadAlbumGenres() {
    this.genreService.getGenresByAlbum(this.album)
      .then(
        (genres: Genre[]) => {
          this.albumGenres = genres;
        }
      )
  }

  loadSongs() {
    this.songService.getSongsByAlbum(this.album)
      .then(
        (songs: Song[]) => {
          this.albumSongs = songs;
        }
      );
  }

  loadAlbumTags() {
    this.tagService.getTagsByAlbum(this.album)
      .then(
        (albumTags: AlbumTag[]) => {
          this.albumTags = albumTags;
          this.loadEditAlbumTags();
        }
      );
  }

  loadEditAlbumTags() {
    for(let userTag of this.albumTags) {
      let word: string = userTag.word;
      this.albumEditAlbumTags.push(word);
    }
  }

  onFileSelected(event) {
    this.albumCoverFile = <File>event.files[0];
    console.log(this.albumCoverFile);
  }

  onFileRemove() {
    this.albumCoverFile = null;
    console.log("You're know you right...");
  }

  async changeCoverFile() {
    await this.albumService.updateAlbumCover(this.album.id, this.album, this.albumCoverFile);
    this.album.coverFileGdaId = this.albumService.album.coverFileGdaId;
  }

  async changeDetails() {
    let albumTags: AlbumTag[] = [];

    for(let tag of this.albumEditAlbumTags) {
      let albumTag: AlbumTag = new AlbumTag();
      albumTag.word = tag;
      albumTags.push(albumTag);
    }

    console.log(this.albumEditGenres);

    await this.albumService.updateAlbumDetails(this.album.id, this.album, this.albumEditGenres, albumTags);
    this.album = this.albumService.album;
    this.loadAlbumGenres();
    this.loadAlbumTags();
  }

  deleteAlbumConfirm() {
    this.confirmationService.confirm({
      message: "Biztos szeretné törölni az alábbi albumot: " + this.album.title + " ?",
      header: 'Album törlés',
      icon: 'fas fa-exclamation-triangle',
      accept: () => {
        this.deleteAlbum(this.album);
      },
      reject: () => {
      }
    });
  }

  async deleteAlbum(album) {
    await this.albumService.deleteAlbum(album);
    this.messageService.add({severity:'success', summary: album.title + ' című album sikeresen törölve', detail:''});
    await delay(1000);
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

  showCoverDialog() {
    this.coverEditDisplay = true;
  }

  showDetailsDialog() {
    this.loadGenres();
    this.albumEditTitle = this.album.title;
    this.detailsEditDisplay = true;
  }

  loadGenres() {
    this.genreService.getGenres()
      .subscribe(
        (genres: Genre[]) => {
          this.genres = genres;
        }
      )
  }

  goBack() {
    this.location.back();
  }
}
