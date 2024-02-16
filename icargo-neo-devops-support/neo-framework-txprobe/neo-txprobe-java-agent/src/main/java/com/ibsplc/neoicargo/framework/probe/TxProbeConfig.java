/*
 * TxProbeConfig.java Created on 28-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe;

import com.ibsplc.neoicargo.framework.probe.camel.CamelProbeConfig;
import com.ibsplc.neoicargo.framework.probe.http.HttpProbeConfig;
import com.ibsplc.neoicargo.framework.probe.kafka.KafkaProbeConfig;
import com.ibsplc.neoicargo.framework.probe.sql.SqlProbeConfig;
import com.ibsplc.neoicargo.framework.probe.ws.WebServiceProbeConfig;
import lombok.ToString;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			28-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
@ToString
public class TxProbeConfig implements TxProbeConfigMXBean {

    private boolean enabled;
    private boolean enableHttpProbing;
    private boolean enableJmsWebServiceProbing;
    private boolean enableHttpWebServiceProbing;
    private boolean enableSqlProbing;
    private boolean enableKafkaProbing;

    private boolean enableCamelProbing;

    private String tenant;
    private String version;
    private String serviceName;
    private ObjectProvider<ApplicationContext> applicationContext;

    private Dispatcher dispatcher;
    private HttpProbeConfig httpProbeConfig;
    private WebServiceProbeConfig webServiceProbeConfig;
    private AggregationServerConfig aggregationServerConfig;
    private SqlProbeConfig sqlProbeConfig;
    private KafkaProbeConfig kafkaProbeConfig;

    private CamelProbeConfig camelProbeConfig;

    private String[] enabledUsers;
    private String[] disabledUsers;
    private String[] enabledModules;
    private String[] disabledModules;

    private PropertyChangeListener probeStateChangeListener;

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "enabled", this.enabled, enabled);
        this.enabled = enabled;
        if (probeStateChangeListener != null && !event.getNewValue().equals(event.getOldValue()))
            probeStateChangeListener.propertyChange(event);
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#isEnableHttpProbing()
     */
    @Override
    public boolean isEnableHttpProbing() {
        return enableHttpProbing;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#setEnableHttpProbing(boolean)
     */
    @Override
    public void setEnableHttpProbing(boolean enableHttpProbing) {
        this.enableHttpProbing = enableHttpProbing;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#isEnableJmsWebServiceProbing()
     */
    @Override
    public boolean isEnableJmsWebServiceProbing() {
        return enableJmsWebServiceProbing;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#setEnableJmsWebServiceProbing(boolean)
     */
    @Override
    public void setEnableJmsWebServiceProbing(boolean enableJmsWebServiceProbing) {
        this.enableJmsWebServiceProbing = enableJmsWebServiceProbing;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#isEnableHttpWebServiceProbing()
     */
    @Override
    public boolean isEnableHttpWebServiceProbing() {
        return enableHttpWebServiceProbing;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#setEnableHttpWebServiceProbing(boolean)
     */
    @Override
    public void setEnableHttpWebServiceProbing(boolean enableHttpWebServiceProbing) {
        this.enableHttpWebServiceProbing = enableHttpWebServiceProbing;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#isEnableSqlProbing()
     */
    @Override
    public boolean isEnableSqlProbing() {
        return enableSqlProbing;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#setEnableSqlProbing(boolean)
     */
    @Override
    public void setEnableSqlProbing(boolean enableSqlProbing) {
        this.enableSqlProbing = enableSqlProbing;
    }

    @Override
    public void setEnableCamelProbing(boolean enableCamelProbing) {
        this.enableCamelProbing = enableCamelProbing;
    }

    @Override
    public boolean isEnableCamelProbing() {
        return this.enableCamelProbing;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#getEnabledUsers()
     */
    @Override
    public String[] getEnabledUsers() {
        return enabledUsers;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#setEnabledUsers(java.lang.String[])
     */
    @Override
    public void setEnabledUsers(String[] enabledUsers) {
        if (enabledUsers != null)
            Arrays.sort(enabledUsers);
        this.enabledUsers = enabledUsers;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#getDisabledUsers()
     */
    @Override
    public String[] getDisabledUsers() {
        return disabledUsers;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#setDisabledUsers(java.lang.String[])
     */
    @Override
    public void setDisabledUsers(String[] disabledUsers) {
        if (disabledUsers != null)
            Arrays.sort(disabledUsers);
        this.disabledUsers = disabledUsers;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#getEnabledModules()
     */
    @Override
    public String[] getEnabledModules() {
        return enabledModules;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#setEnabledModules(java.lang.String[])
     */
    @Override
    public void setEnabledModules(String[] enabledModules) {
        if (enabledModules != null)
            Arrays.sort(enabledModules);
        this.enabledModules = enabledModules;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#getDisabledModules()
     */
    @Override
    public String[] getDisabledModules() {
        return disabledModules;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#setDisabledModules(java.lang.String[])
     */
    @Override
    public void setDisabledModules(String[] disabledModules) {
        if (disabledModules != null)
            Arrays.sort(disabledModules);
        this.disabledModules = disabledModules;
    }

    /**
     * @return the dispatcher
     */
    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeConfigMXBean#setDispatcher(com.ibsplc.icargo.framework.probe.Dispatcher)
     */
    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    /**
     * @return the httpProbeConfig
     */
    public HttpProbeConfig getHttpProbeConfig() {
        return httpProbeConfig;
    }

    /**
     * @param httpProbeConfig the httpProbeConfig to set
     */
    public void setHttpProbeConfig(HttpProbeConfig httpProbeConfig) {
        this.httpProbeConfig = httpProbeConfig;
    }

    /**
     * @return the aggregationServerConfig
     */
    public AggregationServerConfig getAggregationServerConfig() {
        return aggregationServerConfig;
    }

    /**
     * @param aggregationServerConfig the aggregationServerConfig to set
     */
    public void setAggregationServerConfig(AggregationServerConfig aggregationServerConfig) {
        this.aggregationServerConfig = aggregationServerConfig;
    }

    /**
     * @return the webServiceProbeConfig
     */
    public WebServiceProbeConfig getWebServiceProbeConfig() {
        return webServiceProbeConfig;
    }

    /**
     * @param webServiceProbeConfig the webServiceProbeConfig to set
     */
    public void setWebServiceProbeConfig(WebServiceProbeConfig webServiceProbeConfig) {
        this.webServiceProbeConfig = webServiceProbeConfig;
    }

    /**
     * @return the sqlProbeConfig
     */
    public SqlProbeConfig getSqlProbeConfig() {
        return sqlProbeConfig;
    }

    /**
     * @param sqlProbeConfig the sqlProbeConfig to set
     */
    public void setSqlProbeConfig(SqlProbeConfig sqlProbeConfig) {
        this.sqlProbeConfig = sqlProbeConfig;
    }

    /**
     * @return the probeStateChangeListener
     */
    public PropertyChangeListener getProbeStateChangeListener() {
        return probeStateChangeListener;
    }

    /**
     * @param probeStateChangeListener the probeStateChangeListener to set
     */
    public void setProbeStateChangeListener(PropertyChangeListener probeStateChangeListener) {
        this.probeStateChangeListener = probeStateChangeListener;
    }

    @Override
    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext.getIfAvailable();
    }

    public void setApplicationContextProvider(ObjectProvider<ApplicationContext> applicationContext) {
        this.applicationContext = applicationContext;
    }

    public boolean isEnableKafkaProbing() {
        return enableKafkaProbing;
    }

    public void setEnableKafkaProbing(boolean enableKafkaProbing) {
        this.enableKafkaProbing = enableKafkaProbing;
    }

    public KafkaProbeConfig getKafkaProbeConfig() {
        return kafkaProbeConfig;
    }

    public void setKafkaProbeConfig(KafkaProbeConfig kafkaProbeConfig) {
        this.kafkaProbeConfig = kafkaProbeConfig;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public CamelProbeConfig getCamelProbeConfig() {
        return camelProbeConfig;
    }

    public void setCamelProbeConfig(CamelProbeConfig camelProbeConfig) {
        this.camelProbeConfig = camelProbeConfig;
    }
}
