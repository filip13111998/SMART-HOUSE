import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminConfigFormComponent } from './admin-config-form.component';

describe('AdminConfigFormComponent', () => {
  let component: AdminConfigFormComponent;
  let fixture: ComponentFixture<AdminConfigFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminConfigFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminConfigFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
