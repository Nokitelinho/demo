/*
 * BackEndHandler.java Created on 01-Feb-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.handler.outbound;

import com.ibsplc.icargo.txprobe.api.ProbeDataHolder;
import com.lmax.disruptor.EventHandler;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			01-Feb-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public interface BackEndHandler extends EventHandler<ProbeDataHolder>{

}
