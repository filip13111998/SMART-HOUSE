import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-owner-menu',
  templateUrl: './owner-menu.component.html',
  styleUrls: ['./owner-menu.component.css']
})
export class OwnerMenuComponent {
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
