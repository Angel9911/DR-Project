import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliveredPackagesComponent } from './delivered-packages.component';

describe('DeliveredPackagesComponent', () => {
  let component: DeliveredPackagesComponent;
  let fixture: ComponentFixture<DeliveredPackagesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeliveredPackagesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeliveredPackagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
