package innohackatons.api;

import innohackatons.api.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping(("/piggy-bank"))
public interface PiggyBankAPI {
    @Operation(summary = "Get all piggy banks by certain user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Returned piggy banks successfully",
                    content = @Content(schema = @Schema(implementation = GetAllPiggyBanksByUserResponse.class))),

            @ApiResponse(responseCode = "400",
                    description = "Request was malformed",
                    content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),

            @ApiResponse(responseCode = "403",
                    description = "Incorrect access token provided",
                    content = @Content()),

            @ApiResponse(responseCode = "404",
                    description = "Entity with such id is not found",
                    content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @GetMapping(
            path = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers = {"Authorization"})
    ResponseEntity<GetAllPiggyBanksByUserResponse> getAllPiggyBanksByUser(@RequestParam @Min(0) long userId);

    @Operation(summary = "Get piggy bank by certain user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Returned piggy banks successfully",
                    content = @Content(schema = @Schema(implementation = GetPiggyBankInfoResponse.class))),

            @ApiResponse(responseCode = "400",
                    description = "Request was malformed",
                    content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),

            @ApiResponse(responseCode = "403",
                    description = "Incorrect access token provided",
                    content = @Content()),

            @ApiResponse(responseCode = "404",
                    description = "Entity with such id is not found",
                    content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers = {"Authorization"}
    )
    ResponseEntity<GetPiggyBankInfoResponse> getPiggyBank(
            @RequestParam @Min(0) long userId,
            @RequestParam @NotBlank String goal
    );

    @Operation(summary = "Transfer money to the piggy bank from internal deposit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Transaction proceeded successfully",
                    content = @Content(schema = @Schema(implementation = InternalPiggyBankResponse.class))),

            @ApiResponse(responseCode = "400",
                    description = "Request was malformed",
                    content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),

            @ApiResponse(responseCode = "403",
                    description = "Incorrect access token provided",
                    content = @Content()),

            @ApiResponse(responseCode = "404",
                    description = "Entity with such id is not found",
                    content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @PostMapping(
            path = "/internal/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = {"Authorization"})
    ResponseEntity<InternalPiggyBankResponse> internalAddTransactionProcess(
            @RequestParam long userId, @RequestBody @Valid PostInternalPiggyBankRequest request
    );

    @Operation(summary = "Transfer money from the piggy bank to the certain deposit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Transaction proceeded successfully",
                    content = @Content(schema = @Schema(implementation = InternalPiggyBankResponse.class))),

            @ApiResponse(responseCode = "400",
                    description = "Request was malformed",
                    content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),

            @ApiResponse(responseCode = "403",
                    description = "Incorrect access token provided",
                    content = @Content()),

            @ApiResponse(responseCode = "404",
                    description = "Entity with such id is not found",
                    content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),

            @ApiResponse(responseCode = "409",
                    description = "There is no money of such amount on the piggy bank",
                    content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @PostMapping(
            path = "/internal/retrieve",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = {"Authorization"})
    ResponseEntity<InternalPiggyBankResponse> internalRetrieveTransactionProcess(
            @RequestParam @Min(0) long userId, @RequestBody @Valid PostInternalPiggyBankRequest request
    );

    @Operation(summary = "Delete piggy bank and transfer all money to the certain deposit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Piggy Bank deleted successfully",
                    content = @Content(schema = @Schema(implementation = DeletePiggyBankResponse.class))),

            @ApiResponse(responseCode = "400",
                    description = "Request was malformed",
                    content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),

            @ApiResponse(responseCode = "403",
                    description = "Incorrect access token provided",
                    content = @Content()),

            @ApiResponse(responseCode = "404",
                    description = "Entity with such id is not found",
                    content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers = {"Authorization"}
    )
    ResponseEntity<DeletePiggyBankRequest> deletePiggyBank(
            @RequestParam @Min(0) long userId, @RequestBody @Valid DeletePiggyBankRequest request
    );
}
