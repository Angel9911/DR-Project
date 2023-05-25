import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderLoginAdministratorComponent } from './header-login-administrator.component';

describe('HeaderLoginAdministratorComponent', () => {
  let component: HeaderLoginAdministratorComponent;
  let fixture: ComponentFixture<HeaderLoginAdministratorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeaderLoginAdministratorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderLoginAdministratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    cy.wrap(component).should('be.true');
    //expect(component).toBeTruthy();
  });
});
