package com.ibsplc.neoicargo.datamonitor.notify;

import com.ibsplc.neoicargo.datamonitor.model.Message;

public interface NotificationListener {

	void setUrl(String url);
	
	void sendNotification(Message message);
}
