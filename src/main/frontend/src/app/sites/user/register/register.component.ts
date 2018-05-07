import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {AuthService} from "../../../auth.service";
import {Router} from "@angular/router";
import {Genre} from "../../../model/genre";
import {UserService} from "../../../service/user.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User = new User();
  password: string = "";
  message: string = "";
  genres: Genre[] = [];
  favGenre: Genre = new Genre();

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
    this.userService.getGenres().subscribe(
      genres => {
        this.genres = genres;
      });
  }

  write() {
    console.log(this.user.favGenreId);
  }

  async submit(f) {
    if(f.invalid) {
      return;
    }
    try {
      this.user.favGenreId = this.favGenre;
      this.user.biography = "";
      console.log(this.favGenre)
      console.log(this.user);
      this.message = "Regisztráció folyamatban";
      var userString = JSON.stringify(this.user);
      await this.authService.register(userString, this.password);
      console.log("successful registration");
      this.router.navigate([this.authService.redirectUrl]);
    } catch (e) {
      this.message = "Sikertelen regisztráció";
      console.log(e);
    }
  }
}
