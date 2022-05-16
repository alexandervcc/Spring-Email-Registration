package acc.spring.secemail.Repository;

import acc.spring.secemail.DTO.SignUpReq;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    public String register(SignUpReq signUpReq){
        return "working";
    }
}
