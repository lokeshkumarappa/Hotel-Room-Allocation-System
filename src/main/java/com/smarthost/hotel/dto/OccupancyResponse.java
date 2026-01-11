package com.smarthost.hotel.dto;

public record OccupancyResponse(
        int usagePremium,
        double revenuePremium,
        int usageEconomy,
        double revenueEconomy
) {}