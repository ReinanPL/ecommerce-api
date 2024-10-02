package com.compass.reinan.api_ecommerce.controller.impl;

import com.compass.reinan.api_ecommerce.controller.SaleController;
import com.compass.reinan.api_ecommerce.domain.dto.page.PageableResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.CreateSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.response.SaleResponse;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.UpdateItemSaleRequest;
import com.compass.reinan.api_ecommerce.domain.dto.sale.request.UpdatePatchItemSaleRequest;
import com.compass.reinan.api_ecommerce.service.SaleService;
import com.compass.reinan.api_ecommerce.util.MediaType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/sales")
public class SaleControllerImpl implements SaleController {

    private final SaleService saleService;

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_CLIENT')")
    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<SaleResponse> saveSale(@RequestBody @Valid CreateSaleRequest createSaleRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.save(createSaleRequest));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_CLIENT')")
    @GetMapping(value = "/{id}",
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<SaleResponse> findSaleById(@PathVariable Long id) {
        return ResponseEntity.ok().body(saleService.findById(id));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteSaleById(@PathVariable Long id) {
        saleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_CLIENT')")
    @PatchMapping(value = "/{id}/cancel",
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<SaleResponse> cancelSale(@PathVariable Long id) {
        return ResponseEntity.ok().body(saleService.cancelSale(id));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PatchMapping(value = "/{id}/complete",
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<SaleResponse> completeSale(@PathVariable Long id) {
        return ResponseEntity.ok().body(saleService.completeSale(id));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_CLIENT')")
    @PutMapping(value = "/{id}",
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<SaleResponse> updateSale(@PathVariable Long id, @RequestBody @Valid UpdateItemSaleRequest itemSaleRequest) {
        return ResponseEntity.ok().body(saleService.updateSale(id, itemSaleRequest));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_CLIENT')")
    @PatchMapping(value = "/{id}",
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<SaleResponse> patchSale(@PathVariable Long id, @RequestBody @Valid UpdatePatchItemSaleRequest patchItemSale) {
        return ResponseEntity.ok().body(saleService.patchSale(id, patchItemSale));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping(
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<PageableResponse<SaleResponse>> findAllSales(@RequestParam(defaultValue = "0")  int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok().body(saleService.findAll(page, size));
    }
}
