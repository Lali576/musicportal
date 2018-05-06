import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {AuthService} from "../../../auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User = new User();
  password: string = "";
  message: string = "";

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
  }

  async submit(f) {
    if(f.invalid) {
      return;
    }
    try {
      this.user.iconFile = null;
      this.user.favGenreId = null;
      this.user.biography = "";
      console.log(this.user);
      console.log(this.password);
      this.message = "Regisztráció folyamatban";
      await this.authService.register(this.user, this.password);
      console.log("successful registration");
      this.router.navigate([this.authService.redirectUrl]);
    } catch (e) {
      this.message = "Sikertelen regisztráció";
      console.log(e);
    }
  }
}
