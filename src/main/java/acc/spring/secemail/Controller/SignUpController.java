package acc.spring.secemail.Controller;

import acc.spring.secemail.Service.RegistrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import acc.spring.secemail.DTO.SignUpReq;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/signup")
@AllArgsConstructor
public class SignUpController {

    private final RegistrationService registrationService;

    @PostMapping("/")
    public String postSignup(@RequestBody SignUpReq signupreq){
        return registrationService.register(signupreq);
    }
}
