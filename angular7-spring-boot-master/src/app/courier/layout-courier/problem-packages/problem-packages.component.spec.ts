import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProblemPackagesComponent } from './problem-packages.component';

describe('ProblemPackagesComponent', () => {
  let component: ProblemPackagesComponent;
  let fixture: ComponentFixture<ProblemPackagesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProblemPackagesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProblemPackagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    cy.wrap(component).should('be.true');
    //expect(component).toBeTruthy();
  });
});
