import { Component, OnInit } from '@angular/core';
import { User } from "../../../model/user";
import { AuthService } from "../../../service/auth.service";
import { UserService } from "../../../service/user.service";
import { Router } from "@angular/router";
import { ConfirmationService } from "primeng/api";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css'],
  providers: [ConfirmationService]
})
export class UserDetailComponent implements OnInit {

  user: User = new User();
  userIconFile: File = null;
  display: boolean = false;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private confirmationService: ConfirmationService,
    private router: Router
  ) { }

  ngOnInit() {
    this.user = this.authService.loggedInUser;
  }

  showDialog() {
    this.display = true;
  }

  onFileSelected(event) {
    this.userIconFile = <File>event.files[0];
    console.log(this.userIconFile);
  }

  onFileRemove() {
    this.userIconFile = null;
    console.log("You're know you right...");
  }

  async changeIconFile() {
    await this.userService.updateIconFile(this.user.id, this.userIconFile);
    this.user.iconFileGdaId = this.authService.loggedInUser.iconFileGdaId;
  }

  async deleteUserConfirm() {
    this.confirmationService.confirm({
      message: "Biztos szeretné törölni regisztrációját\na weboldalról ?",
      header: 'Regisztráció törlés',
      icon: 'fas fa-exclamation-triangle',
      accept: () => {
        this.deleteUser();
      },
      reject: () => {
      }
    });
  }

  async deleteUser() {
    await this.userService.deleteUser(this.user.id);
    this.router.navigate(['/login']);
  }
}
