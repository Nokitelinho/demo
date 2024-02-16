/*
 * MaintainPrivilegeSessionInterface.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults;

import java.util.Collection;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
/**
 * @author A-1366
 *
 */
public interface ProductPerformanceSession extends ScreenSession {

    
	
	public Collection<OneTimeVO>  getPriority();
	/**
	 * Method for setting priority to session
	 * @param priority
	 */
	public void setPriority(Collection<OneTimeVO> priority);
	/**
	 * method for removing priority from session
	 *
	 */
	public void removePriority();
	/**
	 * Method for getting transportmode from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getTransportMode();
	/**
	 * Method for setting transportmode to session
	 * @param transportMode
	 */
	public void setTransportMode(Collection<OneTimeVO> transportMode);
	/**
	 * Method for removing transportmode from session
	 *
	 */
	public void removeTransportMode();
	

}
