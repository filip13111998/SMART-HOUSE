import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCsrComponent } from './admin-csr.component';

describe('AdminCsrComponent', () => {
  let component: AdminCsrComponent;
  let fixture: ComponentFixture<AdminCsrComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminCsrComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminCsrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
