import {User} from "./user";
import {PlaylistTag} from "./Tags/playlisttag";

export class Playlist {
  id: number = 0;
  name: string = "";
  user: User = null;
  date: Date = new Date();
  playlistTags: PlaylistTag[] = [];
}
