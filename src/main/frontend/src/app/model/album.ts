import {User} from "./user";

export class Album {
  id: number = 0;
  title: string = "";
  date: Date = new Date();
  albumFolderGdaId: string = "";
  albumSongsFolderGdaId: string = "";
  albumCoverFolderGdaId: string = "";
  coverFileGdaId: string = "";
  user: User = null;
  type: string = "SINGLE";
}
