package ar.pet_project.security.services;

import ar.pet_project.security.models.AppUser;
import ar.pet_project.security.models.RegistrationDTO;
import ar.pet_project.security.models.LoginResponseDTO;
import ar.pet_project.security.models.Role;
import ar.pet_project.security.repositories.RoleRepository;
import ar.pet_project.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


     public AppUser registerUser(RegistrationDTO user) {
         System.out.println("In authService");

//         String encodedPassword = passwordEncoder.encode(user.getPassword());
         Role userRole = roleRepository.findByAuthority("USER").get();

         Set<Role> authorities = new HashSet<>();
         authorities.add(userRole);

//         return userRepository.save(new AppUser(user.getUsername(), encodedPassword, authorities));

         return userRepository.save(AppUser.builder()
                 .username(user.getUsername())
                 .password(passwordEncoder.encode(user.getPassword()))
                 .authorities(authorities)
                 .build());
     }

     public LoginResponseDTO loginUser(String username, String password) {

         try {
             Authentication authentication = authenticationManager.authenticate(
                     new UsernamePasswordAuthenticationToken(username, password)
             );

             String token = tokenService.generateJwt(authentication);

             return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);
         }
         catch (AuthenticationException exception) {
             return new LoginResponseDTO(null, "");
         }
     }

}
