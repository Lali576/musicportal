import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../../auth.service";
import {UserMessage} from "../../../model/usermessage";

@Component({
  selector: 'app-user-message',
  templateUrl: './user-message.component.html',
  styleUrls: ['./user-message.component.css']
})
export class UserMessageComponent implements OnInit {

  userMessages: UserMessage[] = [];

  constructor(
    private authService: AuthService,
  ) { }

  ngOnInit() {
    if(this.authService.user.role !== 'GUEST') {
      this.userMessages = this.userMessages;
    }
  }

}
