import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetPasswordComponent } from './reset-password.component';

describe('ResetPasswordComponent', () => {
  let component: ResetPasswordComponent;
  let fixture: ComponentFixture<ResetPasswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResetPasswordComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResetPasswordComponent);
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
