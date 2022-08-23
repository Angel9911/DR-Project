import { TestBed } from '@angular/core/testing';

import { HttpAdiministratorService } from './http-adiministrator.service';

describe('HttpAdiministratorService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HttpAdiministratorService = TestBed.get(HttpAdiministratorService);
    expect(service).toBeTruthy();
  });
});
