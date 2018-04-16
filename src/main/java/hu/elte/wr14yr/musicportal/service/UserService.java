package hu.elte.wr14yr.musicportal.service;

import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private User user;

    public User register(User user) {
        user.setRole(User.Role.USER);
        return this.user = userRepository.save(user);
    }

    public User login(User user) throws Exception {
        if(isValid(user)) {
            //return this.user = userRepository.findByUsername(user.getUsername());
        }
        throw new Exception();
    }

    public boolean isValid(User user) {
        String username = user.getUsername();
        //String password = user.getPassword();
        //return userRepository.findByUsernameAndPassword(username, password).isPresent();
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

    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
