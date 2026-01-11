package com.smarthost.hotel.service;

import com.smarthost.hotel.dto.OccupancyRequest;
import com.smarthost.hotel.dto.OccupancyResponse;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class OccupancyService {

    public OccupancyResponse calculateOccupancy(OccupancyRequest request) {
        List<Double> guests = request.potentialGuests();

        // Split guests into Premium (>= 100) and Economy (< 100) sorted descending
        List<Double> premiumBids = guests.stream()
                .filter(g -> g >= 100)
                .sorted(Comparator.reverseOrder())
                .toList();

        List<Double> economyBids = guests.stream()
                .filter(g -> g < 100)
                .sorted(Comparator.reverseOrder())
                .toList();

        int premiumRooms = request.premiumRooms();
        int economyRooms = request.economyRooms();

        int usedPremium = Math.min(premiumRooms, premiumBids.size());
        double revPremium = premiumBids.stream().limit(usedPremium).mapToDouble(Double::doubleValue).sum();

        int vacantPremium = premiumRooms - usedPremium;
        int economyOverflow = Math.max(0, economyBids.size() - economyRooms);
        int totalUpgrades = Math.min(vacantPremium, economyOverflow);

        revPremium += economyBids.stream().limit(totalUpgrades).mapToDouble(Double::doubleValue).sum();
        usedPremium += totalUpgrades;

        int usedEconomy = Math.min(economyRooms, economyBids.size() - totalUpgrades);
        double revEconomy = economyBids.stream()
                .skip(totalUpgrades)
                .limit(usedEconomy)
                .mapToDouble(Double::doubleValue)
                .sum();

        return new OccupancyResponse(usedPremium, revPremium, usedEconomy, revEconomy);
    }
}