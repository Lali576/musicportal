import { Component, OnInit } from '@angular/core';
import { User } from "../../../model/user";
import { AuthService } from "../../../service/auth.service";
import { UserService } from "../../../service/user.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {ConfirmationService, MessageService} from "primeng/api";
import {Album} from "../../../model/album";
import {Song} from "../../../model/song";
import {Playlist} from "../../../model/playlist";
import {AlbumService} from "../../../service/album.service";
import {SongService} from "../../../service/song.service";
import {PlaylistService} from "../../../service/playlist.service";
import {switchMap} from "rxjs/internal/operators";
import {UserTag} from "../../../model/Tags/usertag";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css'],
  providers: [ConfirmationService, MessageService]
})
export class UserDetailComponent implements OnInit {

  user: User = new User();
  userAlbums: Album[] = [];
  userSongs: Song[] = [];
  userPlaylist: Playlist[] = [];
  userIconFile: File = null;
  userTags: UserTag[] = [];
  display: boolean = false;

  constructor(
    public authService: AuthService,
    private userService: UserService,
    private albumService: AlbumService,
    private songService: SongService,
    private playlistService: PlaylistService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = +params.get('id');
      await this.userService.getUser(id).then(
        (user: User) => {
          this.user = user;
          this.loadUserTags();
          this.loadUserMessages();
          this.loadAlbums();
          this.loadSongs();
          this.loadPlaylists();
        });
    })).subscribe();
  }

  showDialog() {
    this.display = true;
  }

  onFileSelected(event) {
    this.userIconFile = <File>event.files[0];
    console.log(this.userIconFile);
  }

  onFileRemove() {
    this.userIconFile = null;
    console.log("You're know you right...");
  }

  async changeIconFile() {
    await this.userService.updateIconFile(this.user.id, this.userIconFile);
    this.user.iconFileGdaId = this.authService.loggedInUser.iconFileGdaId;
  }

  async deleteUserConfirm() {
    this.confirmationService.confirm({
      message: "Biztos szeretné törölni regisztrációját\na weboldalról ?",
      header: 'Regisztráció törlés',
      icon: 'fas fa-exclamation-triangle',
      accept: () => {
        this.deleteUser();
      },
      reject: () => {
      }
    });
  }

  async deleteUser() {
    await this.userService.deleteUser(this.user.id);
    this.router.navigate(['/login']);
  }

  loadUserTags() {
    this.userService.getUserTags(this.user.id)
      .then(
        (userTags: UserTag[]) => {
          this.userTags = userTags;
        }
      );
  }

  loadUserMessages() {
    this.userService.getUserMessages(this.user.id);
  }

  loadAlbums() {
    this.albumService.getUserAlbums(this.user.id)
      .then(
        (albums: Album[]) => {
          this.userAlbums = albums;
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

  loadSongs() {
    this.songService.getSongsByUser(this.user.id)
      .then(
        (songs: Song[]) => {
          this.userSongs = songs;
        }
      );
  }

  deleteSongConfirm(song: Song) {
    this.confirmationService.confirm({
      message: "Biztos szeretné törölni az alábbi dalt: " + song.title + " ?",
      header: 'Dal törlés',
      icon: 'fas fa-exclamation-triangle',
      accept: () => {
        this.deleteSong(song);
      },
      reject: () => {
      }
    });
  }

  async deleteSong(song) {
    await this.songService.deleteSong(song);
    this.messageService.add({severity:'success', summary: song.title + ' című album sikeresen törölve', detail:''});
    this.loadSongs();
  }

  loadPlaylists() {
    this.playlistService.getUserPlaylist(this.user.id)
      .then(
        (playlist: Playlist[]) => {
          this.userPlaylist = playlist;
        }
      )
  }

  deletePlaylistConfirm(playlist) {
    this.confirmationService.confirm({
      message: "Biztos szeretné törölni az alábbi lejátszási listát: " + playlist.title + " ?",
      header: 'Lej. lista törlés',
      icon: 'fas fa-exclamation-triangle',
      accept: () => {
        this.deletePlaylist(playlist);
      },
      reject: () => {
      }
    });
  }

  async deletePlaylist(playlist) {
    await this.playlistService.deletePlaylist(playlist)
    this.messageService.add({severity:'success', summary: playlist.title + ' nevű lej. lista sikeresen törölve', detail:''});
    this.loadPlaylists();
  }
}
