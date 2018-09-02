import { Component, OnInit } from '@angular/core';
import {User} from "../../../model/user";
import {AuthService} from "../../../service/auth.service";
import {Router} from "@angular/router";
import {Genre} from "../../../model/genre";
import {UserService} from "../../../service/user.service";
import {HttpClient, HttpClientModule, HttpHeaders} from "@angular/common/http";
import {tap} from "rxjs/internal/operators";
import {logging} from "selenium-webdriver";
import {UserKeyword} from "../../../model/keywords/userkeywords";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User = new User();
  emailAddressConfirm: string = "";
  password: string = "";
  passwordConfirm : string = "";
  message: string = "";
  chooseAbleGenres: Genre[] = [];
  isArtist: boolean = false;
  userIconFile: File = null;
  userKeywords: UserKeyword[] = [];

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
    this.userService.getGenres().subscribe(
      genres => {
        this.chooseAbleGenres = genres;
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
      if (this.isArtist) {
        this.user.role = "ARTIST";
      } else {
        this.user.role = "USER";
      }

      const uploadData = new FormData();
      if(this.userIconFile !== null) {
        uploadData.append(this.userIconFile.name, this.userIconFile, this.userIconFile.name);
      }
      uploadData.append("user", JSON.stringify(this.user));
      uploadData.append("password", this.password);
      uploadData.append("userKeywords", JSON.stringify(this.userKeywords));

      this.message = "Regisztráció folyamatban";
      await this.authService.register(uploadData);
      console.log("successful registration");
      this.message = "Regisztráció sikeres";
      console.log("Try to login with user named " + this.authService.loggedInUser.username + " and with number id" + this.authService.loggedInUser.id);
      this.router.navigate(['/user', this.authService.loggedInUser.id]);
    } catch (e) {
      this.message = "Sikertelen regisztráció";
      console.log(e);
    }
  }

  getUserErrorMessage(usernameText) {
    return usernameText.hasError('required') ? 'Adja meg a felhasználó nevét!' :
          usernameText.hasError('pattern') ? 'A megadott felhasználónév karakterisztikája nem megfelelő!' :
          usernameText.hasError('minlength') ? 'A megadott felhasználónév kevesebb, mint 5 karakterből áll!':
          usernameText.hasError('maxlength') ? 'A megadott felhasználónév több, mint 30 karakterből áll!':
            '';
  }

  getEmailAddressErrorMessage(emailAddressText) {
    return emailAddressText.hasError('required') ? "Adja meg az e-mail címét!" :
          emailAddressText.hasError('pattern') ? 'A megadott e-mail cím karakterisztikája nem megfelelő!' :
          emailAddressText.hasError('minlength') ? 'A megadott e-mail cím kevesebb, mint 5 karakterből áll!':
            '';
  }

  getPasswordErrorMessage(passwordText) {
    return passwordText.hasError('required') ? 'Adja meg a jelszavát!':
          passwordText.hasError('pattern') ? 'A megadott jelszó karakterisztikája nem megfelelő!' :
          passwordText.hasError('minlength') ? 'A megadott jelszó kevesebb, mint 6 karakterből áll!':
          passwordText.hasError('maxlength') ? 'A megadott jelszó több, mint 15 karakterből áll!':
            '';
  }
}
