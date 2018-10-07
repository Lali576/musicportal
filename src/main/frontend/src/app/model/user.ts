import {Genre} from "./genre";
import {Country} from "./country";
import {UserKeyword} from "./keywords/userkeyword";

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
  userKeywords: UserKeyword[] = [];
  role: string = "GUEST";
}
