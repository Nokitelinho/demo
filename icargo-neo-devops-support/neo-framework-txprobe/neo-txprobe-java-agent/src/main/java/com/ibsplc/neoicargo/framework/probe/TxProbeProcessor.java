/*
 * TxProbeProcessor.java Created on 29-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.neoicargo.framework.probe.camel.CamelProbePayloadEnableEvaluator;
import com.ibsplc.neoicargo.framework.probe.http.HttpProbeEnableEvaluator;
import com.ibsplc.neoicargo.framework.probe.kafka.KafkaProbeEnableEvaluator;
import com.ibsplc.neoicargo.framework.probe.sql.SqlProbeEnableEvaluator;
import com.ibsplc.neoicargo.framework.probe.sql.spy.agents.DummySqlAgent;
import com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SpyAgentFactory;
import com.ibsplc.neoicargo.framework.probe.ws.WebServiceProbeEnableEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
public class TxProbeProcessor {

    static final Logger logger = LoggerFactory.getLogger(TxProbeProcessor.class);

    private TxProbeConfig config;
    private Dispatcher dispatcher;
    private final TxProbeEnabledEvaluator httpProbeEvaluator = new HttpProbeEnableEvaluator();
    private final TxProbeEnabledEvaluator sqlProbeEvaluator = new SqlProbeEnableEvaluator();
    private final TxProbeEnabledEvaluator webServiceProbeEvaluator = new WebServiceProbeEnableEvaluator();
    private final TxProbeEnabledEvaluator kafkaProbeEvaluator = new KafkaProbeEnableEvaluator();
    private final TxProbeEnabledEvaluator camelProbeEvaluator = new CamelProbePayloadEnableEvaluator();

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    static {
        // probe specific initialization routines
        SpyAgentFactory.setSpyAgent(new DummySqlAgent());
    }

    /**
     * @param config
     */
    public TxProbeProcessor(TxProbeConfig config) {
        this.config = config;
    }

    @PostConstruct
    public void init() {
        this.dispatcher = this.config.getDispatcher();
        if (this.dispatcher != null && this.config.isEnabled())
            this.dispatcher.start(config);
        this.config.setProbeStateChangeListener(new DispatcherActivationListener());
        logger.info("txProbe initialization complete.");
    }

    @PreDestroy
    public void destroy() {
        if (this.scheduledThreadPoolExecutor != null)
            this.scheduledThreadPoolExecutor.shutdownNow();
        if (this.dispatcher != null)
            this.dispatcher.stop();

    }

    private boolean dispatch(boolean eager, ProbePayload payload, Object... probeData) {
        PayloadHolder holder = new PayloadHolder();
        holder.setPayload(payload);
        holder.setProbe(payload.getProbe());
        holder.setProbeState(payload.getProbeState());
        holder.setProbeData(probeData);
        // add the thread name for tracking concurrent txns
        Thread thread = Thread.currentThread();
        String threadName = new StringBuilder(thread.getName()).append(':').append(thread.getId()).toString();
        payload.setThreadName(threadName);
        payload.setTenant(this.config.getTenant());
        payload.setService(this.config.getServiceName());
        payload.setVersion(this.config.getVersion());
        return eager ? this.dispatcher.dispatchEager(holder) : this.dispatcher.dispatch(holder);
    }

    public boolean doDispatchEager(ProbePayload payload, Object... probeData) {
        boolean answer = isProbeEnabled(payload, probeData);
        return answer && doDispatchEagerForced(payload, probeData);
    }

    public boolean doDispatchEagerForced(ProbePayload payload, Object... probeData) {
        return dispatch(true, payload, probeData);
    }

    public boolean doDispatch(ProbePayload payload, Object... probeData) {
        boolean answer = isProbeEnabled(payload, probeData);
        return answer && doDispatchForced(payload, probeData);
    }

    public boolean doDispatchForced(ProbePayload payload, Object... probeData) {
        return dispatch(false, payload, probeData);
    }

    public boolean isProbeEnabled(ProbePayload payload, Object... probeData) {
        if (!isProbeEnabled(payload.getProbe()))
            return false;
        switch (payload.getProbe()) {
            case HTTP:
                return httpProbeEvaluator.isProbeEnabled(this.config, payload, probeData);
            case SQL:
                return sqlProbeEvaluator.isProbeEnabled(this.config, payload, probeData);
            case WEBSERVICE_HTTP:
            case WEBSERVICE_JMS:
                return webServiceProbeEvaluator.isProbeEnabled(this.config, payload, probeData);
            case KAFKA:
                return kafkaProbeEvaluator.isProbeEnabled(this.config, payload, probeData);
            case INTERFACE_MESSAGE:
                return camelProbeEvaluator.isProbeEnabled(this.config, payload, probeData);
            default:
                break;
        }
        return false;
    }

    /**
     * Method to check whether the probe is enabled or not. This method does
     * a best effort check and the result could vary with additional information.
     *
     * @param probe
     * @return
     */
    public boolean isProbeEnabled(Probe probe) {
        if (!config.isEnabled() || !this.dispatcher.isReady())
            return false;
        switch (probe) {
            case HTTP:
                return config.isEnableHttpProbing();
            case SQL:
                return config.isEnableSqlProbing();
            case WEBSERVICE_HTTP:
                return config.isEnableHttpWebServiceProbing();
            case WEBSERVICE_JMS:
                return config.isEnableJmsWebServiceProbing();
            case KAFKA:
                return config.isEnableKafkaProbing();
            case INTERFACE_MESSAGE:
                return config.isEnableCamelProbing();
            default:
                return false;
        }
    }

    /**
     * @return the config
     */
    public TxProbeConfig getConfig() {
        return config;
    }

    /**
     * @return the dispatcher
     */
    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    /**
     * Method to generate unique random Ids.
     *
     * @return
     */
    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    private class DispatcherActivationListener implements PropertyChangeListener {

        /* (non-Javadoc)
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (TxProbeProcessor.this.dispatcher == null)
                return;
            boolean enabled = (Boolean) evt.getNewValue();
            if (enabled)
                TxProbeProcessor.this.dispatcher.start(config);
            else
                TxProbeProcessor.this.dispatcher.stop();
        }

    }

}
