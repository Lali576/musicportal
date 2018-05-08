import {Song} from "./song";
import {Genre} from "./genre";
import {Keyword} from "./keyword";

export class Album {
  id: number = 0;
  name: string = '';
  year: Date = null;
  coverFile: File = null;
}
