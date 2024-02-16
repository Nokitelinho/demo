package com.ibsplc.neoicargo.datamonitor.scheduler;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.ibsplc.neoicargo.datamonitor.DataMonitorController;
import com.ibsplc.neoicargo.datamonitor.jdbc.NativeRepository;
import com.ibsplc.neoicargo.datamonitor.model.DataMonitorConfig;
import com.ibsplc.neoicargo.datamonitor.model.Message;
import com.ibsplc.neoicargo.datamonitor.notify.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataMonitorGenericTask extends DataMonitorAbstractTask {

	public DataMonitorGenericTask(NotificationService notificationService, DataMonitorConfig config, NativeRepository repository) {
		super(notificationService, config, repository);
	}

	@Override
	public void run() {
		log.info("Sqlquery : {}", config.getSqlquery());
		List<Map<String, Object>> list = repository.queryForList(config.getSqlquery(), config.getModule(), config.isCqrs());
		if (CollectionUtils.isNotEmpty(list)) {
			log.info("No.of records found : {}", list.size());
			Message msg = constructMessage(list);
			msg.setStatus(config.getId());
			notificationService.notifyListeners(config.getId(), msg);
		}
	}
	

}
