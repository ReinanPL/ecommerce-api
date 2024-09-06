package com.compass.reinan.api_ecommerce.controller;

import com.compass.reinan.api_ecommerce.domain.dto.sale.response.SaleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Sales Reports", description = "Operations related to generating sales reports by user. Includes reports by specific date, by month, and by the current week, considering only business days.")
public interface SalesReportController {

    @Operation(
            summary = "Retrieve all sales by user for a given month and year",
            description = "Retrieve a report of sales by user for a given month. The month must be specified in the request, and the report will include all sales in that month. Only the CLIENT authenticated or the ADMIN can retrieve this resource.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(name = "cpf", description = "The CPF of the user whose sales are being retrieved.", required = true),
                    @Parameter(name = "month", description = "The month for which sales are being retrieved.", required = true, example = "9"),
                    @Parameter(name = "year", description = "The year for which sales are being retrieved.", required = true, example = "2024")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of sales for the specified month and year.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SaleResponse.class)),
                                    examples = @ExampleObject(value = "[{\"id\":1,\"date_sale\":\"2024-09-01T00:00:00Z\",\"user_cpf\":\"12345678900\",\"status\":\"PROCESSING\",\"items\":[{\"product_id\":1,\"quantity\":2,\"price\":50.00}],\"total_value\":100.00}]")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized. The user is not authenticated or the authentication credentials are missing/invalid.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Unauthorized access.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden. The authenticated user does not have permission to access this resource.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Access denied.\"}")
                            )
                    )
            }
    )
    ResponseEntity<List<SaleResponse>> getSalesByMonth(String cpf, int month, int year);

    @Operation(
            summary = "Retrieve sales by user for the current week",
            description = "Retrieve a report of sales by user for the current week, considering only business days (Monday through Friday). The week is determined based on the current date. Only the CLIENT authenticated or the ADMIN can retrieve this resource.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(name = "cpf", description = "The CPF of the user whose sales are being retrieved.", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of sales for the current week.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SaleResponse.class)),
                                    examples = @ExampleObject(value = "[{\"id\":1,\"date_sale\":\"2024-09-01T00:00:00Z\",\"user_cpf\":\"12345678900\",\"status\":\"PROCESSING\",\"items\":[{\"product_id\":1,\"quantity\":2,\"price\":50.00}],\"total_value\":100.00}]")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized. The user is not authenticated or the authentication credentials are missing/invalid.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Unauthorized access.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden. The authenticated user does not have permission to access this resource.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Access denied.\"}")
                            )
                    )
            }
    )
    ResponseEntity<List<SaleResponse>> getSalesByCurrentWeek(String cpf);

    @Operation(
            summary = "Retrieve sales by user for a specific date",
            description = "Retrieve a report of sales by user for a given date. The date must be specified in the request, and the report will include all sales on that date. Only the CLIENT authenticated or the ADMIN can retrieve this resource. ",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(name = "cpf", description = "The CPF of the user whose sales are being retrieved.", required = true),
                    @Parameter(name = "day", description = "The day of the month for which sales are being retrieved.", required = true, example = "2"),
                    @Parameter(name = "month", description = "The month for which sales are being retrieved.", required = true, example = "9"),
                    @Parameter(name = "year", description = "The year for which sales are being retrieved.", required = true, example = "2024")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of sales for the specified date.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "[{\"id\":1,\"date_sale\":\"2024-09-01T00:00:00Z\",\"user_cpf\":\"12345678900\",\"status\":\"PROCESSING\",\"items\":[{\"product_id\":1,\"quantity\":2,\"price\":50.00}],\"total_value\":100.00}]"),
                                    array = @ArraySchema(schema = @Schema(implementation = SaleResponse.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized. The user is not authenticated or the authentication credentials are missing/invalid.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Unauthorized access.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden. The authenticated user does not have permission to access this resource.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Access denied.\"}")
                            )
                    )
            }
    )
    ResponseEntity<List<SaleResponse>> getSalesByDate( String cpf, int day, int month, int year);
}
