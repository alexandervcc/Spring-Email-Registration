package acc.spring.secemail.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpReq {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
}
