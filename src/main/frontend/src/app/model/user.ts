import {Genre} from "./genre";
import {Album} from "./album";
import {Playlist} from "./playlist";
import {Keyword} from "./keyword";
import {Usermessage} from "./usermessage";

export class User {
  id: number = 0;
  username: string = '';
  emailAddress: string = '';
  saltCode: string = '';
  hashPassword: string = '';
  favGenreId: Genre = null;
  biography: string = '';
  iconPath: string = '';
  role: string = 'GUEST';
  albums: Album[] = null;
  playlists: Playlist[] = null;
  keywords: Keyword[] = null;
  userMessages: Usermessage[] = null;
}
