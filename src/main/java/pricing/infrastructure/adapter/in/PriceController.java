package pricing.infrastructure.adapter.in;

import pricing.domain.exception.ErrorResponse;
import pricing.domain.port.in.GetPriceUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pricing.infrastructure.adapter.in.mapper.PriceResponseMapper;
import pricing.infrastructure.adapter.in.rest.dto.PriceDTO;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/prices")
@Tag(name = "Prices", description = "Operations related to product prices")
@Validated
public class PriceController {


    private final GetPriceUseCase getPriceUseCase;
    public PriceController(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }
    @GetMapping
    @Operation(summary = "Retrieves the applicable price for a given product and brand at a specific date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price found successfully"),
            @ApiResponse(responseCode = "404", description = "Price not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
    public List<PriceDTO> getPrices(
            @Parameter(description = "Query date in format yyyy-MM-dd-HH.mm.ss (example: 2020-12-31-23.59.59)")
            @RequestParam LocalDateTime date,
            @Parameter(description = "Product ID")
            @RequestParam Long productId,
            @Parameter(description = "Brand ID")
            @RequestParam Long brandId) {

        return getPriceUseCase.getPrices(productId, brandId, date).stream()
                .map(PriceResponseMapper::toDTO)
                .toList();
    }
}
