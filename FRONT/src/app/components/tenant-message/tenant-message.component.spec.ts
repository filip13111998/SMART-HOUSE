import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TenantMessageComponent } from './tenant-message.component';

describe('TenantMessageComponent', () => {
  let component: TenantMessageComponent;
  let fixture: ComponentFixture<TenantMessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TenantMessageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TenantMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
