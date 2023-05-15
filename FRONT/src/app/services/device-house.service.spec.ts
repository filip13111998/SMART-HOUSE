import { TestBed } from '@angular/core/testing';

import { DeviceHouseService } from './device-house.service';

describe('DeviceHouseService', () => {
  let service: DeviceHouseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeviceHouseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
