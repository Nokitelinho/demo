package com.ibsplc.neoicargo.tracking.dao.impl.repositories;

import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestoneEvent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShipmentMilestoneEventRepository extends CrudRepository<ShipmentMilestoneEvent, Long> {
    List<ShipmentMilestoneEvent> findByShipmentKeyInAndShipmentType(List<String> shipmentKeys, String shipmentType);

    long deleteByShipmentKey(String shipmentKey);
}
