import {Genre} from "./genre";

export class User {
  id: number = 0;
  username: string = '';
  emailAddress: string = '';
  saltCode: string = '';
  hashPassword: string = '';
  favGenreId: Genre = null;
  biography: string = '';
  iconFile: File = null;
  role: string = 'GUEST';
}
