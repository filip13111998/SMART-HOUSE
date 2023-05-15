import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminConfigTableComponent } from './admin-config-table.component';

describe('AdminConfigTableComponent', () => {
  let component: AdminConfigTableComponent;
  let fixture: ComponentFixture<AdminConfigTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminConfigTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminConfigTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
