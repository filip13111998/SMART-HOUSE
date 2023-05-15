import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerHouseComponent } from './owner-house.component';

describe('OwnerHouseComponent', () => {
  let component: OwnerHouseComponent;
  let fixture: ComponentFixture<OwnerHouseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwnerHouseComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnerHouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
