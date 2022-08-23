import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderLoginCourierComponent } from './header-login-courier.component';

describe('HeaderLoginCourierComponent', () => {
  let component: HeaderLoginCourierComponent;
  let fixture: ComponentFixture<HeaderLoginCourierComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeaderLoginCourierComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderLoginCourierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
