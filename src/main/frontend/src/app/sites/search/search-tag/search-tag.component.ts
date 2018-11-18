import { Component, OnInit } from '@angular/core';
import {SearchService} from "../../../service/search.service";
import {Album} from "../../../model/album";
import {Playlist} from "../../../model/playlist";
import {User} from "../../../model/user";
import {switchMap} from "rxjs/operators";
import {ActivatedRoute, ParamMap, Route} from "@angular/router";

@Component({
  selector: 'app-search-tag',
  templateUrl: './search-tag.component.html',
  styleUrls: ['./search-tag.component.css']
})
export class SearchTagComponent implements OnInit {

  albums: Album[] = [];
  playlist: Playlist[] = [];
  users: User[] = [];

  constructor(
    private searchService: SearchService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const tagWord: string = params.get('word');
      this.loadAlbums(tagWord);
      this.loadPlaylist(tagWord);
      this.loadUsers(tagWord);
    })).subscribe();
  }

  loadAlbums(tagWord: string) {
    this.searchService.getAlbumsByAlbumTag(tagWord)
      .then(
      (albums: Album[]) => {
        this.albums = albums;
      }
    );
  }

  loadPlaylist(tagWord: string) {
    this.searchService.getPlaylistByPlaylistTag(tagWord)
      .then(
        (playlist: Playlist[]) => {
          this.playlist = playlist;
        }
      );
  }

  loadUsers(tagWord : string) {
    this.searchService.getUsersByUserTag(tagWord)
      .then(
        (users: User[]) => {
          this.users = users;
        }
      )
  }
}
