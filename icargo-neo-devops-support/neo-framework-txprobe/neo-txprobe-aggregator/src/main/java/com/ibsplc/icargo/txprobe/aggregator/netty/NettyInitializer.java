/*
 * NettyInitializer.java Created on 06-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.netty;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			06-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
@ApplicationScoped
public class NettyInitializer{

	static final Logger logger = LoggerFactory.getLogger(NettyInitializer.class);
	
	private final List<NettyComponent> nettyComponents;

	@Inject
	public NettyInitializer(Instance<NettyComponent> nettyComponents){
		this.nettyComponents = nettyComponents.stream().collect(Collectors.toList());
	}

	public void onStart(@Observes StartupEvent event) {
		for(NettyComponent c : nettyComponents){
			logger.info("starting netty component : " + c);
			c.start();
		}
	}
	
	public void onStop(@Observes ShutdownEvent event){
		for(NettyComponent c : nettyComponents){
			logger.info("stopping netty component : " + c);
			c.stop();
		}
	}

}
