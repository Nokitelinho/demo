package com.ibsplc.neoicargo.awb.dao.impl.repositories;

import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotification;
import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotificationKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AwbUserNotificationRepository extends CrudRepository<AWBUserNotification, AWBUserNotificationKey> {

    List<AWBUserNotification> findByNotificationsKeyTrackingAwbSerialNumber(long awbSerialNumber);

}
