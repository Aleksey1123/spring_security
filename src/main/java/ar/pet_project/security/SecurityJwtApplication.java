package ar.pet_project.security;

import ar.pet_project.security.models.AppUser;
import ar.pet_project.security.models.Role;
import ar.pet_project.security.repositories.RoleRepository;
import ar.pet_project.security.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityJwtApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (roleRepository.findByAuthority("ADMIN").isPresent()) return;

            Role adminRole = roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

//            AppUser admin = new AppUser(1, "admin", encoder.encode("password"), roles);
            AppUser admin = AppUser.builder()
                    .userId(1)
                    .username("admin")
                    .password(encoder.encode("password"))
                    .authorities(roles)
                    .build();

            userRepository.save(admin);
        };
    }

}
