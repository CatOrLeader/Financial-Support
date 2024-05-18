package innohackatons.api;

import innohackatons.api.model.APIErrorResponse;
import innohackatons.api.model.PostTransactionRequest;
import innohackatons.api.model.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping(("/transaction"))
public interface TransactionAPI {
    @Operation(summary = "Process the transaction from external service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Transaction proceeded successfully",
                    content = @Content(schema = @Schema(implementation = TransactionResponse.class))
            ),

            @ApiResponse(responseCode = "400",
                    description = "Request was malformed",
                    content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),

            @ApiResponse(responseCode = "403",
                    description = "Incorrect access token provided",
                    content = @Content()),

            @ApiResponse(responseCode = "409",
                    description = "Not enough money on the deposit",
                    content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = {"Authorization"}
    )
    ResponseEntity<TransactionResponse> processTransaction(@RequestBody @Valid PostTransactionRequest transaction);
}
