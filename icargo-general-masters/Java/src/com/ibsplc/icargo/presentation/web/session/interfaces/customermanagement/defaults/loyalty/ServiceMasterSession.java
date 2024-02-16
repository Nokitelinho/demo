/*
 * ListLoyaltySession.java Created on Apr 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty;


import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.CustomerServicesVO;
import com.ibsplc.icargo.framework.session.ScreenSession;


/**
 * @author A-2052
 *
 */
public interface ServiceMasterSession extends ScreenSession {
	    
	
	public void setService(String service);
	
	public String getService();
	
	public CustomerServicesVO getCustomerServicesVO();
	
	public void setCustomerServicesVO(CustomerServicesVO customerServicesVO);
   
}
