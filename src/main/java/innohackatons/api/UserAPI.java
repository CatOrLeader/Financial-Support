package innohackatons.api;

import innohackatons.api.model.APIErrorResponse;
import innohackatons.api.model.GetAllUsersInfoResponse;
import innohackatons.api.model.GetUserInfoResponse;
import innohackatons.api.model.UserRegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(("/user"))
@Validated
public interface UserAPI {
    @Operation(summary = "Register new user by his name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "User registered successfully",
                     content = @Content(schema = @Schema(implementation = UserRegisterResponse.class))),

        @ApiResponse(responseCode = "400",
                     description = "Request was malformed",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),

        @ApiResponse(responseCode = "403",
                     description = "Incorrect access token provided",
                     content = @Content()),

        @ApiResponse(responseCode = "409",
                     description = "User with such name is already registered",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @GetMapping(
        path = "/register",
        produces = MediaType.APPLICATION_JSON_VALUE,
        headers = {"Authorization"}
    )
    ResponseEntity<UserRegisterResponse> registerUser(@RequestParam @NotBlank String name);

    @Operation(summary = "Get user by his id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "User retrieved successfully",
                     content = @Content(schema = @Schema(implementation = GetUserInfoResponse.class))),

        @ApiResponse(responseCode = "400",
                     description = "Request was malformed",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),

        @ApiResponse(responseCode = "403",
                     description = "Incorrect access token provided",
                     content = @Content()),

        @ApiResponse(responseCode = "404",
                     description = "User with such id is not found",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        headers = {"Authorization"}
    )
    ResponseEntity<GetUserInfoResponse> getUser(@RequestParam @Min(0) long userId);

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Users retrieved successfully",
                     content = @Content(schema = @Schema(implementation = GetAllUsersInfoResponse.class))),

        @ApiResponse(responseCode = "400",
                     description = "Request was malformed",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),

        @ApiResponse(responseCode = "403",
                     description = "Incorrect access token provided",
                     content = @Content())
    })
    @GetMapping(
        path = "/all",
        produces = MediaType.APPLICATION_JSON_VALUE,
        headers = {"Authorization"}
    )
    ResponseEntity<GetAllUsersInfoResponse> getUser();

    @Operation(summary = "Register new user by his id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "User unregistered successfully"),

        @ApiResponse(responseCode = "400",
                     description = "Request was malformed",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),

        @ApiResponse(responseCode = "403",
                     description = "Incorrect access token provided",
                     content = @Content()),

        @ApiResponse(responseCode = "404",
                     description = "User with such name is not registered yet",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @DeleteMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        headers = {"Authorization"}
    )
    ResponseEntity<Object> deleteUser(@RequestParam @Min(0) long userId);
}
