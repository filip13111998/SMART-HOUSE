import { Component } from '@angular/core';

@Component({
  selector: 'app-tenant-menu',
  templateUrl: './tenant-menu.component.html',
  styleUrls: ['./tenant-menu.component.css']
})
export class TenantMenuComponent {
  constructor() { }

  ngOnInit(): void {
  }

  public logout() {
    localStorage.clear();
  }
}
