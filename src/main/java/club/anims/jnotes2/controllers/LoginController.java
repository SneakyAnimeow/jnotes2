package club.anims.jnotes2.controllers;

import club.anims.jnotes2.requests.LoginRequest;
import club.anims.jnotes2.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody String body) {
        var loginRequest = new LoginRequest(body);
        loginService.login(loginRequest.getUsername(), loginRequest.getPassword());

        return "OK";
    }

    @PostMapping("/logout")
    public String logout() {
        loginService.logout();

        return "OK";
    }

    @PostMapping("/register")
    public String register(@RequestBody String body) {
        var loginRequest = new LoginRequest(body);
        loginService.register(loginRequest.getUsername(), loginRequest.getPassword());

        return "OK";
    }
}
