import { TestBed } from '@angular/core/testing';

import { ChatbotServiceService } from './chatbot-service.service';

describe('ChatbotServiceService', () => {
  let service: ChatbotServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChatbotServiceService);
  });

  it('should be created', () => {
    cy.wrap(service).should('be.true');
    //expect(service).toBeTruthy();
  });
});
