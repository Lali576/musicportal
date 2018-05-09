import { Component, OnInit } from '@angular/core';
import {Album} from "../../../model/album";
import {AlbumService} from "../../../service/album.service";
import {AuthService} from "../../../auth.service";

@Component({
  selector: 'app-album-list',
  templateUrl: './album-list.component.html',
  styleUrls: ['./album-list.component.css']
})
export class AlbumListComponent implements OnInit {

  albums: Album[] = [];

  constructor(
    private albumService: AlbumService,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.albumService.getAlbums()
      .subscribe(albums => {
        this.albums = albums;
      });
  }
}
