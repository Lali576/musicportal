import {User} from "./user";

export class SongComment {
  id: number = 0;
  user: User = null;
  textMessage: string = "";
  date: Date = null;
}
