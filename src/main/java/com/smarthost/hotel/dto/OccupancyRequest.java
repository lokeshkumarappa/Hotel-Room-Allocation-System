package com.smarthost.hotel.dto;

import java.util.List;

public record OccupancyRequest(
        int premiumRooms,
        int economyRooms,
        List<Double> potentialGuests
) {}