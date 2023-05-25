import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatbotComponent } from './chatbot.component';

describe('ChatbotComponent', () => {
  let component: ChatbotComponent;
  let fixture: ComponentFixture<ChatbotComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChatbotComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChatbotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
<<<<<<< HEAD
<<<<<<< HEAD
    cy.wrap(component).should('be.true');
    //expect(component).toBeTruthy();
=======
    expect(component).toBeTruthy();
>>>>>>> 96e6108617b064c9890a2c56099c02c04753ae58
=======
    expect(component).toBeTruthy();
>>>>>>> 96e6108617b064c9890a2c56099c02c04753ae58
  });
});
