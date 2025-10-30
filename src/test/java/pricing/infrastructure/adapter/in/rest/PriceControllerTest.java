package pricing.infrastructure.adapter.in.rest;

import pricing.domain.model.Price;
import pricing.domain.port.in.GetPriceUseCase;
import pricing.domain.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pricing.infrastructure.adapter.in.PriceController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriceController.class)
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetPriceUseCase getPriceUseCase;

    private static final String API_ENDPOINT = "/api/prices";
    private static final String VALID_DATE = "2020-06-14-10.00.00";
    private static final LocalDateTime EXPECTED_DATE = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;

    private Price createPrice(Long id, Long priceList) {
        return new Price(
                id,
                BRAND_ID,
                priceList,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59),
                new BigDecimal("25.45"),
                1
        );
    }

    @Test
    void getPrices_whenValidRequest_shouldReturnPriceList() throws Exception {
        Price price1 = createPrice(1L, 1L);
        Price price2 = createPrice(2L, 2L);
        List<Price> prices = List.of(price1, price2);

        when(getPriceUseCase.getPrices(PRODUCT_ID, BRAND_ID, EXPECTED_DATE))
                .thenReturn(prices);

        mockMvc.perform(get(API_ENDPOINT)
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString())
                        .param("date", VALID_DATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].productId").value(1))
                .andExpect(jsonPath("$[0].priceList").value(1))
                .andExpect(jsonPath("$[0].brandId").value(BRAND_ID))
                .andExpect(jsonPath("$[1].productId").value(2))
                .andExpect(jsonPath("$[1].priceList").value(2));

        verify(getPriceUseCase).getPrices(PRODUCT_ID, BRAND_ID, EXPECTED_DATE);
        verifyNoMoreInteractions(getPriceUseCase);
    }

    @Test
    void getPrices_whenSinglePrice_shouldReturnOneElement() throws Exception {
        Price price = createPrice(1L, 1L);

        when(getPriceUseCase.getPrices(PRODUCT_ID, BRAND_ID, EXPECTED_DATE))
                .thenReturn(List.of(price));

        mockMvc.perform(get(API_ENDPOINT)
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString())
                        .param("date", VALID_DATE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].priceList").value(1));

        verify(getPriceUseCase).getPrices(PRODUCT_ID, BRAND_ID, EXPECTED_DATE);
    }

    @Test
    void getPrices_whenNoPricesFound_shouldReturnNotFound() throws Exception {
        String errorMessage = "No price found for productId " + PRODUCT_ID +
                ", brandId " + BRAND_ID;

        when(getPriceUseCase.getPrices(PRODUCT_ID, BRAND_ID, EXPECTED_DATE))
                .thenThrow(new NotFoundException(errorMessage));

        mockMvc.perform(get(API_ENDPOINT)
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString())
                        .param("date", VALID_DATE))
                .andExpect(status().isNotFound());

        verify(getPriceUseCase).getPrices(PRODUCT_ID, BRAND_ID, EXPECTED_DATE);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2020-06-14 10:00:00",
            "2020/06/14-10.00.00",
            "invalid-date",
            "2020-13-14-10.00.00"
    })
    void getPrices_whenInvalidDateFormat_shouldReturnBadRequest(String invalidDate) throws Exception {
        mockMvc.perform(get(API_ENDPOINT)
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString())
                        .param("date", invalidDate))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(getPriceUseCase);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1, 2020-06-14-10.00.00",
            "999999, 1, 2020-06-14-10.00.00",
            "35455, 0, 2020-06-14-10.00.00",
            "35455, 999, 2020-06-14-10.00.00"
    })
    void getPrices_withDifferentValidIds_shouldCallService(Long productId, Long brandId, String date) throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

        when(getPriceUseCase.getPrices(productId, brandId, dateTime))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get(API_ENDPOINT)
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("date", date))
                .andExpect(status().isOk());

        verify(getPriceUseCase).getPrices(productId, brandId, dateTime);
    }

    @Test
    void getPrices_whenMissingRequiredParams_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(API_ENDPOINT)
                        .param("productId", PRODUCT_ID.toString()))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(getPriceUseCase);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "not-a-number", "12.5"})
    void getPrices_whenInvalidProductIdFormat_shouldReturnBadRequest(String invalidId) throws Exception {
        mockMvc.perform(get(API_ENDPOINT)
                        .param("productId", invalidId)
                        .param("brandId", BRAND_ID.toString())
                        .param("date", VALID_DATE))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(getPriceUseCase);
    }
}