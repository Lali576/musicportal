import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../../service/auth.service";
import {Router} from "@angular/router";
import {Keyword} from "../../../model/keywords/keyword";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string = "";
  password: string = "";
  message: string = "";
  keyword: Keyword = new Keyword();
  keywords: Keyword[] = [];

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
  }

  ngOnInit() {}


  async submit(f) {
    if (f.invalid) {
      return;
    }
    try {
      const uploadData = new FormData();
      uploadData.append("username", this.username);
      uploadData.append("password", this.password)
      this.message = "Bejelentkezés folyamatban";
      await this.authService.login(uploadData);
      console.log("successful logging");
      this.router.navigate(['/user', this.authService.loggedInUser.id]);
    } catch (e) {
      this.message = "Sikertelen bejelentkezés";
      console.log(e);
    }
  }
}
