import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderLayoutAdiministratorComponent } from './header-layout-adiministrator.component';

describe('HeaderLayoutAdiministratorComponent', () => {
  let component: HeaderLayoutAdiministratorComponent;
  let fixture: ComponentFixture<HeaderLayoutAdiministratorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeaderLayoutAdiministratorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderLayoutAdiministratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
