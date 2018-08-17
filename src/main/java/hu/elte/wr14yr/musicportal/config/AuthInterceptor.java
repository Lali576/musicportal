package hu.elte.wr14yr.musicportal.config;

import hu.elte.wr14yr.musicportal.annotation.Role;
import hu.elte.wr14yr.musicportal.model.User;
import hu.elte.wr14yr.musicportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(AuthInterceptor.class.getName());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.log(Level.INFO, "Authentication interceptor triggered");

        List<User.Role> routeRoles = getRoles((HandlerMethod) handler);
        User user = (userService.isLoggedIn()) ? userService.getLoggedInUser() : null;

        if(routeRoles.isEmpty() || routeRoles.contains(User.Role.GUEST)) {
            logger.log(Level.INFO, "Access to current route is free");
            return true;
        }

        if(userService.isLoggedIn() && routeRoles.contains(user.getRole())) {
            logger.log(Level.INFO, "Access to current route is granted");
            return true;
        }

        response.setStatus(401);

        return false;
    }

    private List<User.Role> getRoles(HandlerMethod handler) {
        Role role = handler.getMethodAnnotation(Role.class);
        return role == null ? Collections.emptyList() : Arrays.asList(role.value());
    }
}
