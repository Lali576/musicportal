import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {AuthService} from "../../../auth.service";
import {Router} from "@angular/router";
import {Genre} from "../../../model/genre";
import {UserService} from "../../../service/user.service";
import {HttpClient, HttpClientModule, HttpHeaders} from "@angular/common/http";
import {tap} from "rxjs/internal/operators";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User = new User();
  password: string = "";
  message: string = "";
  chooseableGenres: Genre[] = [];
  isArtist: boolean = false;
  userIconFile: File;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router,
    private http: HttpClient
  ) { }

  ngOnInit() {
    this.userService.getGenres().subscribe(
      genres => {
        this.chooseableGenres = genres;
      });
  }

  onFileSelected(event) {
    this.userIconFile = <File>event.target.files[0];
    console.log(this.userIconFile);
  }

  async submit(f) {
    if(f.invalid) {
      return;
    }
    try {
      const uploadData = new FormData();
      uploadData.append(this.userIconFile.name, this.userIconFile, this.userIconFile.name);
      uploadData.append("user", JSON.stringify(this.user));
      uploadData.append("password", this.password);

      if (this.isArtist) {
        this.user.role = "ARTIST";
      } else {
        this.user.role = "USER";
      }

      await this.authService.register(uploadData);
      console.log("successful registration");
      this.router.navigate(['/user', this.authService.user.id]);
    } catch (e) {
      this.message = "Sikertelen regisztráció";
      console.log(e);
    }
  }
}
