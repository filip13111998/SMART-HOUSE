import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-tenant-menu',
  templateUrl: './tenant-menu.component.html',
  styleUrls: ['./tenant-menu.component.css']
})
export class TenantMenuComponent {
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
