package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.Album;
import hu.elte.wr14yr.musicportal.model.Playlist;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.model.UserMessage;
import hu.elte.wr14yr.musicportal.repository.UserMessageRepository;
import hu.elte.wr14yr.musicportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMessageRepository userMessageRepository;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private PlaylistService playlistService;

    private User user;

    public User register(User user, String password) {
        user.setRole(User.Role.USER);

        //crypto salt + sha256

        return this.user = userRepository.save(user);
    }

    public User login(User user) throws Exception {
         user = userRepository.findByUsername(user.getUsername());
         if(isValid(user)) {
             this.user = user;
             return this.user;
         }
         throw new Exception();
    }

    public boolean isValid(User user) {
        String username = user.getUsername();

        //cryptosalt + sha256 passworkd checking

        return false;
    }

    public boolean isLoggenIn() {
        return user != null;
    }

    public User getLoggedInUser() {
        return user;
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void logout() {
        user = null;
    }

    public void delete(long id, User user) {
        for(Album album : user.getAlbums()) {
            albumService.delete(album);
        }

        playlistService.deleteAllByUser(user);

        for(UserMessage userMessage : user.getUserFromMessages()) {
            userMessageRepository.deleteAllByUserFrom(user);
        }

        userRepository.deleteById(id);
    }
}
