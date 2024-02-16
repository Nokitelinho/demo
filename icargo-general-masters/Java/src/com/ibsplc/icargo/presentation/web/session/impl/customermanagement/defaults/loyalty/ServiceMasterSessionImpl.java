/*
 * ServiceMasterSessionImpl.java Created on Apr 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.customermanagement.defaults.loyalty;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.CustomerServicesVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ServiceMasterSession;


/**
 * @author A-2052
 *
 */

public class ServiceMasterSessionImpl extends AbstractScreenSession
		implements ServiceMasterSession {

	private static final String MODULE = "customermanagement.defaults";
	private static final String SCREENID ="customermanagement.defaults.servicemaster";
	private static final String KEY_CUSTOMERSERVICESVO = "CustomerServicesVO";
	private static final String KEY_SERVICE = "service";
	/**
	 * 
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * 
	 */
	public String getModuleName() {
		return MODULE;
	}
	
		/**
		 * @param service
		 */ 
	public void setService(String service) {
		setAttribute(KEY_SERVICE, service);
	}
	/**
	 * 
	 */
	public String getService() {
		return getAttribute(KEY_SERVICE);
	}
/**
 * 
 */
	public CustomerServicesVO getCustomerServicesVO() {
		return (CustomerServicesVO)getAttribute(KEY_CUSTOMERSERVICESVO);
	}
	/**
	 * @param customerServicesVO
	 */
	public void setCustomerServicesVO(CustomerServicesVO customerServicesVO) {
		 setAttribute(KEY_CUSTOMERSERVICESVO,customerServicesVO);
	}

}
