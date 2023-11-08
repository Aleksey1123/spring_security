package ar.pet_project.security.controllers;

import ar.pet_project.security.models.AppUser;
import ar.pet_project.security.models.RegistrationDTO;
import ar.pet_project.security.models.LoginResponseDTO;
import ar.pet_project.security.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AppUser registerController(@RequestBody RegistrationDTO user) {

        return authenticationService.registerUser(user);
    }

    @PostMapping("/login")
    public LoginResponseDTO loginController(@RequestBody RegistrationDTO user) {

        return authenticationService.loginUser(user.getUsername(), user.getPassword());
    }
}
