import { Component, OnInit } from '@angular/core';
import {switchMap} from "rxjs/operators";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {Album} from "../../../model/album";
import {Playlist} from "../../../model/playlist";
import {User} from "../../../model/user";
import {SearchService} from "../../../service/search.service";

@Component({
  selector: 'app-search-genre',
  templateUrl: './search-genre.component.html',
  styleUrls: ['./search-genre.component.css']
})
export class SearchGenreComponent implements OnInit {

  albums: Album[] = [];
  users: User[] = [];

  constructor(
    private searchService: SearchService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const genreWord: string = params.get('word');
      this.loadAlbums(genreWord);
      this.loadUsers(genreWord);
    })).subscribe();
  }

  loadAlbums(genreWord: string) {
    this.searchService.getAlbumsByGenre(genreWord)
      .then(
        (albums: Album[]) => {
          this.albums = albums;
        }
      );
  }

  loadUsers(genreWord : string) {
    this.searchService.getUsersByGenre(genreWord)
      .then(
        (users: User[]) => {
          this.users = users;
        }
      )
  }

}
