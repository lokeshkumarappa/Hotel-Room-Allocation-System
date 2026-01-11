package com.smarthost.hotel.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OccupancyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnCorrectOccupancy_WhenValidRequestIsMade() throws Exception {
        // This corresponds to Test Case 2 from the problem statement
        String jsonRequest = """
            {
                "premiumRooms": 7,
                "economyRooms": 5,
                "potentialGuests": [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]
            }
            """;

        mockMvc.perform(post("/occupancy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usagePremium").value(6))
                .andExpect(jsonPath("$.revenuePremium").value(1054.0))
                .andExpect(jsonPath("$.usageEconomy").value(4))
                .andExpect(jsonPath("$.revenueEconomy").value(189.99));
    }

    @Test
    void shouldHandleZeroRoomsGracefully() throws Exception {
        String jsonRequest = """
            {
                "premiumRooms": 0,
                "economyRooms": 0,
                "potentialGuests": [100, 20, 50]
            }
            """;

        mockMvc.perform(post("/occupancy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usagePremium").value(0))
                .andExpect(jsonPath("$.usageEconomy").value(0));
    }
}