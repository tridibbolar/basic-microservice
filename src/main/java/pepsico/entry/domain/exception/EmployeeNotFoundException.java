package pepsico.entry.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException(String name) {
        super("No such employee: " + name);
    }
    public EmployeeNotFoundException() {
        super("No active employee");
    }
}
