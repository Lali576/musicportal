import {Song} from "./song";
import {Genre} from "./genre";
import {Keyword} from "./keyword";

export class Album {
  id: number = 0;
  name: string = '';
  //Year type in JAVA
  year: Date = null;
  coverPath: string = '';
  songsNumber: number = 0;
  songs: Song[] = null;
  genres: Genre[] = null;
  keywords: Keyword[] = null;
}
