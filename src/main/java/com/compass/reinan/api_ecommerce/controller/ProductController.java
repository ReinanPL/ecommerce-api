package com.compass.reinan.api_ecommerce.controller;

import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.ProductRequest;
import com.compass.reinan.api_ecommerce.domain.dto.product.ProductResponse;
import com.compass.reinan.api_ecommerce.domain.dto.product.UpdateProductRequest;
import com.compass.reinan.api_ecommerce.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

@Tag(name = "Product", description = "Operations for managing products including creation, retrieval, update, deletion, and listing.")
public interface ProductController {

    @Operation(
            summary = "Create a new product",
            description = "Creates a new product. Only users with CLIENT or ADMIN roles can create a product.",
            security = @SecurityRequirement(name = "security"),
            requestBody = @RequestBody(
                    description = "Request body for a new product.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductRequest.class),
                            examples =@ExampleObject(value = "{ \"name\": \"Product A\", \"quantity_in_stock\": 10, \"price\": 19.99, \"category_id\": 1 }")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Product created successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponse.class),
                                    examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Sample Product\",\"price\":19.99,\"active\":true}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflict due to a product with the same name already existing.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Product name already registered.\"}")
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
    ResponseEntity<ProductResponse> saveProduct(ProductRequest productRequest);

    @Operation(
            summary = "Retrieve a product by ID",
            description = "Retrieves a product by its ID. Accessible to any authenticated user.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(name = "id", description = "The id of the product to retrieve", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product retrieved successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponse.class),
                                    examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Sample Product\",\"price\":19.99,\"active\":true}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product with the specified ID not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Product not found.\"}")
                            )
                    )
            }
    )
    ResponseEntity<ProductResponse> findProductById(Long id);

    @Operation(
            summary = "Delete a product by ID",
            description = "Deletes a product by its ID if it is not linked to any items. If the product is linked, it will be inactivated instead.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(name = "id", description = "The id of the product to delete", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Product deleted successfully."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product with the specified ID not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Product not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Product is already inactive or cannot be deleted.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Product is already inactive.\"}")
                            )
                    )
            }
    )
    ResponseEntity<Void> deleteProductById(Long id);

    @Operation(
            summary = "Update a product's details",
            description = "Updates the details of an existing product, such as its name or price.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(name = "id", description = "The id of the product to update", required = true)
            },
            requestBody = @RequestBody(
                    description = "Request body for a new product.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateProductRequest.class),
                            examples = @ExampleObject(value = "{ \"name\": \"Updated Product Name\", \"quantity_in_stock\": 15, \"price\": 24.99 }")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product updated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponse.class),
                                    examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Updated Product\",\"price\":29.99,\"active\":true}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product with the specified ID not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Product not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflict due to a product name already existing.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Product name already registered.\"}")
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
    ResponseEntity<ProductResponse> updateProduct(Long id, UpdateProductRequest productRequest);

    @Operation(
            summary = "Activate a product by ID",
            description = "Activates a product that is currently inactive.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(name = "id", description = "The id of the product to activate", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product activated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponse.class),
                                    examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Sample Product\",\"price\":19.99,\"active\":true}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product with the specified ID not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Product not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Product is already active.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Product is already active.\"}")
                            )
                    )
            }
    )
    ResponseEntity<ProductResponse> activeProduct(Long id);

    @Operation(
            summary = "List all products",
            description = "Retrieves a paginated list of all registered products. This endpoint returns all products, regardless of their active status or stock level. Only for ADMINs",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(name = "page", description = "The page number to retrieve (starting from 0).", required = true, schema = @Schema(type = "integer", example = "0")),
                    @Parameter(name = "size", description = "The number of products per page.", required = true, schema = @Schema(type = "integer", example = "10"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of all registered products retrieved successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "[{\"id\":1,\"name\":\"Sample Product\",\"price\":19.99,\"active\":true}, {\"id\":2,\"name\":\"Another Product\",\"price\":29.99,\"active\":false}]"),
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = ProductResponse.class)
                                    )
                            )
                    )
            }
    )
    ResponseEntity<PageableResponse<ProductResponse>> findAllProducts(int page, int size);

    @Operation(
            summary = "List active products with filters",
            description = "Retrieves a paginated list of active products that match the specified filters for category and price range. This endpoint only returns products that are active and have a stock greater than 0.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(name = "categoryId", description = "The ID of the category to filter products by. Pass null to ignore this filter.", schema = @Schema(type = "integer", example = "1")),
                    @Parameter(name = "minPrice", description = "The minimum price of the products to include. Pass null to ignore this filter.", schema = @Schema(type = "number", format = "float", example = "10.00")),
                    @Parameter(name = "maxPrice", description = "The maximum price of the products to include. Pass null to ignore this filter.", schema = @Schema(type = "number", format = "float", example = "100.00")),
                    @Parameter(name = "page", description = "The page number to retrieve (starting from 0).", required = true, schema = @Schema(type = "integer", example = "0")),
                    @Parameter(name = "size", description = "The number of products per page.", required = true, schema = @Schema(type = "integer", example = "10"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of active products retrieved successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "[{\"id\":1,\"name\":\"Sample Product\",\"price\":19.99,\"active\":true}, {\"id\":3,\"name\":\"Filtered Product\",\"price\":25.99,\"active\":true}]"),
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = ProductResponse.class)
                                    )
                            )
                    )
            }
    )
    ResponseEntity<PageableResponse<ProductResponse>> findAllProductsActives(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, int page, int size);
}
