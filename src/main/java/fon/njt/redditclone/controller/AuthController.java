package fon.njt.redditclone.controller;

import fon.njt.redditclone.dto.AuthenticationResponse;
import fon.njt.redditclone.dto.LoginRequest;
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


    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }


//    @GetMapping("/signup")
//    public String signUpTest() {
//        return "It's working!";
//    }
//
//    @GetMapping("/hello")
//    public String hello() {
//        return "Hello!";
//    }
//
//    @GetMapping("/test")
//    public String testBuild() {
//        return "Build is working";
//    }
}
