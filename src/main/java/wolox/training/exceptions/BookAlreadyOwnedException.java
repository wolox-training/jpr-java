package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "book already owned")
public class BookAlreadyOwnedException extends Exception{
    public BookAlreadyOwnedException(String message) {
        super(message);
    }
}
