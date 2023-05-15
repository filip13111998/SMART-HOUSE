import { AuthService } from 'src/app/services/auth.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-menu',
  templateUrl: './admin-menu.component.html',
  styleUrls: ['./admin-menu.component.css']
})
export class AdminMenuComponent {
  constructor(private authService:AuthService) { }

  ngOnInit(): void {
  }

  public logout() {
    this.authService.logout().subscribe(answer =>{
      if(answer){
        localStorage.clear();
      }
    });
    // localStorage.clear();

  }
}
