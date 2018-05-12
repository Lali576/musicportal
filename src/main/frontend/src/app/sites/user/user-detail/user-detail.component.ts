import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {AuthService} from "../../../auth.service";
import {DomSanitizer, SafeStyle} from "@angular/platform-browser";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  user: User = new User();
  imageUrl2;

  constructor(
    private authService: AuthService,
    private sanitizer: DomSanitizer
  ) { }

  ngOnInit() {
    this.user = this.authService.user;
    //var imageUrl: string  = this.user.iconFile.toString();
    this.imageUrl2 = this.sanitizer.bypassSecurityTrustResourceUrl("C:\\Users\\Tóth Ádám\\Downloads\\jpeg-home.jpg");
  }
}
