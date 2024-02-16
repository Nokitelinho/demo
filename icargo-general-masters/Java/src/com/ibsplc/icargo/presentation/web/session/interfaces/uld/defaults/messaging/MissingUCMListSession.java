/*
 * MissingUCMListSession.java Created on Jul 29, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3459
 *
 */
public interface MissingUCMListSession extends ScreenSession{
	/**
	 * 
	 * @return screenID
	 */
	public String getScreenID();
	/**
	 * 
	 * @return modulename
	 */
	public String getModuleName();
	/**
	 * 
	 * @return
	 */
	public Page <ULDFlightMessageReconcileVO> getULDFlightMessageReconcileDetailsVOs();
	/**
	 * 
	 * @param reconcileVO
	 */
	public void setULDFlightMessageReconcileDetailsVOs(Page<ULDFlightMessageReconcileVO>reconcileVO);	
	/**
	 * 
	 */	
	public void removeULDFlightMessageReconcileDetailsVOs();	
	
	/**
	 * 
	 * @param totalRecords
	 */
	public void setTotalRecords(int totalRecords);
	/**
	 * 
	 * @return
	 */
	public Integer getTotalRecords();
	/**
	 * 
	 */
	public void removeTotalRecords();
	
}

