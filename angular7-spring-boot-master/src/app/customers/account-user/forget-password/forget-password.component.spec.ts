import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ForgetPasswordComponent } from './forget-password.component';

describe('ForgetPasswordComponent', () => {
  let component: ForgetPasswordComponent;
  let fixture: ComponentFixture<ForgetPasswordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForgetPasswordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ForgetPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    cy.wrap(component).should('be.true');
    // expect(component).toBeTruthy();
=======
    expect(component).toBeTruthy();
>>>>>>> 96e6108617b064c9890a2c56099c02c04753ae58
=======
    expect(component).toBeTruthy();
>>>>>>> 96e6108617b064c9890a2c56099c02c04753ae58
=======
    expect(component).toBeTruthy();
>>>>>>> 96e6108617b064c9890a2c56099c02c04753ae58
  });
});
