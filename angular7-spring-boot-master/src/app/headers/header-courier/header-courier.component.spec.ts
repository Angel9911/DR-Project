import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderCourierComponent } from './header-courier.component';

describe('HeaderCourierComponent', () => {
  let component: HeaderCourierComponent;
  let fixture: ComponentFixture<HeaderCourierComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeaderCourierComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderCourierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
