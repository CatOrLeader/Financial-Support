package innohackatons.api;

import innohackatons.api.exception.ConflictException;
import innohackatons.api.exception.NotFoundEntityException;
import innohackatons.api.model.APIErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(value = NotFoundEntityException.class)
    public ResponseEntity<APIErrorResponse> handleNotFoundException(NotFoundEntityException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new APIErrorResponse(exception.getStatusCode(), exception));
    }

    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<APIErrorResponse> handleConflictException(ConflictException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new APIErrorResponse(exception.getStatusCode(), exception));
    }
}
