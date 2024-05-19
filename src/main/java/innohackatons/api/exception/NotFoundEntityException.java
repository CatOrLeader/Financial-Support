package innohackatons.api.exception;

import lombok.Getter;

@Getter
public class NotFoundEntityException extends RuntimeException {
    private final int statusCode = 404;

    public NotFoundEntityException(String message) {
        super(message);
    }
}
