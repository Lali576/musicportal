import { Component, OnInit } from '@angular/core';
import {Album} from "../../../model/album";
import {AlbumService} from "../../../service/album.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-album-list',
  templateUrl: './album-list.component.html',
  styleUrls: ['./album-list.component.css']
})
export class AlbumListComponent implements OnInit {

  albums: Album[] = [];

  constructor(
    private albumService: AlbumService,
    private router: Router
  ) { }

  ngOnInit() {
    this.albumService.getAllAlbums()
      .then(
        (albums: Album[]) => {
          this.albums = albums;
        }
      )
  }

  delete(id: number) {
    this.albumService.deleteAlbum(id);
    this.router.navigate(["/album"]);
  }
}
