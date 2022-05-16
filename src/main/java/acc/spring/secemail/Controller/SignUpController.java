package acc.spring.secemail.Controller;

import acc.spring.secemail.Service.RegistrationService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/confirm")
    public String getConfirmUser(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }
}
