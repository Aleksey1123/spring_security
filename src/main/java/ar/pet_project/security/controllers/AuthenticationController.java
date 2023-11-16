package ar.pet_project.security.controllers;

import ar.pet_project.security.models.AppUser;
import ar.pet_project.security.models.LoginDTO;
import ar.pet_project.security.models.RegistrationDTO;
import ar.pet_project.security.models.LoginResponseDTO;
import ar.pet_project.security.services.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public String registerControllerPost(@ModelAttribute("user") RegistrationDTO registrationDTO) {

        if (authenticationService.registerUser(registrationDTO)) {
            return "redirect:register?success";
        }
        else
            return "redirect:register?error";
    }
    @GetMapping("/register")
    public String registerControllerGet(Model model) {
        model.addAttribute("user", new RegistrationDTO());
        return "register";
    }

    @PostMapping("/login")
    public String loginController(@ModelAttribute("user") LoginDTO request,
                                  HttpServletResponse response) {

        LoginResponseDTO responseDTO = authenticationService.loginUser(request);
        if (!responseDTO.getJwt().equals("")) {
            Cookie cookie = new Cookie("jwtToken", responseDTO.getJwt());
            cookie.setMaxAge(86400);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:/user/";
        }
        else {
            return "redirect:login?error";
        }
    }

    @GetMapping("/login")
    public String loginControllerGet(Model model) {
        model.addAttribute("user", new LoginDTO());
        return "login";
    }


}
