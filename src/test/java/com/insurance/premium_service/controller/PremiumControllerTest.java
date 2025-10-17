package com.insurance.premium_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.premium_service.model.PremiumRequest;
import com.insurance.premium_service.model.PremiumResponse;
import com.insurance.premium_service.service.PremiumCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PremiumControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PremiumCalculationService calculationService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        PremiumController controller = new PremiumController(calculationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void calculate_ok() throws Exception {
        when(calculationService.calculatePremium(any(PremiumRequest.class)))
                .thenReturn(new PremiumResponse(198.0));

        PremiumRequest request = new PremiumRequest(15000, "SUV", "12345");
        String body = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/premium/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calculatedPremium").value(198.0));
    }
}


