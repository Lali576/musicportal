import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../../../service/user.service";
import {AuthService} from "../../../service/auth.service";
import {Message} from "primeng/api";

@Component({
  selector: 'app-user-password',
  templateUrl: './user-password.component.html',
  styleUrls: ['./user-password.component.css']
})
export class UserPasswordComponent implements OnInit {
  oldPassword: string = "";
  newPassword: string = "";
  newPasswordConfirm: string = "";
  msgs: Message[] = [];

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
  }

  async submit(f) {
    if (f.invalid) {
      return;
    }
    try {
      this.showMsgInfo();
      await this.userService.updatePassword(this.authService.loggedInUser.id, this.oldPassword, this.newPassword);
      this.showMsgSuccess();
      this.router.navigate(['/user', this.authService.loggedInUser.id]);
    } catch (e) {
      this.showMsgError();
      console.log(e);
    }
  }

  showMsgInfo() {
    this.msgs = [];
    this.msgs.push({severity:'info', summary:'Küldés folyamatban', detail:''});
  }

  showMsgSuccess() {
    this.msgs = [];
    this.msgs.push({severity:'success', summary:'Sikeres küldés', detail:''});
  }

  showMsgError() {
    this.msgs = [];
    this.msgs.push({severity:'error', summary:'Küldés sikertelen', detail:''});
  }
}
