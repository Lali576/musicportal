import {Genre} from "./genre";
import {Country} from "./country";
import {UserTag} from "./Tags/usertag";

export class User {
  id: number = 0;
  username: string = "";
  emailAddress: string = "";
  favGenreId: Genre = null;
  countryId: Country = null;
  biography: string = "";
  userFolderGdaId: string = "";
  userAlbumsFolderGdaId: string = "";
  userIconFolderGdaId: string = "";
  iconFileGdaId: string = "";
  userTags: UserTag[] = [];
  role: string = "GUEST";
}
