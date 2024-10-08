package com.compass.reinan.api_ecommerce.controller;

import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.CreateSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.response.SaleResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.UpdateItemSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.UpdatePatchItemSaleRequest;
import com.compass.reinan.api_ecommerce.exception.handler.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@Tag(name = "Sale", description = "Operations related to managing sales, including registration, deletion, updates, and retrieval.")
public interface SaleController {

    @Operation(
            summary = "Create a new sale",
            description = "Registers a new sale in the system. Only users with CLIENT or ADMIN roles can create a sale.",
            security = @SecurityRequirement(name = "security"),
            tags = "Post",
            requestBody = @RequestBody(
                    description = "Request body for a new sale.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateSaleRequest.class),
                            examples = @ExampleObject(value = "{ \"user_cpf\": \"43721493010\", \"items\": [ { \"product_id\": 1, \"quantity\": 2 }, { \"product_id\": 2, \"quantity\": 1 } ] }")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Sale created successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaleResponse.class),
                                    examples = @ExampleObject(value = "[{\"id\":1,\"date_sale\":\"2024-09-01T00:00:00Z\",\"user_cpf\":\"43721493010\",\"status\":\"PROCESSING\",\"items\":[{\"product_id\":1,\"quantity\":2,\"price\":50.00}],\"total_value\":100.00}]")
                            )
                    ),@ApiResponse(
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
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflict due to the sale with the given details already existing.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Sale with these details already exists.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable entity due to invalid input data.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Invalid input data provided.\"}")
                            )
                    )
            }
    )
    ResponseEntity<SaleResponse> saveSale(CreateSaleRequest createSaleRequest);

    @Operation(
            summary = "Retrieve a sale by ID",
            description = "Fetches a sale record by its ID. Accessible only to the client who owns the sale or an admin.",
            security = @SecurityRequirement(name = "security"),
            tags = "Get",
            parameters = {
                    @Parameter(name = "id", description = "The id of the sale to retrieve", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sale retrieved successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaleResponse.class),
                                    examples = @ExampleObject(value = "[{\"id\":1,\"date_sale\":\"2024-09-01T00:00:00Z\",\"user_cpf\":\"43721493010\",\"status\":\"PROCESSING\",\"items\":[{\"product_id\":1,\"quantity\":2,\"price\":50.00}],\"total_value\":100.00}]")
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Sale with the specified ID not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Sale not found.\"}")
                            )
                    )
            }
    )
    ResponseEntity<SaleResponse> findSaleById(Long id);

    @Operation(
            summary = "Delete a sale by ID",
            description = "Deletes a sale record by its ID. Accessible only to admins.",
            security = @SecurityRequirement(name = "security"),
            tags = "Delete",
            parameters = {
                    @Parameter(name = "id", description = "The id of the sale to delete", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Sale deleted successfully."
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Sale with the specified ID not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Sale not found.\"}")
                            )
                    )
            }
    )
    ResponseEntity<Void> deleteSaleById(Long id);

    @Operation(
            summary = "Cancel a sale by ID",
            description = "Cancels a sale by its ID. Accessible to the client who owns the sale or an admin.",
            security = @SecurityRequirement(name = "security"),
            tags = "Patch",
            parameters = {
                    @Parameter(name = "id", description = "The id of the sale to cancel", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sale status updated to 'CANCELLED' successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaleResponse.class),
                                    examples = @ExampleObject(value = "[{\"id\":1,\"date_sale\":\"2024-09-01T00:00:00Z\",\"user_cpf\":\"43721493010\",\"status\":\"CANCELLED\",\"items\":[{\"product_id\":1,\"quantity\":2,\"price\":50.00}],\"total_value\":100.00}]")
                            )
                    ),@ApiResponse(
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Sale with the specified ID not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Sale not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Sale is already cancelled.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Sale is already cancelled.\"}")
                            )
                    )
            }
    )
    ResponseEntity<SaleResponse> cancelSale(Long id);

    @Operation(
            summary = "Complete status sale by ID",
            description = "Complete status sale by its ID. Accessible only to admin.",
            security = @SecurityRequirement(name = "security"),
            tags = "Patch",
            parameters = {
                    @Parameter(name = "id", description = "The id of the sale to complete status", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sale status updated to 'COMPLETED' successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaleResponse.class),
                                    examples = @ExampleObject(value = "[{\"id\":1,\"date_sale\":\"2024-09-01T00:00:00Z\",\"user_cpf\":\"43721493010\",\"status\":\"COMPLETED\",\"items\":[{\"product_id\":1,\"quantity\":2,\"price\":50.00}],\"total_value\":100.00}]")
                            )
                    ),@ApiResponse(
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Sale with the specified ID not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Sale not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Sale is cancelled, cannot be completed status. / Sale is already completed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Sale is cancelled, cannot be completed status. / Sale is already completed\"}")
                            )
                    )
            }
    )
    ResponseEntity<SaleResponse> completeSale(Long id);

    @Operation(
            summary = "Update an item in a sale",
            description = "Updates the items in a sale. Only the client associated with the sale or an admin can add or modify items.",
            security = @SecurityRequirement(name = "security"),
            tags = "Put",
            parameters = {
                    @Parameter(name = "id", description = "The id of the sale to update", required = true)
            },
            requestBody = @RequestBody(
                    description = "Request body for a new sale.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateItemSaleRequest.class),
                            examples = @ExampleObject(value = "{ \"items\": [ { \"product_id\": 1, \"quantity\": 2 }, { \"product_id\": 2, \"quantity\": 1 } ] }")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sale updated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaleResponse.class),
                                    examples = @ExampleObject(value = "[{\"id\":1,\"date_sale\":\"2024-09-01T00:00:00Z\",\"user_cpf\":\"43721493010\",\"status\":\"PROCESSING\",\"items\":[{\"product_id\":2,\"quantity\":5,\"price\":50.00}],\"total_value\":100.00}]")
                            )
                    ),@ApiResponse(
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Sale with the specified ID not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Sale not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid input data provided.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Invalid data provided.\"}")
                            )
                    )
            }
    )
    ResponseEntity<SaleResponse> updateSale(Long id, UpdateItemSaleRequest itemSaleRequest);

    @Operation(
            summary = "Patch items in a sale",
            description = "Patches the items in a sale by adding, removing, or modifying them. Accessible only to the client associated with the sale or an admin.",
            security = @SecurityRequirement(name = "security"),
            tags = "Patch",
            parameters = {
                    @Parameter(name = "id", description = "The id of the sale to patch", required = true)
            },
            requestBody = @RequestBody(
                    description = "Request body for a new sale.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdatePatchItemSaleRequest.class),
                            examples = @ExampleObject(value = "{ \"update_items\": [ { \"product_id\": 1, \"quantity\": 2 } ], \"remove_items\": [ 2 ] }")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sale items updated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaleResponse.class),
                                    examples = @ExampleObject(value = "[{\"id\":1,\"date_sale\":\"2024-09-01T00:00:00Z\",\"user_cpf\":\"43721493010\",\"status\":\"PROCESSING\",\"items\":[{\"product_id\":1,\"quantity\":2,\"price\":50.00}],\"total_value\":100.00}]")
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Sale with the specified ID not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Sale not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid input data provided.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Invalid data provided.\"}")
                            )
                    )
            }
    )
    ResponseEntity<SaleResponse> patchSale(Long id, UpdatePatchItemSaleRequest patchItemSale);

    @Operation(
            summary = "List all sales",
            description = "Retrieves a paginated list of all sales records. This endpoint is accessible only to users with admin privileges. It returns detailed information about each sale, including the sale date, user information, status, items involved, and total value. Only for ADMINs",
            security = @SecurityRequirement(name = "security"),
            tags = "Get",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "The page number to retrieve, starting from 0.",
                            required = true,
                            schema = @Schema(type = "integer", example = "0")
                    ),
                    @Parameter(
                            name = "size",
                            description = "The number of sales records per page.",
                            required = true,
                            schema = @Schema(type = "integer", example = "10")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved the list of sales.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "[{\"id\":1,\"date_sale\":\"2024-09-01T00:00:00Z\",\"user_cpf\":\"43721493010\",\"status\":\"PROCESSING\",\"items\":[{\"product_id\":1,\"quantity\":2,\"price\":50.00}],\"total_value\":100.00}, {\"id\":2,\"date_sale\":\"2024-09-01T00:00:00Z\",\"user_cpf\":\"23239786087\",\"status\":\"PROCESSING\",\"items\":[{\"product_id\":3,\"quantity\":5,\"price\":50.00}],\"total_value\":250.00}]"),
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = SaleResponse.class)
                                    )
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
    ResponseEntity<PageableResponse<SaleResponse>> findAllSales(int page, int size);
}
