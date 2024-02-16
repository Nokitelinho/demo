/*
 * EmptyULDsSessionImpl.java Created on August 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.EmptyULDsSession;

/**
 * @author A-2047
 *
 */
public class EmptyULDsSessionImpl extends AbstractScreenSession implements
		EmptyULDsSession {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.emptyulds";
	
	private static final String EMPTY_ULDS = "containerDetailsVOs";
	
	/**
	 * @return Screen Id
	 */
	public String getScreenID() {
		
		return SCREEN_ID;
	}

	/**
	 * @return Module Name
	 */
	public String getModuleName() {
		
		return MODULE_NAME;
	}
	
	/**
     * @return Collection<ContainerDetailsVO>
     */
	public Collection<ContainerDetailsVO> getContainerDetailsVOs() {
		return (Collection<ContainerDetailsVO>)getAttribute(EMPTY_ULDS);
	}
	
	/**
     * @param containerDetailsVOs
     */
	public void setContainerDetailsVOs(Collection<ContainerDetailsVO> containerDetailsVOs) {
		setAttribute(EMPTY_ULDS,(ArrayList<ContainerDetailsVO>)containerDetailsVOs);
	}

}