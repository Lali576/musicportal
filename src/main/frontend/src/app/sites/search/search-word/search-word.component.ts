import { Component, OnInit } from '@angular/core';
import {Album} from "../../../model/album";
import {Playlist} from "../../../model/playlist";
import {SearchService} from "../../../service/search.service";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {Location} from "@angular/common";
import {switchMap} from "rxjs/operators";
import {Song} from "../../../model/song";

@Component({
  selector: 'app-search-word',
  templateUrl: './search-word.component.html',
  styleUrls: ['./search-word.component.css']
})
export class SearchWordComponent implements OnInit {

  searchWord: string = "";
  albums: Album[] = [];
  playlists: Playlist[] = [];
  songs: Song[] = [];

  constructor(
    private searchService: SearchService,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      this.searchWord = params.get('word');
      this.loadAlbums();
      this.loadPlaylist();
      this.loadSongs();
    })).subscribe();
  }

  loadAlbums() {
    this.searchService.getAlbumsByTitle(this.searchWord)
      .then(
        (albums: Album[]) => {
          this.albums = albums;
        }
      );
  }

  loadPlaylist() {
    this.searchService.getPlaylistByName(this.searchWord)
      .then(
        (playlists: Playlist[]) => {
          this.playlists = playlists;
        }
      );
  }

  loadSongs() {
    this.searchService.getSongsByTitle(this.searchWord)
      .then(
        (songs: Song[]) => {
          this.songs = songs;
        }
      )
  }

  goBack() {
    this.location.back();
  }

}
