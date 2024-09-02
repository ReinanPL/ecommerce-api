package com.compass.reinan.api_ecommerce.controller;

import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryRequestDto;
import com.compass.reinan.api_ecommerce.domain.dto.category.CategoryResponseDto;
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

@Tag(name = "Category", description = "Operations for managing product categories including creation, retrieval, update, deletion, and activation.")
public interface CategoryController {

    @Operation(
            summary = "Create a new category",
            description = "Creates a new category. Only users with CLIENT or ADMIN roles can create a category.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Category created successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponseDto.class),
                                    examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Electronics\",\"active\":true}")
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
    ResponseEntity<CategoryResponseDto> saveCategory(
            @Parameter(description = "Details of the category to be created.", required = true)
            CategoryRequestDto categoryRequestDto
    );

    @Operation(
            summary = "Retrieve a category by ID",
            description = "Retrieves a category by its ID. Accessible to any authenticated user.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category retrieved successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponseDto.class),
                                    examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Electronics\",\"active\":true}")
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
    ResponseEntity<CategoryResponseDto> getCategoryById(
            @Parameter(description = "ID of the category to retrieve.", required = true)
            Long id
    );

    @Operation(
            summary = "Delete a category by ID",
            description = "Deletes a category by its ID. If there are products linked to the category, it will be inactivated instead.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Category deleted successfully."
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
    ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID of the category to delete or deactivate.", required = true)
            Long id
    );

    @Operation(
            summary = "Activate a category by ID",
            description = "Activates a category that is currently inactive.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category activated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponseDto.class),
                                    examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Electronics\",\"active\":true}")
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
    ResponseEntity<CategoryResponseDto> activeCategory(
            @Parameter(description = "ID of the category to activate.", required = true)
            Long id
    );

    @Operation(
            summary = "Update a category's name",
            description = "Updates the name of an existing category.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category name updated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponseDto.class),
                                    examples = @ExampleObject(value = "{\"id\":1,\"name\":\"Updated Category Name\",\"active\":true}")
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
    ResponseEntity<CategoryResponseDto> updateCategoryName(
            @Parameter(description = "ID of the category to update.", required = true)
            Long id,
            @Parameter(description = "New details for the category, including the updated name.", required = true)
            CategoryRequestDto requestDto
    );

    @Operation(
            summary = "List all categories",
            description = "Retrieves a list of all registered categories.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of all registered categories retrieved successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "[{\"id\":1,\"name\":\"Electronics\",\"active\":true}, {\"id\":2,\"name\":\"Home Appliances\",\"active\":true}]"),
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = CategoryResponseDto.class)
                                    )
                            )
                    )
            }
    )
    ResponseEntity<List<CategoryResponseDto>> getAllCategories();
}
