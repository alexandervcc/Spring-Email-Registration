package acc.spring.secemail.Configuration.security;

import acc.spring.secemail.Model.UserApp;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import acc.spring.secemail.Repository.IUserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceSecurity implements UserDetailsService{

    private final String USER_NOT_FOUND = "user with email %s not found";
    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        return userRepository.findByEmail(email).orElseThrow(
            ()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email))
        );
    }

    public String signUpUser(UserApp userApp){
        //check if user exists
        boolean userExists = userRepository.findByEmail(userApp.getEmail()).isPresent();
        if(userExists){
            throw new IllegalStateException("user already exists");
        }
        String passwordEncoded = bCryptPasswordEncoder.encode(userApp.getPassword());
        userApp.setPassword(passwordEncoded);
        userRepository.save(userApp);
        //TODO: sent token for confirmation

        return "it works";
    }
}
