import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListCouriersComponent } from './list-couriers.component';

describe('ListCouriersComponent', () => {
  let component: ListCouriersComponent;
  let fixture: ComponentFixture<ListCouriersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListCouriersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListCouriersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    cy.wrap(component).should('be.true');
    // expect(component).toBeTruthy();
  });
});
