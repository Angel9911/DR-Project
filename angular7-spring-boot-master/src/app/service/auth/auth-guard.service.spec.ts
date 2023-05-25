import { TestBed } from '@angular/core/testing';

import { AuthGuardService } from './auth-guard.service';

describe('AuthGuardService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AuthGuardService = TestBed.get(AuthGuardService);
    cy.wrap(service).should('be.true');
    //expect(service).toBeTruthy();
  });
});
