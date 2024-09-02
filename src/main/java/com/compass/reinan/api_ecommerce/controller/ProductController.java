package com.compass.reinan.api_ecommerce.controller;

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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Product", description = "Operations for managing products including creation, retrieval, update, deletion, and listing.")
public interface ProductController {

    @Operation(
            summary = "Create a new product",
            description = "Creates a new product. Only users with CLIENT or ADMIN roles can create a product.",
            security = @SecurityRequirement(name = "security"),
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
    ResponseEntity<ProductResponse> saveProduct(
            @Parameter(description = "Details of the product to be created.", required = true)
            ProductRequest productRequest
    );

    @Operation(
            summary = "Retrieve a product by ID",
            description = "Retrieves a product by its ID. Accessible to any authenticated user.",
            security = @SecurityRequirement(name = "security"),
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
    ResponseEntity<ProductResponse> findProductById(
            @Parameter(description = "ID of the product to retrieve.", required = true)
            Long id
    );

    @Operation(
            summary = "Delete a product by ID",
            description = "Deletes a product by its ID if it is not linked to any items. If the product is linked, it will be inactivated instead.",
            security = @SecurityRequirement(name = "security"),
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
    ResponseEntity<Void> deleteProductById(
            @Parameter(description = "ID of the product to delete or deactivate.", required = true)
            Long id
    );

    @Operation(
            summary = "Update a product's details",
            description = "Updates the details of an existing product, such as its name or price.",
            security = @SecurityRequirement(name = "security"),
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
    ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "ID of the product to update.", required = true)
            Long id,
            @Parameter(description = "Updated details of the product.", required = true)
            UpdateProductRequest productRequest
    );

    @Operation(
            summary = "Activate a product by ID",
            description = "Activates a product that is currently inactive.",
            security = @SecurityRequirement(name = "security"),
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
    ResponseEntity<ProductResponse> activeProduct(
            @Parameter(description = "ID of the product to activate.", required = true)
            Long id
    );

    @Operation(
            summary = "List all products",
            description = "Retrieves a list of all registered products.",
            security = @SecurityRequirement(name = "security"),
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
    ResponseEntity<List<ProductResponse>> findAllProducts();
}
