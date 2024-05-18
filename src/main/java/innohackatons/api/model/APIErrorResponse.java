package innohackatons.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record APIErrorResponse(
    String description,
    String code,
    String exceptionName,
    String exceptionMessage,
    List<String> stacktrace
) {
    private static final int MAX_STACK_DEPTH = 7;

    public APIErrorResponse(@NotNull HttpStatus status, @NotNull Exception exception) {
        this(
            status.getReasonPhrase(),
            String.valueOf(status.value()),
            exception.getClass().getName(),
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList().subList(
                0, Math.max(exception.getStackTrace().length, MAX_STACK_DEPTH)
            )
        );
    }
}
