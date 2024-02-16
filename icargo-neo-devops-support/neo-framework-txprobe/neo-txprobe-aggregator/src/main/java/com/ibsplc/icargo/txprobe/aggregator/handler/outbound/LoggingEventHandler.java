/*
 * LoggingEventHandler.java Created on 29-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.handler.outbound;

import com.ibsplc.icargo.txprobe.api.ProbeDataHolder;
import com.lmax.disruptor.EventHandler;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
@Singleton
@Named("out.loggingEventHandler")
public class LoggingEventHandler implements EventHandler<ProbeDataHolder>{

	static final Logger logger = LoggerFactory.getLogger(LoggingEventHandler.class);
	
	private final String logData;

	public LoggingEventHandler(@ConfigProperty(name = "out.handler.logging.logData", defaultValue = "off") String logData){
		this.logData = logData;
	}

	/* (non-Javadoc)
	 * @see com.lmax.disruptor.EventHandler#onEvent(java.lang.Object, long, boolean)
	 */
	@Override
	public void onEvent(ProbeDataHolder event, long sequence, boolean endOfBatch) throws Exception {
		if("all".equals(logData) && logger.isInfoEnabled()){
			logger.info(event.getProbeData().toString());
		}
	}

}
