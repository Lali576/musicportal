import { Component, OnInit } from '@angular/core';
import {Album} from "../../../model/album";
import {Song} from "../../../model/song";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {AlbumService} from "../../../service/album.service";
import {switchMap} from "rxjs/internal/operators";
import {Observable, of} from "rxjs/index";
import {Location} from "@angular/common";
import {SongService} from "../../../service/song.service";

@Component({
  selector: 'app-album-edit',
  templateUrl: './album-edit.component.html',
  styleUrls: ['./album-edit.component.css']
})
export class AlbumEditComponent implements OnInit {

  album: Album;
  songs: Song[];

  constructor(
    private route: ActivatedRoute,
    private albumService: AlbumService,
    private songService: SongService,
    private location: Location
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = params.get('id');
      if(id !== null) {
        this.album = await this.albumService.getAlbum(+id);
        this.songs = await this.songService.getSongsByAlbum(this.album);
      } else {
        this.album = new Album();
      }

      return of (Observable);
    })).subscribe();
  }

  async onFormSubmit(params: object) {
    var album: Album = params["album"];
    var songs: Song[] = params["songs"];

    if(album.id > 0) {
      await this.albumService.updateAlbum(album.id, album);

      for(var i = 0; i < songs.length; i++) {
        var song: Song = songs[i];
        var songString = JSON.stringify(song);
        var albumString = JSON.stringify(album);
        await this.songService.updateSong(song.id, songString, albumString);
      }

    } else {
      var albumString = JSON.stringify(album);
      var savedAlbum: Album = await this.albumService.addAlbum(albumString, null, null);
      albumString = JSON.stringify(savedAlbum);

      for(var i = 0; i < songs.length; i++) {
        var songString = JSON.stringify(songs[i]);
        await this.songService.addSong(songString, albumString, null, null);
      }
    }
    this.location.back();
  }

}
