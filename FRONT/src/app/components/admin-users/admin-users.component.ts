import { UserTableInterface } from 'src/app/models/UserTableInterface';
import { UsersService } from './../../services/users.service';
import { Component } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent {
  displayedColumns: string[] = [ 'id', 'name', 'username', 'owner','tenant'];

  data: UserTableInterface[] = [];


  constructor(private usersService: UsersService ,  public sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.getAllUsers();

  }

  public getAllUsers() {
    // this.tkn = localStorage.getItem('user_token');
    // this.username = JSON.parse(atob(this.tkn.split('.')[1]))['sub'];
    this.usersService.getAllUsers().subscribe((data: UserTableInterface[]) => {
      this.data = data;
    });
  }

  public ownerRoleUser(username: string , flag:boolean) {
    // console.log(flag);
    // const userToUpdate = this.data.find(user => user.username === username);

    // if (userToUpdate) {
    //   userToUpdate.owner = !userToUpdate.owner;
    // }

    this.usersService.ownerRoleUser(username,!flag).subscribe((b: Boolean) => {
      this.ngOnInit();
    });

  }

  public tenantRoleUser(username: string , flag:boolean) {
    this.usersService.tenantRoleUser(username , !flag).subscribe((b: Boolean) => {
      this.ngOnInit();
    });

  }
}
