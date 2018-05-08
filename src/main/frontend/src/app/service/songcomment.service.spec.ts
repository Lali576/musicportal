import { TestBed, inject } from '@angular/core/testing';

import { SongcommentService } from './songcomment.service';

describe('SongcommentService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SongcommentService]
    });
  });

  it('should be created', inject([SongcommentService], (service: SongcommentService) => {
    expect(service).toBeTruthy();
  }));
});
