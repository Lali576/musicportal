import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SongCommentComponent } from './song-comment.component';

describe('SongCommentComponent', () => {
  let component: SongCommentComponent;
  let fixture: ComponentFixture<SongCommentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SongCommentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SongCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
