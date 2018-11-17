import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../../service/auth.service";
import {Router} from "@angular/router";
import {Message} from "primeng/api";
import {delay} from "q";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string = "";
  password: string = "";
  msgs: Message[] = [];

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
      this.showMsgInfo();
      await this.authService.login(this.username, this.password);
      this.showMsgSuccess();
      console.log("Successful logging");
      await delay(1000);
      this.router.navigate(['/user', this.authService.loggedInUser.id]);
    } catch (e) {
      this.showMsgError();
      console.log("Error! Unsuccessful logging");
      console.log(e);
    }
  }

  showMsgInfo() {
    this.msgs = [];
    this.msgs.push({severity:'info', summary:'Bejelentkezés folyamatban', detail:''});
  }

  showMsgSuccess() {
    this.msgs = [];
    this.msgs.push({severity:'success', summary:'Sikeres bejelentkezés', detail:''});
  }

  showMsgError() {
    this.msgs = [];
    this.msgs.push({severity:'error', summary:'Bejelentkezés sikertelen', detail:''});
  }
}
