/*
 * LoggingEventHandler.java Created on 06-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.handler.inbound;

import com.ibsplc.icargo.txprobe.aggregator.PayloadHolder;
import com.lmax.disruptor.EventHandler;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			06-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
@Singleton
@Named("in.loggingEventHandler")
public class LoggingEventHandler implements EventHandler<PayloadHolder>{

	static final Logger logger = LoggerFactory.getLogger(LoggingEventHandler.class);
	
	private final String logData;

	@Inject
	public LoggingEventHandler(@ConfigProperty(name = "in.handler.logging.logData", defaultValue = "off") String logData){
		this.logData = logData;
	}

	/* (non-Javadoc)
	 * @see com.lmax.disruptor.EventHandler#onEvent(java.lang.Object, long, boolean)
	 */
	@Override
	public void onEvent(PayloadHolder event, long sequence, boolean endOfBatch) throws Exception {
		// minimal data
		if("size".equals(logData) && logger.isInfoEnabled()){
			logger.info("message compressed size : " + event.getSize());
		}
		if("body".equals(logData) && logger.isInfoEnabled()){
			StringBuilder sbul = new StringBuilder(1000);
			Object[][] data = event.getData();
			for(int x = 0 ; x < data.length; x++){
				String name = data[x][0].toString();
				Object value = data[x][1];
				if(sbul.length() == 0){
					sbul.append("[ ");
				}else
					sbul.append(", ");
				sbul.append(name).append(" \'").append(value).append("\'");
			}
			sbul.append(" ]");
			logger.info(sbul.toString());
		}
	}

	
}
