import {User} from "./user";
import {AlbumTag} from "./Tags/albumtag";

export class Album {
  id: number = 0;
  title: string = "";
  date: Date = new Date();
  albumFolderGdaId: string = "";
  albumSongsFolderGdaId: string = "";
  albumCoverFolderGdaId: string = "";
  coverFileGdaId: string = "";
  user: User = null;
  albumTags: AlbumTag[] = [];
  type: string = "SINGLE";
}
