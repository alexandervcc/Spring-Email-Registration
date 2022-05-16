package acc.spring.secemail.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import acc.spring.secemail.Repository.IUserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceSecurity implements UserDetailsService{

    private final String USER_NOT_FOUND = "user with email %s not found";
    private final IUserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        return userRepository.findByEmail(email).orElseThrow(
            ()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email))
        );
    }

}
