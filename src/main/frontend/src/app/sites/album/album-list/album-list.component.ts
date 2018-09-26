import { Component, OnInit } from '@angular/core';
import {Album} from "../../../model/album";
import {AlbumService} from "../../../service/album.service";

@Component({
  selector: 'app-album-list',
  templateUrl: './album-list.component.html',
  styleUrls: ['./album-list.component.css']
})
export class AlbumListComponent implements OnInit {

  albums: Album[] = [];

  constructor(
    private albumService: AlbumService,
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
}
