import { TestBed } from '@angular/core/testing';

import { AlertServiceService } from './alert-service.service';

describe('AlertServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AlertServiceService = TestBed.get(AlertServiceService);
    cy.wrap(service).should('be.true');
    //expect(service).toBeTruthy();
  });
});
