package pepsico.entry.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HCMNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public HCMNotFoundException(String role) {
        super("No such roles: " + role);
    }

    public HCMNotFoundException() {
        super("No such hcm data");
    }
}
