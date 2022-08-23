import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginAdministratorComponent } from './login-administrator.component';

describe('LoginAdministratorComponent', () => {
  let component: LoginAdministratorComponent;
  let fixture: ComponentFixture<LoginAdministratorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginAdministratorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginAdministratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
