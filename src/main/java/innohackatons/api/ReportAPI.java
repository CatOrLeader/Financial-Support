package innohackatons.api;

import innohackatons.api.model.APIErrorResponse;
import innohackatons.api.model.GetCategoryReportRequest;
import innohackatons.api.model.GetCategoryReportResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(("/report"))
@Validated
public interface ReportAPI {
    @Operation(summary = "Get report for categoryId with certain date window")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Report created successfully",
                     content = @Content(schema = @Schema(implementation = GetCategoryReportResponse.class))),

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
        path = "/category",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE,
        headers = {"Authorization"}
    )
    ResponseEntity<GetCategoryReportResponse> getCategoryReport(
        @RequestParam @Min(0) long userId, @RequestBody @Valid GetCategoryReportRequest request
    );
}
