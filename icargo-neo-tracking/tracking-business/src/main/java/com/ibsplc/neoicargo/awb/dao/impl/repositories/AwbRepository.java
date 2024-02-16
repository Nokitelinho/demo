package com.ibsplc.neoicargo.awb.dao.impl.repositories;

import com.ibsplc.neoicargo.awb.dao.entity.Awb;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AwbRepository extends CrudRepository<Awb, Long> {

    List<Awb> findByShipmentKeyIn(List<ShipmentKey> shipmentKeys);

    Optional<Awb> findByShipmentKey(ShipmentKey shipmentKey);
}
