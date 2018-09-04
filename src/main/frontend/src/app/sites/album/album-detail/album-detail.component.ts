import { Component, OnInit } from '@angular/core';
import {Album} from "../../../model/album";
import {Song} from "../../../model/song";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {AlbumService} from "../../../service/album.service";
import {switchMap} from "rxjs/internal/operators";
import {SongService} from "../../../service/song.service";

@Component({
  selector: 'app-album-detail',
  templateUrl: './album-detail.component.html',
  styleUrls: ['./album-detail.component.css']
})
export class AlbumDetailComponent implements OnInit {
  album: Album = new Album();
  albumSongs: Song[] = [];

  constructor(
    private route: ActivatedRoute,
    private albumService: AlbumService,
    private songService: SongService
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = +params.get('id');
      await this.albumService.getAlbum(id);
      this.album = this.albumService.album;
      this.albumSongs = await this.songService.getSongsByAlbum(this.album);
    })).subscribe();
  }

}
