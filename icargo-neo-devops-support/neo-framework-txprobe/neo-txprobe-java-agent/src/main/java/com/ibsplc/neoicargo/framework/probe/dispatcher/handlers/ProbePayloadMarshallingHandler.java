/*
 * ProbePayloadMarshaller.java Created on 29-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.dispatcher.handlers;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.neoicargo.framework.probe.*;
import com.ibsplc.neoicargo.framework.probe.camel.CamelProbePayloadMarshaller;
import com.ibsplc.neoicargo.framework.probe.dispatcher.HandlerState;
import com.ibsplc.neoicargo.framework.probe.dispatcher.HandlingStateAwareEventHanlder;
import com.ibsplc.neoicargo.framework.probe.http.HttpProbePayloadMarshaller;
import com.ibsplc.neoicargo.framework.probe.kafka.KafkaProbePayloadMarshaller;
import com.ibsplc.neoicargo.framework.probe.sql.SqlProbePayloadMarshaller;
import com.ibsplc.neoicargo.framework.probe.ws.WebServicePayloadMarshaller;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 * @ThreadSafe
 */
public class ProbePayloadMarshallingHandler implements HandlingStateAwareEventHanlder<PayloadHolder> {

    private TxProbeConfig config;
    private final ProbePayloadMarshaller httpMarshaller = new HttpProbePayloadMarshaller();
    private final ProbePayloadMarshaller sqlMarshaller = new SqlProbePayloadMarshaller();
    private final ProbePayloadMarshaller webserviceMarshaller = new WebServicePayloadMarshaller();
    private final ProbePayloadMarshaller kafkaMarshaller = new KafkaProbePayloadMarshaller();
    private final ProbePayloadMarshaller camelMarshaller = new CamelProbePayloadMarshaller();

    public ProbePayloadMarshallingHandler(TxProbeConfig config) {
        this.config = config;
    }

    /* (non-Javadoc)
     * @see com.lmax.disruptor.EventHandler#onEvent(java.lang.Object, long, boolean)
     */
    @Override
    public void onEvent(PayloadHolder event, long sequence, boolean endOfBatch) throws Exception {
        Probe probe = event.getProbe();
        ProbePayloadMarshaller marshaller = null;
        switch (probe) {
            case HTTP:
                marshaller = this.httpMarshaller;
                break;
            case SQL:
                marshaller = this.sqlMarshaller;
                break;
            case WEBSERVICE_HTTP:
            case WEBSERVICE_JMS:
                marshaller = this.webserviceMarshaller;
                break;
            case KAFKA:
                marshaller = this.kafkaMarshaller;
                break;
            case INTERFACE_MESSAGE:
                marshaller = this.camelMarshaller;
                break;
            default:
                break;
        }
        ProbePayload payload = marshaller.marshall(config, event.getPayload(), event.getProbeData());
        event.setPayload(payload);
        // loose the object handle
        event.setProbeData(null);
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.dispatcher.HandlingStateAwareEventHanlder#handleState()
     */
    @Override
    public HandlerState handleState() {
        return HandlerState.MARSHALL_PAYLOAD;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.dispatcher.HandlingStateAwareEventHanlder#stop()
     */
    @Override
    public void stop() {

    }
}
