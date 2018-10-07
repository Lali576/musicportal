import {User} from "./user";
import {PlaylistKeyword} from "./keywords/playlistkeyword";

export class Playlist {
  id: number = 0;
  name: string = "";
  user: User = null;
  date: Date = new Date();
  playlistKeywords: PlaylistKeyword[] = [];
}
