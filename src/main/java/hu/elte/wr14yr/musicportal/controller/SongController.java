package hu.elte.wr14yr.musicportal.controller;

import hu.elte.wr14yr.musicportal.service.SongCommentService;
import hu.elte.wr14yr.musicportal.service.SongCounterService;
import hu.elte.wr14yr.musicportal.service.SongLikeService;
import hu.elte.wr14yr.musicportal.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/song")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private SongLikeService songLikeService;

    @Autowired
    private SongCommentService songCommentService;

    @Autowired
    private SongCounterService songCounterService;
}
