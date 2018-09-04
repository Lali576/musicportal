import { Component, OnInit } from '@angular/core';
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

  constructor(
    private route: ActivatedRoute,
    private songService: SongService
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = +params.get('id');
      await this.songService.getSong(id);
      this.song = this.songService.song;
      console.log(this.song.audioFileGdaId);
    })).subscribe();
  }

}
