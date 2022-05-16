package acc.spring.secemail.Service;

import acc.spring.secemail.Configuration.security.EmailValidator;
import acc.spring.secemail.Configuration.security.UserServiceSecurity;
import acc.spring.secemail.DTO.SignUpReq;
import acc.spring.secemail.Model.UserApp;
import acc.spring.secemail.Model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserServiceSecurity userServiceSecurity;
    private EmailValidator emailValidator;

    public String register(SignUpReq signUpReq){
        boolean isValid = emailValidator.test(signUpReq.getEmail());
        if(!isValid){
            throw new IllegalStateException("email is no valid");
        }
        return userServiceSecurity.signUpUser(
            UserApp.builder()
                .firstname(signUpReq.getFirstName())
                .lastname(signUpReq.getLastName())
                .email(signUpReq.getEmail())
                .userole(UserRole.USER)
                .password(signUpReq.getPassword())
                .locked(false)
                .enabled(false)
                .build()
        );
    }
}
