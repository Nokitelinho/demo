package com.ibsplc.neoicargo.datamonitor.notify;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.ibsplc.neoicargo.datamonitor.model.Message;

@Service
public class NotificationService {

	private List<NotificationListener> notificationList;

	public void notifyListeners(String id, Message message) {
		for (NotificationListener listener : CollectionUtils.emptyIfNull(notificationList)) {
			listener.sendNotification(message);
		}
	}

	public void addNotificationListener(NotificationListener listener) {
		if (notificationList == null) {
			notificationList = new ArrayList<>();
		}
		notificationList.add(listener);
	}

}
