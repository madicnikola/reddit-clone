package fon.njt.redditclone.controller;

import fon.njt.redditclone.dto.RegisterRequest;
import fon.njt.redditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signUp(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                OK);
    }

    @GetMapping("/signup")
    public String signUpTest() {
        return "It's working!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }

    @GetMapping("/test")
    public String testBuild() {
        return "Build is working";
    }
}
