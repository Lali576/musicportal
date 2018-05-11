import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {AuthService} from "../../../auth.service";
import {Router} from "@angular/router";
import {Genre} from "../../../model/genre";
import {UserService} from "../../../service/user.service";
import {HttpClient, HttpClientModule, HttpHeaders} from "@angular/common/http";

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
      if(this.isArtist) {
        this.user.role = "ARTIST";
      } else {
        this.user.role = "USER";
      }
      this.message = "Regisztráció folyamatban";
      var userString = JSON.stringify(this.user);

      const uploadData = new FormData();
      uploadData.append(this.user.username, this.userIconFile, this.userIconFile.name);
      await this.http.post('/api/user/file', uploadData).subscribe(
        (res: string) => {
          console.log(res);
          this.subimt2();
        });
    } catch (e) {
      this.message = "Sikertelen regisztráció";
      console.log(e);
    }
  }

  async subimt2() {
    if(this.isArtist) {
      this.user.role = "ARTIST";
    } else {
      this.user.role = "USER";
    }
    this.message = "Regisztráció folyamatban";
    var userString = JSON.stringify(this.user);

    await this.authService.register(userString, this.password);
    console.log("successful registration");
    this.router.navigate(['/user', this.authService.user.id]);
  }
}
