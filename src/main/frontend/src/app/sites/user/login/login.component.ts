import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../../auth.service";
import {Router} from "@angular/router";
import {UserMessage} from "../../../model/usermessage";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string = "";
  password: string = "";
  message: string = "";
  userMessage: UserMessage = new UserMessage();

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
      this.message = "Bejelentkezés folyamatban";
      await this.authService.login(this.username, this.password);
      console.log("successful logging");
      this.router.navigate([this.authService.redirectUrl]);
    } catch (e) {
      this.message = "Sikertelen bejelentkezés";
      console.log(e);
    }
  }

  async send(g) {
    if(g.invalid) {
      return;
    }
    try {
      console.log(this.userMessage);
      await this.authService.sendMessage(this.userMessage);
      console.log("yeah");
    } catch (e) {
      console.log(e);
    }
  }
}
