import { Component, OnInit } from '@angular/core';
import {Song} from "../../../model/song";
import {Album} from "../../../model/album";
import {AlbumTag} from "../../../model/tags/albumtag";
import {SongComment} from "../../../model/songcomment";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {SongService} from "../../../service/song.service";
import {TagService} from "../../../service/tag.service";
import {AuthService} from "../../../service/auth.service";
import {MessageService} from "primeng/api";
import {Location} from "@angular/common";
import {switchMap} from "rxjs/operators";
import {SongLike} from "../../../model/songlike";
import {AlbumService} from "../../../service/album.service";

@Component({
  selector: 'app-album-songs',
  templateUrl: './album-songs.component.html',
  styleUrls: ['./album-songs.component.css'],
  providers: [MessageService]
})
export class AlbumSongsComponent implements OnInit {

  album: Album = new Album();
  albumTags: AlbumTag[] = [];
  albumSongs: Song[] = [];
  songIndex: number = 0;
  currentAlbumSong: Song = new Song();
  currentAudio = new Audio();
  currentSongComments: SongComment[] = [];
  currentSongCounterNumber: number = 0;
  currentSongLikeNumber: number = 0;
  currentSongDislikeNumber: number = 0;
  songEditLyrics: string = "";
  tempCommentText: string = "";
  commentDisplay: boolean = false;
  lyricsEditDisplay: boolean = false;
  paused = true;
  elapsed = "0:00";
  total;
  current = 0;
  duration: number = 0;
  volumeNumber: number = 50;

  constructor(
    private route: ActivatedRoute,
    private albumService: AlbumService,
    private songService: SongService,
    private tagService: TagService,
    private authService: AuthService,
    private messageService: MessageService,
    private location: Location
  ) {
  }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const albumId = +params.get('ida');
      this.songIndex = +params.get('ids');
      await this.albumService.getAlbum(albumId);
      this.album = this.albumService.album;
      this.loadAlbumSongs(this.songIndex);
    })).subscribe();
  }

  loadAlbumSongs(songIndex: number) {
    this.songService.getSongsByAlbum(this.album)
      .then(
        (songs: Song[]) => {
          this.albumSongs = songs;
          this.loadCurrentAlbumSong(songIndex);
        }
      );
  }

  loadCurrentAlbumSong(songIndex: number) {
    this.currentAlbumSong = this.getCurrentSong(songIndex);
    this.currentAudio.src = "https://docs.google.com/uc?export=download&id=" + this.currentAlbumSong.audioFileGdaId;
    this.currentAudio.load();
    this.currentAudio.ontimeupdate = this.handleTimeUpdate.bind(this);
    this.total = this.formatTime(this.currentAlbumSong.duration);
    if(!(this.authService.isLoggedIn)) {
      this.current = Math.floor(this.currentAlbumSong.duration * 0.33);
      this.currentAudio.currentTime = this.current;
      this.duration = this.current + 14;
    } else {
      this.current = 0;
      this.duration = this.currentAudio.duration;
    }
    this.currentAudio.volume = this.volumeNumber/100;
    this.loadAlbumTags();
    this.loadSongCounterNumber();
    this.loadLikeAndDislikeNumbers();
    if(this.authService.isLoggedIn) {
      this.songEditLyrics = this.currentAlbumSong.lyrics;
      this.loadSongComments();
    }
  }

  getCurrentSong(songIndex: number) {
    return this.albumSongs[songIndex];
  }

  getPreviousSong() {
    if(this.songIndex > 0) {
      this.songIndex--;

      if(this.songIndex >= 0) {
        this.handlePause();
        this.loadCurrentAlbumSong(this.songIndex);
      }
    }
  }

  getNextSong() {
    if(this.songIndex < (this.albumSongs.length-1)) {
      this.songIndex++;

      if(this.songIndex <= (this.albumSongs.length-1)) {
        this.handlePause();
        this.loadCurrentAlbumSong(this.songIndex);
      }
    }
  }

  loadAlbumTags() {
    this.tagService.getTagsByAlbum(this.album)
      .then(
        (albumTags: AlbumTag[]) => {
          this.albumTags = albumTags;
        }
      );
  }

  loadSongCounterNumber() {
    this.songService.countSongCounters(this.currentAlbumSong.id, this.currentAlbumSong)
      .then(
        (songCounterNumber: number) => {
          this.currentSongCounterNumber = songCounterNumber;
        }
      )
  }

  loadLikeAndDislikeNumbers() {
    this.songService.countSongLikes(this.currentAlbumSong.id, this.currentAlbumSong)
      .then(
        (likeTypeNumbers: number[]) => {
          this.currentSongLikeNumber = likeTypeNumbers[0];
          this.currentSongDislikeNumber = likeTypeNumbers[1];
        }
      )
  }

  loadSongComments() {
    this.songService.getSongComments(this.currentAlbumSong.id, this.currentAlbumSong)
      .then(
        (songComments: SongComment[]) => {
          this.currentSongComments = songComments;
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
    this.currentAudio.volume = this.volumeNumber/100;
  }

  handleVolumeDown() {
    this.currentAudio.volume = 0.0;
    this.volumeNumber = 0;
  }

  handleVolumeUp() {
    this.currentAudio.volume = 1.0;
    this.volumeNumber = 100;
  }

  handlePlay() {
    if(this.paused) {
      this.currentAudio.play();
      this.paused = false;

      this.addSongCounter();
    }
  }

  handlePause() {
    if(!this.paused) {
      this.currentAudio.pause();
      this.paused = true;
    }
  }

  handleBackward() {
    let elapsed = this.currentAudio.currentTime;
    if(elapsed >= 5) {
      this.currentAudio.currentTime = elapsed - 5;
    }
  }

  handleFastBackward() {
    this.currentAudio.currentTime = 0;
  }

  handleForward() {
    let elapsed = this.currentAudio.currentTime;
    let duration = this.currentAudio.duration;
    if(duration - elapsed >= 5) {
      this.currentAudio.currentTime = elapsed + 5;
    }
  }

  handleFastForward() {
    this.currentAudio.currentTime = this.currentAudio.duration;
  }

  handleTimeUpdate(e) {
    if(this.authService.isLoggedIn) {
      const elapsed = this.currentAudio.currentTime;
      const duration = this.currentAudio.duration;
      this.current = elapsed/duration;
      this.elapsed = this.formatTime(elapsed);

      if(elapsed === this.currentAudio.duration) {
        this.paused = true;
      }
    } else {
      const elapsed = this.currentAudio.currentTime;
      const duration = this.duration;
      this.current = elapsed/duration;
      this.elapsed = this.formatTime(elapsed);

      if(Math.floor(elapsed) === this.duration + 1) {
        this.handlePause();
        if(!(this.authService.isLoggedIn)) {
          this.current = Math.floor(this.currentAlbumSong.duration * 0.33);
          this.currentAudio.currentTime = this.current;
        }
      }
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
    try {
      this.messageService.add({severity: 'info', summary: 'Dal szöveg feltöltés alatt', detail: ''});
      this.songService.updateLyrics(this.currentAlbumSong.id, this.currentAlbumSong, this.songEditLyrics).then(
        (song: Song) => {
          this.currentAlbumSong = song;
          this.songEditLyrics = this.currentAlbumSong.lyrics;
        }
      );
      this.messageService.add({severity: 'success', summary: 'Dal szöveg feltöltés alatt', detail: ''});
    } catch (e) {
      this.messageService.add({severity: 'error', summary: 'Dal szöveg feltöltés alatt', detail: ''});
    }
  }

  async addSongCounter() {
    await this.songService.addSongCounter(this.currentAlbumSong).then(
      (songCounterNumber: number) => {
        this.currentSongCounterNumber = songCounterNumber;
      }
    )
  }

  async addSongLike(songLikeType: string) {
    let songLike = new SongLike();
    songLike.type = (songLikeType === 'like') ? 'LIKE' : 'DISLIKE';

    try {
      this.messageService.add({severity: 'info', summary: songLike.type + ' feltöltés alatt', detail: ''});

      await this.songService.addSongLike(songLike, this.currentAlbumSong)
        .then(
          (songLikeNumber: number) => {
            if (songLikeType === 'like') {
              this.currentSongLikeNumber = songLikeNumber;
            } else if (songLikeType === 'dislike') {
              this.currentSongDislikeNumber = songLikeNumber;
            }
          }
        )
      this.messageService.add({severity: 'success', summary: songLike.type + ' feltöltése sikeres', detail: ''});
    } catch (e) {
      this.messageService.add({severity: 'error', summary: songLike.type + ' feltöltése sikertelen', detail: ''});
    }
  }

  async sendComment() {
    try {
      this.messageService.add({severity: 'info', summary: 'Komment feltöltés alatt', detail: ''});
      let songComment: SongComment = new SongComment();
      songComment.textMessage = this.tempCommentText;
      this.tempCommentText = "";
      await this.songService.addSongComment(songComment, this.currentAlbumSong).then(
        (songComments: SongComment[]) => {
          this.currentSongComments = songComments;
        }
      );
      this.messageService.add({severity: 'success', summary: 'Komment feltöltése sikeres', detail: ''});
    } catch (e) {
      this.messageService.add({severity: 'error', summary: 'Komment feltöltése sikertelen', detail: ''});
    }
  }

  goBack() {
    this.location.back();
  }

  printOrderNumber(song: Song) {
    let index = this.getSongIndex(song);
    return (index + 1);
  }

  getSongIndex(song: Song) {
    let index = this.albumSongs.indexOf(song);
    return index;
  }

}
