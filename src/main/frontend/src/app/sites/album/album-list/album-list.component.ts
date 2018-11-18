import { Component, OnInit } from '@angular/core';
import {Album} from "../../../model/album";
import {AlbumService} from "../../../service/album.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-album-list',
  templateUrl: './album-list.component.html',
  styleUrls: ['./album-list.component.css']
})
export class AlbumListComponent implements OnInit {

  albums: Album[] = [];

  constructor(
    private albumService: AlbumService,
    private location: Location
  ) { }

  ngOnInit() {
    this.loadAlbums();
  }

  loadAlbums() {
    this.albumService.getAllAlbums()
      .then(
        (albums: Album[]) => {
          this.albums = albums;
        }
      );
  }

  goBack() {
    this.location.back();
  }
}
