import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-menu',
  templateUrl: './admin-menu.component.html',
  styleUrls: ['./admin-menu.component.css']
})
export class AdminMenuComponent {
  constructor() { }

  ngOnInit(): void {
  }

  public logout() {
    localStorage.clear();
  }
}
