package com.ibsplc.neoicargo.tracking.dao.impl;

import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.dao.entity.Milestone;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestoneEvent;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestonePlan;
import com.ibsplc.neoicargo.tracking.dao.impl.repositories.MilestoneRepository;
import com.ibsplc.neoicargo.tracking.dao.impl.repositories.ShipmentMilestoneEventRepository;
import com.ibsplc.neoicargo.tracking.dao.impl.repositories.ShipmentMilestonePlanRepository;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanDeletedVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrackingDAOImpl implements TrackingDAO {

    private final ShipmentMilestonePlanRepository shipmentMilestonePlanRepository;
    private final ShipmentMilestoneEventRepository shipmentMilestoneEventRepository;
    private final MilestoneRepository milestoneRepository;

    @Override
    public List<ShipmentMilestonePlan> findPlansByShipmentKeys(List<String> shipmentKeys) {
        log.info("Find tracking shipment milestone plan by shipmentKeys: {}", shipmentKeys);
        return shipmentMilestonePlanRepository.findByShipmentKeyIn(shipmentKeys);
    }

    @Override
    public List<ShipmentMilestoneEvent> findEventsByShipmentKeysAndType(List<String> shipmentKeys,
                                                                        String shipmentType) {
        log.info("Find tracking shipment milestone event by shipmentKeys: {} and shipment type: {}", shipmentKeys, shipmentType);
        return shipmentMilestoneEventRepository.findByShipmentKeyInAndShipmentType(shipmentKeys, shipmentType);
    }

    @Override
    public void deletePlanByShipmentKey(String shipmentKey) {
        log.info("Delete tracking shipment milestone plan by shipmentKey: {}", shipmentKey);
        var rowsDeleted = shipmentMilestonePlanRepository.deleteByShipmentKey(shipmentKey);
        log.info("Tracking shipment milestones plan deleted : {}", rowsDeleted);
    }
    @Override
    public void deletePlan(ShipmentMilestonePlanDeletedVO event) {
        log.info("Delete tracking shipment milestone plan by event: {}", event);
        event.getPlanVOs().forEach(plan->{
           
            if(null!=plan.getFlightCarrierCode()) {
             var rowsDeleted = shipmentMilestonePlanRepository.deleteByShipmentKeyAndAirportCodeAndMilestoneCodeAndFlightCarrierCodeAndFlightNumberAndFlightDate(plan.getShipmentKey(),plan.getAirportCode(),plan.getMilestoneCode().getLabel(),plan.getFlightCarrierCode(),plan.getFlightNumber(),plan.getFlightDate());
             log.info("Tracking shipment milestones plan deleted : {}", rowsDeleted);
            }else {
             var   deletedRow = shipmentMilestonePlanRepository.deleteByShipmentKeyAndAirportCodeAndMilestoneCode(plan.getShipmentKey(),plan.getAirportCode(),plan.getMilestoneCode().getLabel());
             log.info("Tracking shipment milestones plan deleted : {}", deletedRow);
            }
            
            
        });
       
    }
    
    @Override
    public void deleteEventByShipmentKey(String shipmentKey) {
        log.info("Delete tracking shipment milestone events by shipmentKey: {}", shipmentKey);
        var rowsDeleted = shipmentMilestoneEventRepository.deleteByShipmentKey(shipmentKey);
        log.info("Tracking shipment milestones events deleted : {}", rowsDeleted);
    }

    @Override
    public void saveShipmentMilestonePlans(List<ShipmentMilestonePlan> shipmentPlan) {
        log.info("Save tracking shipment milestone plan: {}", shipmentPlan);
        shipmentMilestonePlanRepository.saveAll(shipmentPlan);
    }

    @Override
    public void saveShipmentMilestoneEvent(ShipmentMilestoneEvent event) {
        log.info("Save tracking shipment milestone event: {}", event.getShipmentKey());
        shipmentMilestoneEventRepository.save(event);
    }

    @Override
    @Cacheable(value = "milestoneMasterCache")
    public List<Milestone> findAllMilestones() {
        log.info("Find all milestones");
        return milestoneRepository.findAll();
    }
}
