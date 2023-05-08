import { TestBed } from '@angular/core/testing';

import { SharedMessageService } from './shared-message.service';

describe('SharedMessageService', () => {
  let service: SharedMessageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SharedMessageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
