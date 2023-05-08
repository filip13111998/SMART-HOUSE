import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerMessageComponent } from './owner-message.component';

describe('OwnerMessageComponent', () => {
  let component: OwnerMessageComponent;
  let fixture: ComponentFixture<OwnerMessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwnerMessageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnerMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
