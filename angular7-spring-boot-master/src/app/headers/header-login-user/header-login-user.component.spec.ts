import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderLoginUserComponent } from './header-login-user.component';

describe('HeaderLoginUserComponent', () => {
  let component: HeaderLoginUserComponent;
  let fixture: ComponentFixture<HeaderLoginUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeaderLoginUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderLoginUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    cy.wrap(component).should('be.true');
    //expect(component).toBeTruthy();
  });
});
