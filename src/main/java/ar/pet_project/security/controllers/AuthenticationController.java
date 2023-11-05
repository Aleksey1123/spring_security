package ar.pet_project.security.controllers;

import ar.pet_project.security.models.AppUser;
import ar.pet_project.security.models.AppUserDTO;
import ar.pet_project.security.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AppUser registerController(@RequestBody AppUserDTO user) {

        return authenticationService.registerUser(user);
    }
}
