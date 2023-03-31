import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginInterface } from 'src/app/models/LoginInterface';
import { TokenInterface } from 'src/app/models/TokenInterface';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),

  });

  loginInterface: LoginInterface = {
    username: '',
    password: ''
  };

  constructor(private authService : AuthService,private router: Router ,
    private http : HttpClient, private route:ActivatedRoute) { }

  ngOnInit(): void {
  }



  public login(){
    // console.log(this.loginForm.value);
    this.loginInterface.username = this.loginForm.value.username!;
    this.loginInterface.password = this.loginForm.value.password!;
    // if (this.loginForm.value.username !== '' && this.loginForm.value.username !== undefined) {
    //   this.loginInterface.username = this.loginForm.value.username!;
    // }

    this.authService.login(this.loginInterface).subscribe(
      (tkn: TokenInterface) => {
        localStorage.setItem('user_token',tkn.accessToken)

        console.log(tkn.accessToken);

        let my_roles = JSON.parse(atob(tkn.accessToken.split('.')[1]))['roles'].split(',');
        console.log(my_roles);
        if (my_roles.includes('ROLE_OWNER')) { //ROLE CITIZEN
          this.router.navigate(['/', 'owner-home']);

        }

        if (my_roles.includes('ROLE_TENANT')) { //ROLE EMPLOYEE
          this.router.navigate(['/', 'tenant-home']);

        }

        if (my_roles.includes('ROLE_ADMIN')) { //ROLE EMPLOYEE
          this.router.navigate(['/', 'admin-home']);

        }

      },
    )
    console.log('sent user');

  }

}
