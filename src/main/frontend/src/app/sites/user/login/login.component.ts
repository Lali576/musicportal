import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {AuthService} from "../../../auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string = "";
  password: string = "";
  message: string = "";

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
  }

  ngOnInit() {
  }

  async submit(f) {
    if (f.invalid) {
      return;
    }
    try {
      this.message = "Try to login";
      await this.authService.login(this.username, this.password);
      console.log("success");
      this.router.navigate([this.authService.redirectUrl]);
    } catch (e) {
      this.message = "Login failed";
      console.log(e);
    }
  }
}
