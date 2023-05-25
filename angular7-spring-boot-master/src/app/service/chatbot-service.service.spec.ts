import { TestBed } from '@angular/core/testing';

import { ChatbotServiceService } from './chatbot-service.service';

describe('ChatbotServiceService', () => {
  let service: ChatbotServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChatbotServiceService);
  });

  it('should be created', () => {
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
