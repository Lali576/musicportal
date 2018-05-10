import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {Album} from "../../../model/album";

@Component({
  selector: 'app-album-form',
  templateUrl: './album-form.component.html',
  styleUrls: ['./album-form.component.css']
})
export class AlbumFormComponent implements OnChanges {

  @Input() album: Album;
  model: Album = null;
  @Output() onSubmit = new EventEmitter<Album>();

  constructor() { }

  ngOnChanges() {
    this.model = Object.assign({}, this.album);
  }

  submit(form) {
    if(!form.valid) {
      return;
    }
    this.onSubmit.emit(this.model);
  }
}
