import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerDeviceComponent } from './owner-device.component';

describe('OwnerDeviceComponent', () => {
  let component: OwnerDeviceComponent;
  let fixture: ComponentFixture<OwnerDeviceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwnerDeviceComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnerDeviceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
