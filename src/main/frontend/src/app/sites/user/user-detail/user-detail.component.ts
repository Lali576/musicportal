import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
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
import {TagService} from "../../../service/tag.service";
import {UserMessage} from "../../../model/usermessage";
import {Country} from "../../../model/country";
import {Genre} from "../../../model/genre";
import {CountryService} from "../../../service/country.service";
import {GenreService} from "../../../service/genre.service";

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
    await this.userService.updateIconFile(this.user.id, this.userIconFile);
    this.user.iconFileGdaId = this.authService.loggedInUser.iconFileGdaId;
  }

  async changeDetails() {
    let userTags: UserTag[] = [];

    for(let tag of this.userEditUserTags) {
      let userTag: UserTag = new UserTag();
      userTag.word = tag;
      userTags.push(userTag);
    }
    await this.userService.updateUser(this.user.id, this.userEditCountryId, this.userEditGenreId, userTags);
    this.user = this.authService.loggedInUser;
    this.loadUserTags();
  }

  async changeEmail() {
    await this.userService.updateEmail(this.user.id, this.userNewEmail);
    this.user.emailAddress = this.authService.loggedInUser.emailAddress;
  }

  async changeBiography() {
    await this.userService.updateBiography(this.user.id, this.userEditBiography);
    this.user.biography = this.authService.loggedInUser.biography;
    this.userEditBiography = this.user.biography;
  }

  async sendMessage() {
    let userMessage: UserMessage = new UserMessage();
    userMessage.textMessage = this.tempMessageText;
    this.tempMessageText = "";
    let userToId = this.user.id;
    await this.userService.addUserMessages(userToId, userMessage).then(
      (userMessages: UserMessage[]) => {
        this.userMessages = userMessages;
      }
    );
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
