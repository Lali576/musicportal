import { Component, OnInit } from '@angular/core';
import {SearchService} from "../../../service/search.service";
import {Album} from "../../../model/album";
import {Playlist} from "../../../model/playlist";
import {User} from "../../../model/user";
import {switchMap} from "rxjs/operators";
import {ActivatedRoute, ParamMap, Route} from "@angular/router";
import {Location} from "@angular/common";

@Component({
  selector: 'app-search-tag',
  templateUrl: './search-tag.component.html',
  styleUrls: ['./search-tag.component.css']
})
export class SearchTagComponent implements OnInit {

  tagWord: string = "";
  albums: Album[] = [];
  playlists: Playlist[] = [];
  users: User[] = [];

  constructor(
    private searchService: SearchService,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      this.tagWord = params.get('word');
      this.loadAlbums();
      this.loadPlaylist();
      this.loadUsers();
    })).subscribe();
  }

  loadAlbums() {
    this.searchService.getAlbumsByAlbumTag(this.tagWord)
      .then(
      (albums: Album[]) => {
        this.albums = albums;
      }
    );
  }

  loadPlaylist() {
    this.searchService.getPlaylistByPlaylistTag(this.tagWord)
      .then(
        (playlists: Playlist[]) => {
          this.playlists = playlists;
        }
      );
  }

  loadUsers() {
    this.searchService.getUsersByUserTag(this.tagWord)
      .then(
        (users: User[]) => {
          this.users = users;
        }
      )
  }

  goBack() {
    this.location.back();
  }
}
