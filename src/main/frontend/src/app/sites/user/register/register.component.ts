import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {AuthService} from "../../../service/auth.service";
import {Router} from "@angular/router";
import {Genre} from "../../../model/genre";
import {UserKeyword} from "../../../model/keywords/userkeyword";
import {GenreService} from "../../../service/genre.service";
import {Message} from 'primeng/components/common/api';
import {Country} from "../../../model/country";
import {CountryService} from "../../../service/country.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {

  user: User = new User();
  emailAddressConfirm: string = "";
  password: string = "";
  passwordConfirm : string = "";
  msgs: Message[] = [];
  isArtist: boolean = false;
  userIconFile: File = null;
  keywords: string[] = [];
  userKeywords: UserKeyword[] = [];
  genres: Genre[] = [];
  countries: Country[] = [];

  constructor(
    private authService: AuthService,
    private genreService: GenreService,
    private countryService: CountryService,
    private router: Router
  ) {
    this.genreService.getGenres().subscribe(
      (genres: Genre[]) => {
        this.genres = genres;
        this.user.favGenreId = this.genres[0];
      }
    );

    this.countryService.getCountries().subscribe(
      (countries: Country[]) => {
        this.countries = countries;
        this.user.country = this.countries[0];
      }
    );
  }

  ngOnInit() {
  }

  onFileSelected(event) {
    this.userIconFile = <File>event.files[0];
    console.log(this.userIconFile);
  }

  onFileRemove() {
    this.userIconFile = null;
    console.log("You're know you right...");
  }

  async submit(f) {
    if(f.invalid) {
      return;
    }
    try {
      if (this.isArtist) {
        this.user.role = "ARTIST";
      } else {
        this.user.role = "USER";
      }
      for(let keyword of this.keywords) {
        let userKeyword: UserKeyword = new UserKeyword();
        userKeyword.word = keyword;
        this.userKeywords.push(userKeyword);
      }
      this.showMsgInfo();
      await this.authService.register(this.user, this.password, this.userIconFile, this.userKeywords);
      console.log("successful registration");
      this.showMsgSuccess();
      console.log("Try to login with user named " + this.authService.loggedInUser.username + " and with number id" + this.authService.loggedInUser.id);
      await new Promise( resolve => setTimeout(resolve, 1000) );
      this.router.navigate(['/user', this.authService.loggedInUser.id]);
    } catch (e) {
      this.showMsgError();
      console.log(e);
    }
  }

  showMsgInfo() {
    this.msgs = [];
    this.msgs.push({severity:'info', summary:'Regisztráció folyamatban', detail:''});
  }

  showMsgSuccess() {
    this.msgs = [];
    this.msgs.push({severity:'success', summary:'Sikeres regisztráció', detail:''});
  }

  showMsgError() {
    this.msgs = [];
    this.msgs.push({severity:'error', summary:'Regisztráció sikertelen', detail:''});
  }
}
