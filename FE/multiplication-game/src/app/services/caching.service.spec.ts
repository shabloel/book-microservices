import { TestBed } from '@angular/core/testing';

import { CachingService } from './caching.service';

describe('CachingServiceService', () => {
  let service: CachingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CachingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
