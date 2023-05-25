import { TestBed } from '@angular/core/testing';

import { HttpCourierService } from './http-courier.service';

describe('HttpCourierService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HttpCourierService = TestBed.get(HttpCourierService);
    cy.wrap(service).should('be.true');
    // expect(service).toBeTruthy();
  });
});
