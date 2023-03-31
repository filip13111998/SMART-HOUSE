import { Component } from '@angular/core';

@Component({
  selector: 'app-owner-menu',
  templateUrl: './owner-menu.component.html',
  styleUrls: ['./owner-menu.component.css']
})
export class OwnerMenuComponent {
  constructor() { }

  ngOnInit(): void {
  }

  public logout() {
    localStorage.clear();
  }
}
