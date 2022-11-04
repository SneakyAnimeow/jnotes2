package club.anims.jnotes2.services;

import club.anims.jnotes2.data.model.User;
import club.anims.jnotes2.data.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class LoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private BcryptService bcryptService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    public LoginService() {
    }

    public void login(String username, String password) throws RuntimeException {
        var user = userRepository.findByName(username);

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        if (!bcryptService.checkPassword(password, user.get().getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        var token = UUID.randomUUID().toString();
        user.get().setToken(token);
        user.get().setTokenValidationTimeout(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2 + "");
        userRepository.save(user.get());

        var cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 2);
        response.addCookie(cookie);

        LOGGER.info("User " + username + " logged in");
    }

    public void logout() {
        var token = request.getCookies() != null ? request.getCookies()[0].getValue() : "NO_TOKEN";
        var user = userRepository.findByToken(token);

        if (user.isEmpty()) {
            throw new RuntimeException("Invalid token");
        }

        user.get().setToken(null);
        user.get().setTokenValidationTimeout(null);
        userRepository.save(user.get());

        var cookie = new Cookie("token", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        LOGGER.info("User " + user.get().getName() + " logged out");
    }

    public void validateToken() {
        var token = request.getCookies() != null ? request.getCookies()[0].getValue() : "NO_TOKEN";
        var user = userRepository.findByToken(token);

        if (user.isEmpty()) {
            throw new RuntimeException("Invalid token");
        }

        tokenValidation(user.get());
    }

    public void validateToken(String token) {
        var user = userRepository.findByToken(token);

        if (user.isEmpty()) {
            throw new RuntimeException("Invalid token");
        }

        tokenValidation(user.get());
    }

    public void validateToken(String token, String username) {
        var user = userRepository.findByToken(token);

        if (user.isEmpty()) {
            throw new RuntimeException("Invalid token");
        }

        if (!user.get().getName().equals(username)) {
            throw new RuntimeException("Invalid token");
        }

        tokenValidation(user.get());
    }

    public User validateTokenAndGetUser() {
        var token = request.getCookies() != null ? request.getCookies()[0].getValue() : "NO_TOKEN";
        var user = userRepository.findByToken(token);

        if (user.isEmpty()) {
            throw new RuntimeException("Invalid token");
        }

        return tokenValidation(user.get());
    }

    public void register(String username, String password) {
        var user = userRepository.findByName(username);

        if (user.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        var token = UUID.randomUUID().toString();
        var newUser = new User();
        newUser.setName(username);
        newUser.setPassword(bcryptService.hashPassword(password));
        newUser.setToken(token);
        newUser.setTokenValidationTimeout(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2 + "");
        userRepository.save(newUser);

        var cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 2);
        response.addCookie(cookie);

        LOGGER.info("User " + username + " registered");
    }

    private User tokenValidation(User user) {
        if (Long.parseLong(user.getTokenValidationTimeout() == null ? "0" : user.getTokenValidationTimeout()) < System.currentTimeMillis()) {
            throw new RuntimeException("Token expired");
        }

        user.setTokenValidationTimeout(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2 + "");
        return userRepository.save(user);
    }
}
