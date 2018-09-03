import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {UserService} from "../../../service/user.service";
import {Location} from "@angular/common";
import {HttpClient} from "@angular/common/http";
import {switchMap} from "rxjs/internal/operators";
import {Observable, of} from "rxjs/index";

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {

  user: User;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private location: Location,
    private http: HttpClient
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(switchMap(async (params: ParamMap) => {
      const id = params.get('id');
      if (id !== null) {
        this.user = await this.userService.getUser(+id);
      } else {
        this.user = new User();
      }

      return of(Observable);
    })).subscribe();
  }

  async onFormSubmit(params: object) {
    var user: User = params["user"];
    var file: File = params["file"];

    const uploadData: FormData = new FormData();
    uploadData.append("user", JSON.stringify(user));
    uploadData.append(file.name,file,file.name);

    if (user.id > 0) {
      //W.I.P.
    } else {
      // register section copy here
    }
  }

}
