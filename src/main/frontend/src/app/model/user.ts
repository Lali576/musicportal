import {Genre} from "./genre";
import {Country} from "./country";

export class User {
  id: number = 0;
  username: string = "";
  emailAddress: string = "";
  favGenreId: Genre = null;
  country: Country = null;
  biography: string = "";
  userFolderGdaId: string = "";
  iconFileGdaId: string = "";
  role: string = "GUEST";
}
