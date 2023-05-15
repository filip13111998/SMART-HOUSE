import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminLoggerComponent } from './admin-logger.component';

describe('AdminLoggerComponent', () => {
  let component: AdminLoggerComponent;
  let fixture: ComponentFixture<AdminLoggerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminLoggerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminLoggerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
