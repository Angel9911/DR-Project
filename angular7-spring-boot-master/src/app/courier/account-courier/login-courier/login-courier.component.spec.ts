import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginCourierComponent } from './login-courier.component';

describe('LoginCourierComponent', () => {
  let component: LoginCourierComponent;
  let fixture: ComponentFixture<LoginCourierComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginCourierComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginCourierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
