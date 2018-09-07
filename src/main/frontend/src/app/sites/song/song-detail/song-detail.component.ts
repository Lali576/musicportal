import {Component, EventEmitter, OnInit} from '@angular/core';
import {Song} from "../../../model/song";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {SongService} from "../../../service/song.service";
import {switchMap} from "rxjs/internal/operators";

@Component({
  selector: 'app-song-detail',
  templateUrl: './song-detail.component.html',
  styleUrls: ['./song-detail.component.css']
})
export class SongDetailComponent implements OnInit {

  song: Song = new Song();
  audio = new Audio();
  paused = true;
  elapsed;
  total;
  current;

  constructor(
    private route: ActivatedRoute,
    private songService: SongService
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
    })).subscribe();
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

  handleForward() {
    let elapsed = this.audio.currentTime;
    let duration = this.audio.duration;
    if(duration - elapsed >= 5) {
      this.audio.currentTime = elapsed + 5;
    }
  }

  handleTimeUpdate(e) {
    const elapsed = this.audio.currentTime;
    const duration = this.audio.duration;
    this.current = elapsed/duration;
    this.elapsed = this.formatTime(elapsed);
    this.total = this.formatTime(duration);
  }


  formatTime(seconds) {
    if(isNaN(seconds)) {
      return;
    }
    let minutes:any = Math.floor(seconds/60);
    minutes = (minutes >= 10) ? minutes : "0" + minutes;
    seconds = Math.floor(seconds%60);
    seconds = (seconds >= 10) ? seconds : "0" + seconds;
    return minutes + ":" + seconds;
  }
}
