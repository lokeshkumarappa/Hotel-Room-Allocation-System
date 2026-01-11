package com.smarthost.hotel.service;

import com.smarthost.hotel.dto.OccupancyRequest;
import com.smarthost.hotel.dto.OccupancyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OccupancyServiceTest {

    @Autowired
    private OccupancyService service;

    private final List<Double> guests = List.of(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0);

    @Test
    @DisplayName("Test Case 1: 3 Premium, 3 Economy")
    void testCase1() {
        var res = service.calculateOccupancy(new OccupancyRequest(3, 3, guests));
        assertEquals(3, res.usagePremium());
        assertEquals(738.0, res.revenuePremium());
        assertEquals(3, res.usageEconomy());
        assertEquals(167.99, res.revenueEconomy());
    }

    @Test
    @DisplayName("Test Case 2: 7 Premium, 5 Economy (High Upgrade Scenario)")
    void testCase2() {
        var res = service.calculateOccupancy(new OccupancyRequest(7, 5, guests));
        assertEquals(6, res.usagePremium());
        assertEquals(1054.0, res.revenuePremium());
        assertEquals(4, res.usageEconomy());
        assertEquals(189.99, res.revenueEconomy());
    }

    @Test
    @DisplayName("Test Case 3: 2 Premium, 7 Economy (No Upgrades Available)")
    void testCase3() {
        var res = service.calculateOccupancy(new OccupancyRequest(2, 7, guests));
        assertEquals(2, res.usagePremium());
        assertEquals(583.0, res.revenuePremium());
        assertEquals(4, res.usageEconomy());
        assertEquals(189.99, res.revenueEconomy());
    }

    @Test
    @DisplayName("Edge Case: More rooms than guests")
    void testEmptyHotel() {
        var res = service.calculateOccupancy(new OccupancyRequest(20, 20, guests));
        assertEquals(6, res.usagePremium()); // 6 guests >= 100
        assertEquals(4, res.usageEconomy()); // 4 guests < 100
    }

    @Test
    @DisplayName("Edge Case: Zero rooms available")
    void testNoRooms() {
        var res = service.calculateOccupancy(new OccupancyRequest(0, 0, guests));
        assertEquals(0, res.usagePremium());
        assertEquals(0, res.revenuePremium());
        assertEquals(0, res.usageEconomy());
        assertEquals(0, res.revenueEconomy());
    }
}