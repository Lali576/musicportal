import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
import { User } from "../../../model/user";
import { AuthService } from "../../../service/auth.service";
import { UserService } from "../../../service/user.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {Album} from "../../../model/album";
import {Song} from "../../../model/song";
import {Playlist} from "../../../model/playlist";
import {AlbumService} from "../../../service/album.service";
import {SongService} from "../../../service/song.service";
import {PlaylistService} from "../../../service/playlist.service";
import {switchMap} from "rxjs/internal/operators";
import {UserTag} from "../../../model/Tags/usertag";
import {TagService} from "../../../service/tag.service";
import {UserMessage} from "../../../model/usermessage";
import {Country} from "../../../model/country";
import {Genre} from "../../../model/genre";
import {CountryService} from "../../../service/country.service";
import {GenreService} from "../../../service/genre.service";
import {delay} from "q";
import {ConfirmationService, MessageService} from "primeng/api";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css'],
  providers: [ConfirmationService, MessageService]
})
export class UserDetailComponent implements OnInit {

  user: User = new User();
  userAlbums: Album[] = [];
  userTags: UserTag[] = [];
  userMessages: UserMessage[] = [];
  userSongs: Song[] = [];
  userPlaylist: Playlist[] = [];

  userIconFile: File = null;
  userEditCountryId: Country = null;
  userEditGenreId: Genre = null;
  userEditUserTags: string[] = [];
  userEditBiography: string = "";
  tempMessageText: string = "";

  detailsEditDisplay: boolean = false;
  emailEditDisplay: boolean = false;
  iconEditDisplay: boolean = false;
  bioEditDisplay: boolean = false;
  messageDisplay: boolean = false;

  userNewEmail: string = "";
  userNewEmailConfirm: string = "";

  countries: Country[] = [];
  genres: Genre[] = [];


  constructor(
    public authService: AuthService,
    private userService: UserService,
    private albumService: AlbumService,
    private songService: SongService,
    private playlistService: PlaylistService,
    private tagService: TagService,
    private countryService: CountryService,
    private genreService: GenreService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private router: Router,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = +params.get('id');
      await this.userService.getUser(id).then(
        (user: User) => {
          this.user = user;
          this.userEditCountryId = this.user.countryId;
          this.userEditGenreId = this.user.favGenreId;
          this.userEditBiography = this.user.biography;
          this.loadUserTags();
          this.loadUserMessages();
          this.loadAlbums();
          this.loadSongs();
          this.loadPlaylists();
        });
    })).subscribe();
  }

  loadUserTags() {
    this.tagService.getTagsByUser(this.user.id)
      .then(
        (userTags: UserTag[]) => {
          this.userTags = userTags;
          this.loadEditUserTags();
        }
      );
  }

  loadEditUserTags() {
    this.userEditUserTags = [];

    for(let userTag of this.userTags) {
      let word: string = userTag.word;
      this.userEditUserTags.push(word);
    }
  }

  loadUserMessages() {
    this.userService.getUserMessages(this.user.id)
      .then(
        (userMessages: UserMessage[]) => {
          this.userMessages = userMessages;
        }
      );
  }

  loadAlbums() {
    this.albumService.getUserAlbums(this.user.id)
      .then(
        (albums: Album[]) => {
          this.userAlbums = albums;
        }
      );
  }

  loadSongs() {
    this.songService.getSongsByUser(this.user.id)
      .then(
        (songs: Song[]) => {
          this.userSongs = songs;
        }
      );
  }

  loadPlaylists() {
    this.playlistService.getUserPlaylist(this.user.id)
      .then(
        (playlist: Playlist[]) => {
          this.userPlaylist = playlist;
        }
      )
  }

  onFileSelected(event) {
    this.userIconFile = <File>event.files[0];
    console.log(this.userIconFile);
  }

  onFileRemove() {
    this.userIconFile = null;
  }

  async changeIconFile() {
    try {
      this.messageService.add({severity:'info', summary: 'Ikon kép feltöltés alatt', detail:''});
      await this.userService.updateIconFile(this.user.id, this.userIconFile);
      this.user.iconFileGdaId = this.authService.loggedInUser.iconFileGdaId;
      this.messageService.add({severity:'success', summary: 'Ikon kép feltöltése sikeres', detail:''});
    } catch (e) {
      this.messageService.add({severity:'error', summary: 'Ikon kép feltöltése sikertelen', detail:''});
    }
  }

  async changeDetails() {
    let userTags: UserTag[] = [];

    for(let tag of this.userEditUserTags) {
      let userTag: UserTag = new UserTag();
      userTag.word = tag;
      userTags.push(userTag);
    }

    try {
      this.messageService.add({severity:'info', summary: 'Adatok feltöltés alatt', detail:''});
      await this.userService.updateUser(this.user.id, this.userEditCountryId, this.userEditGenreId, userTags);
      this.user = this.authService.loggedInUser;
      this.messageService.add({severity:'success', summary: 'Adatok feltöltése sikeres', detail:''});
      this.loadUserTags();
    } catch (e) {
      this.messageService.add({severity:'error', summary: 'Adatok feltöltése sikertelen', detail:''});
    }
  }

  async changeEmail() {
    try {
      this.messageService.add({severity:'info', summary: 'E-mail cím feltöltés alatt', detail:''});
      await this.userService.updateEmail(this.user.id, this.userNewEmail);
      this.user.emailAddress = this.authService.loggedInUser.emailAddress;
      this.messageService.add({severity:'success', summary: 'E-mail cím feltöltése sikeres', detail:''});
    } catch (e) {
      this.messageService.add({severity:'error', summary: 'E-mail cím feltöltése sikertelen', detail:''});
    }
  }

  async changeBiography() {
    try {
      this.messageService.add({severity:'info', summary: 'Biógráfia feltöltés alatt', detail:''});
      await this.userService.updateBiography(this.user.id, this.userEditBiography);
      this.user.biography = this.authService.loggedInUser.biography;
      this.userEditBiography = this.user.biography;
      this.messageService.add({severity:'success', summary: 'Biógráfia feltöltése sikeres', detail:''});
    } catch (e) {
      this.messageService.add({severity:'error', summary: 'Biógráfia feltöltése sikertelen', detail:''});
    }
  }

  async sendMessage() {
    try {
      let userMessage: UserMessage = new UserMessage();
      userMessage.textMessage = this.tempMessageText;
      this.tempMessageText = "";
      let userToId = this.user.id;
      this.messageService.add({severity:'info', summary: 'Üzenet feltöltés alatt', detail:''});
      await this.userService.addUserMessages(userToId, userMessage).then(
        (userMessages: UserMessage[]) => {
          this.userMessages = userMessages;
        }
      );
      this.messageService.add({severity:'success', summary: 'Üzenet feltöltése sikeres', detail:''});
    } catch (e) {
      this.messageService.add({severity:'error', summary: 'Üzenet feltöltése sikertelen', detail:''});
    }
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
    try {
      this.messageService.add({severity:'info', summary: 'Felhasználó törlés alatt', detail:''});
      await this.userService.deleteUser(this.user.id);
      this.messageService.add({severity:'success', summary: 'Felhasználó törlése sikeres', detail:''});
      await delay(1000);
      this.router.navigate(['/login']);
    } catch (e) {
      this.messageService.add({severity:'error', summary: 'Felhasználó törlése sikertelen', detail:''});
    }
  }

  showIconDialog() {
    this.iconEditDisplay = true;
  }

  showDetailsDialog() {
    this.loadCountries();
    this.loadGenres();
    this.detailsEditDisplay = true;
  }

  showEmailDialog() {
    this.emailEditDisplay = true;
  }

  showBioDialog() {
    this.bioEditDisplay = true;
  }

  showMessageDialog() {
    this.messageDisplay = true;
  }

  loadCountries() {
    this.countryService.getCountries()
      .subscribe(
        (countries: Country[]) => {
          this.countries = countries;
        }
      )
  }

  loadGenres() {
    this.genreService.getGenres()
      .subscribe(
        (genres: Genre[]) => {
          this.genres = genres;
        }
      )
  }

  goBack() {
    this.location.back();
  }
}
