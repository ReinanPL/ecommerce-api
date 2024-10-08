package com.compass.reinan.api_ecommerce.controller;

import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryResponse;
import com.compass.reinan.api_ecommerce.domain.dto.category.CreateCategoryRequest;
import com.compass.reinan.api_ecommerce.exception.handler.ErrorMessage;
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

import java.util.List;

@Tag(name = "Category", description = "Operations for managing product categories including creation, retrieval, update, deletion, and activation.")
public interface CategoryController {

    @Operation(
            summary = "Create a new category",
            description = "Creates a new category. Only users with CLIENT or ADMIN roles can create a category.",
            security = @SecurityRequirement(name = "security"),
            tags = "Post",
            requestBody = @RequestBody(
                    description = "Request body for a new category name.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateCategoryRequest.class),
                            examples = @ExampleObject(value = "{ \"name\": \"Suplementos\" }")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Category created successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponse.class),
                                    examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Suplementos\",\"active\":true}")
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
                    ) ,
                    @ApiResponse(
                            responseCode = "409",
                            description = "Category name already exists.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Category name already registered.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid input data.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Invalid input data.\"}")
                            )
                    )
            }
    )
    ResponseEntity<CategoryResponse> saveCategory(CreateCategoryRequest createCategoryRequest);

    @Operation(
            summary = "Retrieve a category by ID",
            description = "Retrieves a category by its ID. Accessible to any authenticated user.",
            security = @SecurityRequirement(name = "security"),
            tags = "Get",
            parameters = {
                    @Parameter(name = "id", description = "The id of the category to retrieve", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category retrieved successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponse.class),
                                    examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Suplementos\",\"active\":true}")
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
                            description = "Category with the specified ID not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Category not found.\"}")
                            )
                    )
            }
    )
    ResponseEntity<CategoryResponse> getCategoryById(Long id);

    @Operation(
            summary = "Delete a category by ID",
            description = "Deletes a category by its ID. If there are products linked to the category, it will be inactivated instead.",
            security = @SecurityRequirement(name = "security"),
            tags = "Delete",
            parameters = {
                    @Parameter(name = "id", description = "The id of the category to delete", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Category deleted successfully."
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
                            description = "Category with the specified ID not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Category not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Category is already inactive or cannot be deleted.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Category is already inactive.\"}")
                            )
                    )
            }
    )
    ResponseEntity<Void> deleteCategory(Long id);

    @Operation(
            summary = "Activate a category by ID",
            description = "Activates a category that is currently inactive.",
            security = @SecurityRequirement(name = "security"),
            tags = "Patch",
            parameters = {
                    @Parameter(name = "id", description = "The id of the category to activate", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category activated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponse.class),
                                    examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Suplementos\",\"active\":true}")
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
                            description = "Category with the specified ID not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Category not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Category is already active.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Category is already active.\"}")
                            )
                    )
            }
    )
    ResponseEntity<CategoryResponse> activeCategory(Long id);

    @Operation(
            summary = "Update a category's name",
            description = "Updates the name of an existing category.",
            security = @SecurityRequirement(name = "security"),
            tags = "Patch",
            parameters = {
                    @Parameter(name = "id", description = "The id of the category to update", required = true)
            },
            requestBody = @RequestBody(
                    description = "Request body for a update a category name.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateCategoryRequest.class),
                            examples = @ExampleObject(value = "{ \"name\": \"Roupas\" }")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category name updated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponse.class),
                                    examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Roupas\",\"active\":true}")
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
                            description = "Category with the specified ID not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Category not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Category name already exists.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Category name already registered.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid input data.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Invalid input data.\"}")
                            )
                    )
            }
    )
    ResponseEntity<CategoryResponse> updateCategoryName(Long id, CreateCategoryRequest requestDto);

    @Operation(
            summary = "List all categories",
            description = "Retrieves a list of all registered categories.",
            security = @SecurityRequirement(name = "security"),
            tags = "Get",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of all registered categories retrieved successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "[{\"id\":1,\"name\":\"Suplementos\",\"active\":true}, {\"id\":2,\"name\":\"Roupas\",\"active\":true}]"),
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = CategoryResponse.class)
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
    ResponseEntity<List<CategoryResponse>> getAllCategories();
}
