import { Component, OnInit } from '@angular/core';
import {Album} from "../../../model/album";
import {AlbumService} from "../../../service/album.service";
import {Router} from "@angular/router";
import {ConfirmationService, Message, MessageService} from "primeng/api";
import {async} from "q";

@Component({
  selector: 'app-album-list',
  templateUrl: './album-list.component.html',
  styleUrls: ['./album-list.component.css'],
  providers: [ConfirmationService, MessageService]
})
export class AlbumListComponent implements OnInit {

  albums: Album[] = [];

  constructor(
    private albumService: AlbumService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) { }

  ngOnInit() {
    this.loadAlbums();
  }

  loadAlbums() {
    this.albumService.getAllAlbums()
      .then(
        (albums: Album[]) => {
          this.albums = albums;
        }
      );
  }

  deleteAlbumConfirm(album: Album) {
    this.confirmationService.confirm({
      message: "Biztos szeretné törölni az alábbi albumot: " + album.title + " ?",
      header: 'Album törlés',
      icon: 'fas fa-exclamation-triangle',
      accept: () => {
        this.deleteAlbum(album);
      },
      reject: () => {
      }
    });
  }

 async deleteAlbum(album) {
    await this.albumService.deleteAlbum(album);
    this.messageService.add({severity:'success', summary: album.title + ' című album sikeresen törölve', detail:''});
    this.loadAlbums();
  }
}
