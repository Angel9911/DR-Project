import {ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCourierComponent } from './add-courier.component';
import {async} from 'rxjs';

describe('AddCourierComponent', () => {
  let component: AddCourierComponent;
  let fixture: ComponentFixture<AddCourierComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddCourierComponent ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(AddCourierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    cy.wrap(component).should('be.true');
    //expect(component).toBeTruthy();
  });
});
