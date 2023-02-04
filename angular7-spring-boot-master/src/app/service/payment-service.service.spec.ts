import { TestBed } from '@angular/core/testing';

import { PaymentServiceService } from './payment-service.service';

describe('PaymentServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PaymentServiceService = TestBed.get(PaymentServiceService);
    expect(service).toBeTruthy();
  });
});
