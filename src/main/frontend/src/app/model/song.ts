import {Songcomment} from "./songcomment";
import {Genre} from "./genre";
import {Keyword} from "./keyword";

export class Song {
  id: number = 0;
  title: string = '';
  year: Date = null;
  lyrics: string = '';
  audioFile: File = null;
}
