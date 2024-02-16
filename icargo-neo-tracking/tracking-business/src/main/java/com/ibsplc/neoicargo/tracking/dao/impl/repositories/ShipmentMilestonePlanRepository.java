package com.ibsplc.neoicargo.tracking.dao.impl.repositories;

import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestonePlan;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShipmentMilestonePlanRepository extends CrudRepository<ShipmentMilestonePlan, Long> {
    List<ShipmentMilestonePlan> findByShipmentKeyIn(List<String> shipmentKeys);

    long deleteByShipmentKey(String shipmentKey);
    
    long deleteByShipmentKeyAndAirportCodeAndMilestoneCode(String shipmentKey,String airportCode,String milestoneCode);
    
    long deleteByShipmentKeyAndAirportCodeAndMilestoneCodeAndFlightCarrierCodeAndFlightNumberAndFlightDate(String shipmentKey,String airportCode,String milestoneCode,String flightCarrierCode,String flightNumber,LocalDate flightDate);
}
