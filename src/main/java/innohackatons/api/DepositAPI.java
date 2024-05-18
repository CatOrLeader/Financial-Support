package innohackatons.api;

import innohackatons.api.model.APIErrorResponse;
import innohackatons.api.model.GetAllDepositsInfoResponse;
import innohackatons.api.model.GetDepositInfoResponse;
import innohackatons.api.model.PostNewDepositResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(("/deposit"))
@Validated
public interface DepositAPI {
    @Operation(summary = "Register new deposit for certain user for known bank")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Deposit created successfully",
                    content = @Content(schema = @Schema(implementation = PostNewDepositResponse.class))),

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
                    description = "Entity with such id is already exists",
                    content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers = {"Authorization"}
    )
    ResponseEntity<PostNewDepositResponse> createNewDeposit(
            @RequestParam @Min(0) long userId,
            @RequestParam @Min(0) long bankId
    );

    @Operation(summary = "Get deposit by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Deposit retrieved successfully",
                    content = @Content(schema = @Schema(implementation = GetDepositInfoResponse.class))),

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
    ResponseEntity<GetDepositInfoResponse> getDeposit(
            @RequestParam @Min(0) long depositId
    );

    @Operation(summary = "Get all deposits by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Deposits retrieved successfully",
                    content = @Content(schema = @Schema(implementation = GetAllDepositsInfoResponse.class))),

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
            headers = {"Authorization"}
    )
    ResponseEntity<GetAllDepositsInfoResponse> getAllDepositsByUserId(
            @RequestParam @Min(0) long userId
    );
}
