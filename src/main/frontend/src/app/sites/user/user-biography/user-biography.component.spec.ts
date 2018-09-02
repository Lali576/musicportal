import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserBiographyComponent } from './user-biography.component';

describe('UserBiographyComponent', () => {
  let component: UserBiographyComponent;
  let fixture: ComponentFixture<UserBiographyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserBiographyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserBiographyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
