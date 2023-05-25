import { TestBed } from '@angular/core/testing';

import { PaymentServiceService } from './payment-service.service';

describe('PaymentServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PaymentServiceService = TestBed.get(PaymentServiceService);
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    cy.wrap(service).should('be.true');
    //expect(service).toBeTruthy();
=======
    expect(service).toBeTruthy();
>>>>>>> 96e6108617b064c9890a2c56099c02c04753ae58
=======
    expect(service).toBeTruthy();
>>>>>>> 96e6108617b064c9890a2c56099c02c04753ae58
=======
    expect(service).toBeTruthy();
>>>>>>> 96e6108617b064c9890a2c56099c02c04753ae58
  });
});
