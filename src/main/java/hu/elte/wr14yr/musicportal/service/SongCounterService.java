package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.repository.SongCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongCounterService {

    @Autowired
    private SongCommentRepository songCommentRepository;
}
