import {User} from "./user";
import {AlbumKeyword} from "./keywords/albumkeyword";

export class Album {
  id: number = 0;
  title: string = "";
  date: Date = new Date();
  albumFolderGdaId: string = "";
  albumSongsFolderGdaId: string = "";
  albumCoverFolderGdaId: string = "";
  coverFileGdaId: string = "";
  user: User = null;
  albumTags: AlbumKeyword[] = [];
  type: string = "SINGLE";
}
