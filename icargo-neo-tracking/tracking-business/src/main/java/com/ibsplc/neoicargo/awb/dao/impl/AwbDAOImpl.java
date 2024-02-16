package com.ibsplc.neoicargo.awb.dao.impl;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotification;
import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotificationKey;
import com.ibsplc.neoicargo.awb.dao.entity.Awb;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.dao.impl.repositories.AwbRepository;
import com.ibsplc.neoicargo.awb.dao.impl.repositories.AwbUserNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwbDAOImpl implements AwbDAO {

    private final AwbRepository awbRepository;
    private final AwbUserNotificationRepository awbUserNotificationRepository;

    @Override
    public void saveAwb(Awb awb) {
        log.info("Save tracking awb master: {}", awb);
        awbRepository.save(awb);
    }

    @Override
    public void deleteAwb(Awb awb) {
        log.info("Delete tracking awb master: {}", awb.getShipmentKey().toString());
        awbRepository.delete(awb);
    }

    @Override
    public List<Awb> findAwbByShipmentKeys(List<ShipmentKey> shipmentKeys) {
        log.info("Find tracking awb master by shipmentKeys: {}", shipmentKeys);
        return awbRepository.findByShipmentKeyIn(shipmentKeys);
    }

    @Override
    public Optional<Awb> findAwbByShipmentKey(ShipmentKey shipmentKey) {
        log.info("Find tracking awb master by shipmentKey: {}", shipmentKey);
        return awbRepository.findByShipmentKey(shipmentKey);
    }

    @Override
    public Optional<AWBUserNotification> findAwbUserNotificationByKey(AWBUserNotificationKey key) {
        log.info("Find awb user notification by key: {}", key);
        return awbUserNotificationRepository.findById(key);
    }

    @Override
    public List<AWBUserNotification> findAwbUserNotificationsByAwbSerialNumber(Long trackingAwbSerialNumber) {
        log.info("Find awb user notifications by trackingAwbSerialNumber: {}", trackingAwbSerialNumber);
        return awbUserNotificationRepository.findByNotificationsKeyTrackingAwbSerialNumber(trackingAwbSerialNumber);
    }

    @Override
    public void saveAwbUserNotification(AWBUserNotification notification) {
        log.info("Saving awb user notification : {}", notification);
        awbUserNotificationRepository.save(notification);
    }

    @Override
    public void deleteAwbUserNotification(AWBUserNotification notification) {
        log.info("Deleting awb user notification with key: {}", notification.getNotificationsKey());
        awbUserNotificationRepository.delete(notification);
    }

}
