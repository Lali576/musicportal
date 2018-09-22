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

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private confirmationService: ConfirmationService,
    private router: Router
  ) { }

  ngOnInit() {
    this.user = this.authService.loggedInUser;
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
