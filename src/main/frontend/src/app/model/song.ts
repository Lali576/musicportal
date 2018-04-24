import {Songcomment} from "./songcomment";
import {Genre} from "./genre";
import {Keyword} from "./keyword";

export class Song {
  id: number = 0;
  title: string = '';
  //Year type in JAVA
  year: Date = null;
  lyrics: string = '';
  audioPath: string = '';
  songComments: Songcomment[] = null;
  songLikeNumber: number = 0;
  songDislikeNumber: number = 0;
  genres: Genre[] = null;
  keywords: Keyword[] = null;
}
