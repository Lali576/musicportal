import { Component, OnInit } from '@angular/core';
import {Album} from "../../../model/album";
import {Song} from "../../../model/song";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {AlbumService} from "../../../service/album.service";
import {switchMap} from "rxjs/internal/operators";
import {Observable, of} from "rxjs/index";
import {Location} from "@angular/common";

@Component({
  selector: 'app-album-edit',
  templateUrl: './album-edit.component.html',
  styleUrls: ['./album-edit.component.css']
})
export class AlbumEditComponent implements OnInit {

  album: Album;
  //songs: Song[];

  constructor(
    private route: ActivatedRoute,
    private albumService: AlbumService,
    private location: Location
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = params.get('id');
      this.album = id !== null
        ? await this.albumService.getAlbum(+id)
        : new Album();
      return of (Observable);
    })).subscribe();
  }

  async onFormSubmit(album: Album) {
    console.log(album);
    if(album.id > 0) {
      await this.albumService.updateAlbum(album.id, album);
    } else {
      var albumString = JSON.stringify(album);
      console.log(albumString);
      await this.albumService.addAlbum(albumString, null, null);
    }
    this.location.back();
  }

}
