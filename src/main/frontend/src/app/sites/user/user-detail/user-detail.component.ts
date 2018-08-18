import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {AuthService} from "../../../auth.service";
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
  //imageUrl2;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit() {
    this.user = this.authService.user;
    //var imageUrl: string  = this.user.iconFile.toString();
    //this.imageUrl2 = this.sanitizer.bypassSecurityTrustResourceUrl("C:\\Users\\Tóth Ádám\\Downloads\\jpeg-home.jpg");
  }

  update() {

  }

  delete() {
    if(confirm("Biztos szeretné magát törölni az oldalról?")) {
      this.userService.deleteUser(this.user.id);
    }
  }
}
