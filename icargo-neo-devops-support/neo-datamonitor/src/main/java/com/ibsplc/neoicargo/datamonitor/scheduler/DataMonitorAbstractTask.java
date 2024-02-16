package com.ibsplc.neoicargo.datamonitor.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.neoicargo.datamonitor.jdbc.NativeRepository;
import com.ibsplc.neoicargo.datamonitor.model.Alert;
import com.ibsplc.neoicargo.datamonitor.model.DataMonitorConfig;
import com.ibsplc.neoicargo.datamonitor.model.Message;
import com.ibsplc.neoicargo.datamonitor.notify.NotificationService;

public abstract class DataMonitorAbstractTask implements Runnable {

	protected NotificationService notificationService;
	protected DataMonitorConfig config;
	protected NativeRepository repository;

	/*
	 * The default constructor is made private to mandate the implementation of
	 * parameterized constructor in the subclasses.
	 */
	@SuppressWarnings("unused")
	private DataMonitorAbstractTask() {

	}

	protected DataMonitorAbstractTask(NotificationService service, DataMonitorConfig data, NativeRepository repository) {
		this.config = data;
		this.notificationService = service;
		this.repository = repository;
	}

	protected Message constructMessage(Map<String, Object> map) {
		Message msg = new Message();
		msg.setStatus("Firing");
		msg.setAlerts(new ArrayList<>());
		Alert a = new Alert();
		setAnnotations(a);
		a.setLabels(new HashMap<String, String>());
		for (String key : map.keySet()) {
			a.getLabels().put(key, map.get(key).toString());
		}
		msg.getAlerts().add(a);
		return msg;
	}

	protected Message constructMessage(List<Map<String, Object>> list) {
		Message msg = null;
		for (Map<String, Object> map : list) {
			if(msg == null) {
				msg = constructMessage(map);
			}else {
				Message m = constructMessage(map);
				msg.getAlerts().addAll(m.getAlerts());
			}
		}
		return msg;
	}
	
	private void setAnnotations(Alert a) {
		a.setAnnotations(new HashMap<String, String>());
		a.getAnnotations().put("description", config.getDescription());
		a.getAnnotations().put("message", config.getDescription());
	}
}
