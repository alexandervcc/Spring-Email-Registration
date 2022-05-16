package acc.spring.secemail.Configuration.security;

import acc.spring.secemail.Model.ConfirmationToken;
import acc.spring.secemail.Model.UserApp;
import acc.spring.secemail.Service.ConfirmationTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import acc.spring.secemail.Repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceSecurity implements UserDetailsService{

    private final String USER_NOT_FOUND = "user with email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        return userRepository.findByEmail(email).orElseThrow(
            ()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email))
        );
    }

    @Transactional
    public String signUpUser(UserApp userApp){
        //check if user exists
        boolean userExists = userRepository.findByEmail(userApp.getEmail()).isPresent();
        if(userExists){
            throw new IllegalStateException("user already exists");
        }
        String passwordEncoded = bCryptPasswordEncoder.encode(userApp.getPassword());
        userApp.setPassword(passwordEncoded);
        userRepository.save(userApp);

        String token = UUID.randomUUID().toString();

        ConfirmationToken conToken = ConfirmationToken.builder()
            .token(token)
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now().plusMinutes(15))
            .confirmedAt(null)
            .userApp(userApp)
            .build();

        confirmationTokenService.saveConfirmationToken(conToken);

        //TODO: send email

        return token;
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }
}
