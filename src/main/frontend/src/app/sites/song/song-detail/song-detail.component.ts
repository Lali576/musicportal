import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {Song} from "../../../model/song";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {SongService} from "../../../service/song.service";
import {switchMap} from "rxjs/internal/operators";
import {AlbumService} from "../../../service/album.service";
import {Album} from "../../../model/album";
import {AlbumTag} from "../../../model/tags/albumtag";

@Component({
  selector: 'app-song-detail',
  templateUrl: './song-detail.component.html',
  styleUrls: ['./song-detail.component.css']
})
export class SongDetailComponent implements OnInit {

  song: Song = new Song();
  audio = new Audio();
  album: Album = new Album();
  albumTags: AlbumTag[] = [];
  paused = true;
  elapsed = "0:00";
  total;
  current = 0;
  volumeNumber: number = 50;

  constructor(
    private route: ActivatedRoute,
    private songService: SongService,
    private albumService: AlbumService,
    private location: Location
  ) {
  }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = +params.get('id');
      await this.songService.getSong(id);
      this.song = this.songService.song;
      this.audio.src = "https://docs.google.com/uc?export=download&id=" + this.song.audioFileGdaId;
      this.audio.load();
      this.audio.ontimeupdate = this.handleTimeUpdate.bind(this);
      this.total = this.formatTime(this.song.duration);
      this.audio.volume = this.volumeNumber/100;
    })).subscribe();

    this.album = this.albumService.album;
    this.loadAlbumTags();
  }

  loadAlbumTags() {
    this.albumService.getAlbumTags(this.album)
      .then(
        (albumTags: AlbumTag[]) => {
          this.albumTags = albumTags;
        }
      );
  }

  handleVolumeChange(e) {
    this.audio.volume = this.volumeNumber/100;
  }

  handleVolumeDown() {
    this.audio.volume = 0.0;
    this.volumeNumber = 0;
  }

  handleVolumeUp() {
    this.audio.volume = 1.0;
    this.volumeNumber = 100;
  }

  handlePlay() {
    if(this.paused) {
      this.audio.play();
      this.paused = false;
    }
  }

  handlePause() {
    if(!this.paused) {
      this.audio.pause();
      this.paused = true;
    }
  }

  handleBackward() {
    let elapsed = this.audio.currentTime;
    if(elapsed >= 5) {
      this.audio.currentTime = elapsed - 5;
    }
  }

  handleFastBackward() {
    this.audio.currentTime = 0;
  }

  handleForward() {
    let elapsed = this.audio.currentTime;
    let duration = this.audio.duration;
    if(duration - elapsed >= 5) {
      this.audio.currentTime = elapsed + 5;
    }
  }

  handleFastForward() {
    this.audio.currentTime = this.audio.duration;
  }

  handleTimeUpdate(e) {
    const elapsed = this.audio.currentTime;
    const duration = this.audio.duration;
    this.current = elapsed/duration;
    this.elapsed = this.formatTime(elapsed);

    if(elapsed === this.audio.duration) {
      this.paused = true;
    }
  }

  formatTime(seconds) {
    let minutes:any = Math.floor(seconds/60);
    minutes = (minutes >= 10) ? minutes : "0" + minutes;
    seconds = Math.floor(seconds%60);
    seconds = (seconds >= 10) ? seconds : "0" + seconds;
    return minutes + ":" + seconds;
  }

  goBack() {
    this.location.back();
  }
}
