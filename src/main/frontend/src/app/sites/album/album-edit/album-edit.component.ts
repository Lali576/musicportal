import { Component, OnInit } from '@angular/core';
import {Album} from "../../../model/album";
import {Song} from "../../../model/song";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {AlbumService} from "../../../service/album.service";
import {switchMap} from "rxjs/internal/operators";
import {Observable, of} from "rxjs/index";
import {Location} from "@angular/common";
import {SongService} from "../../../service/song.service";
import {HttpClient} from "@angular/common/http";

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
    private location: Location,
    private http: HttpClient
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = params.get('id');
      if(id !== null) {
        this.album = await this.albumService.getAlbum(+id);
        this.songs = await this.songService.getSongsByAlbum(this.album);
      } else {
        this.album = new Album();
        this.songs = [];
      }

      return of (Observable);
    })).subscribe();
  }

  async onFormSubmit(params: object) {
    var album: Album = params["album"];
    var songs: Song[] = params["songs"];
    if (album.id > 0) {
      await this.albumService.updateAlbumDetails(album.id, album, null, null);

      for (var i = 0; i < songs.length; i++) {
        var song: Song = songs[i];
        await this.songService.updateSong(song.id, song, null, album);
      }
    } else {
      var savedAlbum: Album = await this.albumService.addAlbum(album, null, null, null);

      for (var i = 0; i < songs.length; i++) {
        var song: Song = songs[i];
        const uploadSongAudio = new FormData();
        //uploadSongAudio.append(savedAlbum.name+"\\"+song.title, song.audioFile, song.audioFile.name);
        await this.http.post('/api/song/file', uploadSongAudio)
          .subscribe(async (res) => {
            await this.songService.addSong(song, null, album, null, null);
          });
      }
    }
  }
}
