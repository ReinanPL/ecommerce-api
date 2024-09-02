package com.compass.reinan.api_ecommerce.controller;

import com.compass.reinan.api_ecommerce.domain.dto.sale.SaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.SaleResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.UpdateItemSale;
import com.compass.reinan.api_ecommerce.domain.dto.sale.UpdatePatchItemSale;
import com.compass.reinan.api_ecommerce.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Sale", description = "Contains all operations related to resources for registering, deleting, editing and reading a Sale.")
public interface SaleController {

    @Operation(summary = "Create a new sale", description = "Resource for creating a new sale, only CLIENT(linked with the user_cpf of the new Sale) and ADMIN can create a sale",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created with success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleResponse.class))),
                    @ApiResponse(responseCode = "409", description = "Product name already registered in the system",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<SaleResponse> saveSale(SaleRequest saleRequest);

    @Operation(summary = "Retrieve a sale by id", description = "Retrieve a sale by id, only the client who owns the sale or an admin can retrieve a sale by id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<SaleResponse> findSaleById(Long id);

    @Operation(summary = "Delete a sale by id", description = "Delete sale by id, only the ADMIN can delete the sale",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    ResponseEntity<Void> deleteSaleById(Long id);

    @Operation(summary = "Cancel a sale by id", description = "Cancel a sale by id, only the client who owns the sale or an admin can cancel the sale status",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource activated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Product is already active",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<SaleResponse> cancelSale(@PathVariable Long id);

    @Operation(summary = "Update an item to a sale", description = "Update an itemList from a sale by id, only the CLIENT linked to the sale or the ADMIN can add the item",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            })
    ResponseEntity<SaleResponse> updateSale(Long id, UpdateItemSale itemSaleRequest);

    @Operation(summary = "Patch an item to a sale", description = "Patch itemList from a sale by id, the user can add, remove or modify the items, only the CLIENT linked to the sale or the ADMIN can remove the item",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource patched successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    ResponseEntity<SaleResponse> patchSale(Long idSale, UpdatePatchItemSale patchItemSale);

    @Operation(summary = "List all sales", description = "List all registered sales, only ADMIN can retrieve this resource",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all registered sales", content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SaleResponse.class))))
            })
    ResponseEntity<List<SaleResponse>> findAllSales();
}
