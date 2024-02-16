package com.ibsplc.neoicargo.awb.dao;

import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotification;
import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotificationKey;
import com.ibsplc.neoicargo.awb.dao.entity.Awb;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;

import java.util.List;
import java.util.Optional;

public interface AwbDAO {

    void saveAwb(Awb awb);

    void deleteAwb(Awb awb);

    List<Awb> findAwbByShipmentKeys(List<ShipmentKey> shipmentKeys);

    Optional<Awb> findAwbByShipmentKey(ShipmentKey shipmentKey);

    Optional<AWBUserNotification> findAwbUserNotificationByKey(AWBUserNotificationKey key);

    List<AWBUserNotification> findAwbUserNotificationsByAwbSerialNumber(Long trackingAwbSerialNumber);

    void saveAwbUserNotification(AWBUserNotification notification);

    void deleteAwbUserNotification(AWBUserNotification notification);
}
