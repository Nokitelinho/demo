package com.ibsplc.neoicargo.framework.probe;

import javax.management.MXBean;

/*
 * 
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			20-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
@MXBean
public interface TxProbeConfigMXBean {

	String DOMAIN = "com.ibsplc.xibase";
	String TYPE = "txProbeConfig";
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#isEnabled()
	 */
	boolean isEnabled();

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#setEnabled(boolean)
	 */
	void setEnabled(boolean enabled);

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#isEnableHttpProbing()
	 */
	boolean isEnableHttpProbing();

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#setEnableHttpProbing(boolean)
	 */
	void setEnableHttpProbing(boolean enableHttpProbing);

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#isEnableJmsWebServiceProbing()
	 */
	boolean isEnableJmsWebServiceProbing();

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#setEnableJmsWebServiceProbing(boolean)
	 */
	void setEnableJmsWebServiceProbing(boolean enableJmsWebServiceProbing);

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#isEnableHttpWebServiceProbing()
	 */
	boolean isEnableHttpWebServiceProbing();

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#setEnableHttpWebServiceProbing(boolean)
	 */
	void setEnableHttpWebServiceProbing(boolean enableHttpWebServiceProbing);

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#isEnableSqlProbing()
	 */
	boolean isEnableSqlProbing();

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#setEnableSqlProbing(boolean)
	 */
	void setEnableSqlProbing(boolean enableSqlProbing);

	void setEnableCamelProbing(boolean enableCamelProbing);

	boolean isEnableCamelProbing();

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#getEnabledUsers()
	 */
	String[] getEnabledUsers();

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#setEnabledUsers(java.lang.String[])
	 */
	void setEnabledUsers(String[] enabledUsers);

	/* 
	 * @return
	 */
	String[] getDisabledUsers();

	void setDisabledUsers(String[] disabledUsers);
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#getEnabledModules()
	 */
	String[] getEnabledModules();

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#setEnabledModules(java.lang.String[])
	 */
	void setEnabledModules(String[] enabledModules);

	String[] getDisabledModules();

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeConfigurationMXBean#setDisabledModules(java.lang.String[])
	 */
	void setDisabledModules(String[] disabledModules);

	/**
	 * Return the tenant id for this config
	 * @return
	 */
	String getTenant();

}