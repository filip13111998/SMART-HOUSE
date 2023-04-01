import { TestBed } from '@angular/core/testing';

import { RegisterCsrService } from './register-csr.service';

describe('RegisterCsrService', () => {
  let service: RegisterCsrService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegisterCsrService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
