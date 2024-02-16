/*
 * TransportClientPool.java Created on 03-Jan-2017
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.es.www;

import com.ibsplc.icargo.txprobe.tools.es.ConnectionProvider;
import com.ibsplc.icargo.txprobe.tools.utils.ObjectPool;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			03-Jan-2017       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
public class TransportClientPool extends ObjectPool<RestHighLevelClient> {

    private final ConnectionProvider connector;
    private final String url;

    public TransportClientPool(ConnectionProvider connector, String url) {
        super(5);
        this.connector = connector;
        this.url = url;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.txprobe.aggregator.utils.ObjectPool#createNew()
     */
    @Override
    public RestHighLevelClient createNew() {
        try {
            return this.connector.connect(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.txprobe.aggregator.utils.ObjectPool#onOverflow(java.lang.Object)
     */
    @Override
    protected void onOverflow(RestHighLevelClient t) {
        try {
            t.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onOverflow(t);
    }


}
