import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {Song} from "../../../model/song";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {SongService} from "../../../service/song.service";
import {switchMap} from "rxjs/internal/operators";
import {Album} from "../../../model/album";
import {AlbumTag} from "../../../model/tags/albumtag";
import {TagService} from "../../../service/tag.service";
import {SongComment} from "../../../model/songcomment";
import {AuthService} from "../../../service/auth.service";
import {SongLike} from "../../../model/songlike";

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
  songComments: SongComment[] = [];
  songCounterNumber: number = 0;
  songLikeNumber: number = 0;
  songDislikeNumber: number = 0;
  songEditLyrics: string = "";
  tempCommentText: string = "";
  commentDisplay: boolean = false;
  lyricsEditDisplay: boolean = false;
  paused = true;
  elapsed = "0:00";
  total;
  current = 0;
  volumeNumber: number = 50;

  constructor(
    private route: ActivatedRoute,
    private songService: SongService,
    private tagService: TagService,
    private authService: AuthService,
    private location: Location
  ) {
  }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = +params.get('id');
      await this.songService.getSong(id);
      this.song = this.songService.song;
      this.songEditLyrics = this.song.lyrics;
      this.audio.src = "https://docs.google.com/uc?export=download&id=" + this.song.audioFileGdaId;
      this.audio.load();
      this.audio.ontimeupdate = this.handleTimeUpdate.bind(this);
      this.total = this.formatTime(this.song.duration);
      this.audio.volume = this.volumeNumber/100;
      this.loadAlbumTags();
      this.loadSongCounterNumber();
      this.loadLikeAndDislikeNumbers();
      this.loadSongComments();
    })).subscribe();
  }

  loadAlbumTags() {
    this.tagService.getTagsByAlbum(this.song.album)
      .then(
        (albumTags: AlbumTag[]) => {
          this.albumTags = albumTags;
        }
      );
  }

  loadSongCounterNumber() {
    this.songService.countSongCounters(this.song.id, this.song)
      .then(
        (songCounterNumber: number) => {
          this.songCounterNumber = songCounterNumber;
        }
      )
  }

  loadLikeAndDislikeNumbers() {
    this.songService.countSongLikes(this.song.id, this.song)
      .then(
        (likeTypeNumbers: number[]) => {
          this.songLikeNumber = likeTypeNumbers[0];
          this.songDislikeNumber = likeTypeNumbers[1];
        }
      )
  }

  loadSongComments() {
    this.songService.getSongComments(this.song.id, this.song)
      .then(
        (songComments: SongComment[]) => {
          this.songComments = songComments;
        }
      );
  }

  showCommentDialog() {
    this.commentDisplay = true;
  }

  showLyricsDialog() {
    this.lyricsEditDisplay = true;
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

      this.addSongCounter();
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

  async changeLyrics() {
    this.songService.updateLyrics(this.song.id, this.song, this.songEditLyrics).then(
      (song: Song) => {
        this.song = song;
        this.songEditLyrics = this.song.lyrics;
      }
    );
  }

  async addSongCounter() {
    await this.songService.addSongCounter(this.song).then(
      (songCounterNumber: number) => {
        this.songCounterNumber = songCounterNumber;
      }
    )
  }

  async addSongLike(songLikeType: string) {
    let songLike = new SongLike();
    songLike.type = (songLikeType === 'like') ? 'LIKE' : 'DISLIKE';

    await this.songService.addSongLike(songLike, this.song)
      .then(
        (songLikeNumber: number) => {
          if(songLikeType === 'like') {
            this.songLikeNumber = songLikeNumber;
          } else if (songLikeType === 'dislike') {
            this.songDislikeNumber = songLikeNumber;
          }
        }
      )
  }

  async sendComment() {
    let songComment: SongComment = new SongComment();
    songComment.textMessage = this.tempCommentText;
    this.tempCommentText = "";
    await this.songService.addSongComment(songComment, this.song).then(
      (songComments: SongComment[]) => {
        this.songComments = songComments;
      }
    );
  }

  goBack() {
    this.location.back();
  }
}
