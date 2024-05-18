package innohackatons.api.exception;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {
    private final int statusCode = 404;

    public ConflictException(String message) {
        super(message);
    }
}
