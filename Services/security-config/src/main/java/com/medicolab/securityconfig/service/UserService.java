package com.medicolab.securityconfig.service;

import com.medicolab.securityconfig.model.User;
import com.medicolab.securityconfig.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserDetailsService {


   private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void createTemporaryUser() {
        String tempPassword = UUID.randomUUID().toString().substring(0, 8); // Génère un mot de passe aléatoire
        System.out.println("🔑 Mot de passe User temporaire : " + tempPassword); // Affiche le mot de passe dans la console

        com.medicolab.securityconfig.model.User tempUser = new User();
        tempUser.setUsername("tempuser");
        tempUser.setPassword(passwordEncoder.encode(tempPassword));


        userRepository.findByUsername("tempuser").ifPresent(userRepository::delete);

        userRepository.save(tempUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
