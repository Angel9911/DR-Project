import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderLayoutUserComponent } from './header-layout-user.component';

describe('HeaderLayoutUserComponent', () => {
  let component: HeaderLayoutUserComponent;
  let fixture: ComponentFixture<HeaderLayoutUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeaderLayoutUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderLayoutUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    cy.wrap(component).should('be.true');
    //expect(component).toBeTruthy();
  });
});
