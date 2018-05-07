import {Genre} from "./genre";

export class User {
  id: number = 0;
  username: string = "";
  emailAddress: string = "";
  favGenreId: Genre;
  fullName: string = "";
  biography: string = "";
  iconFile: File = null;
  role: string = "GUEST";
}
