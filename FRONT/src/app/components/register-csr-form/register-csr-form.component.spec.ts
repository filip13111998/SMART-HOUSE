import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterCsrFormComponent } from './register-csr-form.component';

describe('RegisterCsrFormComponent', () => {
  let component: RegisterCsrFormComponent;
  let fixture: ComponentFixture<RegisterCsrFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterCsrFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterCsrFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
