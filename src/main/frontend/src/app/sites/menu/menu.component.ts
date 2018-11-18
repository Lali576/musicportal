import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  searchWord: string = "";

  constructor(
    public authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
    this.authService.getUser();
  }

  async logout() {
    try {
      await this.authService.logout();
      this.router.navigate(["/"]);
    } catch (err) {
      console.log(err);
    }
  }

  keyDownFunction(event) {
    if(event.keyCode == 13) {
      this.sendSearchWord();
    }
  }

  sendSearchWord() {
    this.router.navigate(['/search/word', this.searchWord]);
  }
}
