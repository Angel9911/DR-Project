import { TestBed } from '@angular/core/testing';

import { ConfirmDialogService } from './confirm-dialog.service';

describe('ConfirmDialogService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ConfirmDialogService = TestBed.get(ConfirmDialogService);
    cy.wrap(service).should('be.true');
    //expect(service).toBeTruthy();
  });
});
