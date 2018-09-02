import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {AuthService} from "../../../service/auth.service";
import {DomSanitizer, SafeStyle} from "@angular/platform-browser";
import {UserService} from "../../../service/user.service";
import {Route, Router} from "@angular/router";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  user: User = new User();

  constructor(
    private authService: AuthService,
    private userService: UserService,
  ) { }

  ngOnInit() {
    this.user = this.authService.loggedInUser;
  }

  delete() {
    if(confirm("Biztos szeretné magát törölni az oldalról?")) {
      this.userService.deleteUser(this.user.id);
    }
  }
}
