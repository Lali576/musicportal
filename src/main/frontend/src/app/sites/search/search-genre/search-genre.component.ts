import { Component, OnInit } from '@angular/core';
import {switchMap} from "rxjs/operators";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {Album} from "../../../model/album";
import {User} from "../../../model/user";
import {SearchService} from "../../../service/search.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-search-genre',
  templateUrl: './search-genre.component.html',
  styleUrls: ['./search-genre.component.css']
})
export class SearchGenreComponent implements OnInit {

  genreWord: string = "";
  albums: Album[] = [];
  users: User[] = [];

  constructor(
    private searchService: SearchService,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      this.genreWord = params.get('word');
      this.loadAlbums();
      this.loadUsers();
    })).subscribe();
  }

  loadAlbums() {
    this.searchService.getAlbumsByGenre(this.genreWord)
      .then(
        (albums: Album[]) => {
          this.albums = albums;
        }
      );
  }

  loadUsers() {
    this.searchService.getUsersByGenre(this.genreWord)
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
