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
  chooseableGenres: Genre[] = [];
  isArtist: boolean = false;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
    this.userService.getGenres().subscribe(
      genres => {
        this.chooseableGenres = genres;
      });
  }

  async submit(f) {
    if(f.invalid) {
      return;
    }
    try {
      if(this.isArtist) {
        this.user.role = "ARTIST";
      } else {
        this.user.role = "USER";
      }
      this.message = "Regisztráció folyamatban";
      var userString = JSON.stringify(this.user);
      console.log(userString);
      await this.authService.register(userString, this.password);
      console.log("successful registration");
      this.router.navigate(['/user', this.authService.user.id]);
    } catch (e) {
      this.message = "Sikertelen regisztráció";
      console.log(e);
    }
  }
}
