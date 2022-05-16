package acc.spring.secemail.Service;

import acc.spring.secemail.Configuration.security.EmailValidator;
import acc.spring.secemail.Configuration.security.UserServiceSecurity;
import acc.spring.secemail.DTO.SignUpReq;
import acc.spring.secemail.Model.ConfirmationToken;
import acc.spring.secemail.Model.UserApp;
import acc.spring.secemail.Model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserServiceSecurity userServiceSecurity;
    private final ConfirmationTokenService confirmationTokenService;
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


    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
            .getToken(token)
            .orElseThrow(() ->
                new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);

        userServiceSecurity.enableAppUser(confirmationToken.getUserApp().getEmail());
        return "confirmed";
    }


}
