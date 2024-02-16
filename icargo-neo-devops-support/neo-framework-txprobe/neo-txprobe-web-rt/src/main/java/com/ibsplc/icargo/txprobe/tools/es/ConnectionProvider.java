/*
 * ConnectionProvider.java Created on 03-Jan-2017
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.es;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			03-Jan-2017       		Jens J P 			First Draft
 */

import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author A-2394
 */
public interface ConnectionProvider {

    RestHighLevelClient connect(String url) throws Exception;

}
