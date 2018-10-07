import {Album} from "./album";
import {User} from "./user";
import {SongKeyword} from "./keywords/songkeyword";

export class Song {
  id: number = 0;
  title: string = "";
  lyrics: string = "";
  audioFileGdaId = "";
  duration: number = 0;
  album: Album = null;
  user: User = null;
  songKeywords: SongKeyword[] = [];
}
