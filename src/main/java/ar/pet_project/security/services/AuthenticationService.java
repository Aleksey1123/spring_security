package ar.pet_project.security.services;

import ar.pet_project.security.models.*;
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


     public Boolean registerUser(RegistrationDTO user) {
//         String encodedPassword = passwordEncoder.encode(user.getPassword());
         try {
             Role userRole = roleRepository.findByAuthority("USER").get();

             Set<Role> authorities = new HashSet<>();
             authorities.add(userRole);

//         return userRepository.save(new AppUser(user.getUsername(), encodedPassword, authorities));

             userRepository.save(AppUser.builder()
                     .username(user.getUsername())
                     .password(passwordEncoder.encode(user.getPassword()))
                     .authorities(authorities)
                     .build());
             return true;
         }
         catch (Exception exception) {
             return false;
         }

     }

     public LoginResponseDTO loginUser(LoginDTO user) {

         try {
             Authentication authentication = authenticationManager.authenticate(
                     new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
             );

             String token = tokenService.generateJwt(authentication);

             return new LoginResponseDTO(userRepository.findByUsername(user.getEmail()).get(), token);
         }
         catch (AuthenticationException exception) {
             return new LoginResponseDTO(null, "");
         }
     }

}
