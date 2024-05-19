package innohackatons.api;

import innohackatons.api.exception.ConflictException;
import innohackatons.api.exception.NotFoundEntityException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

public class APIExceptionHandlerTest {
    private static final APIExceptionHandler handler = new APIExceptionHandler();

    @Test
    void givenException_whenNotFound_thenCorrectReturn() {
        var response = handler.handleNotFoundException(new NotFoundEntityException("Message"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(Objects.requireNonNull(response.getBody()).exceptionMessage()).isEqualTo("Message");
    }

    @Test
    void givenException_whenConflict_thenCorrectReturn() {
        var response = handler.handleConflictException(new ConflictException("Message"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(Objects.requireNonNull(response.getBody()).exceptionMessage()).isEqualTo("Message");
    }
}
