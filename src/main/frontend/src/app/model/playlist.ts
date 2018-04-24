import {Keyword} from "./keyword";
import {Song} from "./song";
import {User} from "./user";

export class Playlist {
  id: number = 0;
  user: User = null;
  name: string = '';
  songs: Song[] = null;
  keywords: Keyword[] = null;
}
