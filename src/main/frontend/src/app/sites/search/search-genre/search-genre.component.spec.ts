import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchGenreComponent } from './search-genre.component';

describe('SearchGenreComponent', () => {
  let component: SearchGenreComponent;
  let fixture: ComponentFixture<SearchGenreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchGenreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchGenreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
