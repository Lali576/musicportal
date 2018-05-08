import {User} from "./user";

export class UserMessage {
  id: number = 0;
  userFrom: User = null;
  textMessage: string = "";
  dateTime: Date = null;
}
