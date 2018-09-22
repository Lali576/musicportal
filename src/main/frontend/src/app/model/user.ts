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
  iconFileGdaId: string = "";
  role: string = "GUEST";
  userKeywords: UserKeyword[] = [];
}
