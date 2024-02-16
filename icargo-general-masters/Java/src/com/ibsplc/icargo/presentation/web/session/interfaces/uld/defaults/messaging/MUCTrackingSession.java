/*
 * MUCTrackingSession.java Created on Aug 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDConfigAuditVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-3045
 *
 */
public interface MUCTrackingSession extends ScreenSession {
		
	/**
	 *
	 * /** Method to get ScreenID
	 *
	 * @return ScreenID
	 */
	public String getScreenID();

	/**
	 * Method to get ProductName
	 *
	 * @return ProductName
	 */
	public String getModuleName();

	/**
	 * @return Returns the oneTimeValues.
	 */
	public HashMap<String,Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */
	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	
	/**
	 * @return Returns the ULDListVO.
	 */
	public TransactionListVO getListDisplayColl();
	/**
	 * @param uldListVOs The uldListVOs to set.
	 */
	public void setListDisplayColl(TransactionListVO transactionListVO);

	/**
	 * @return Returns the listFilterVO.
	 */
	public TransactionFilterVO getListFilterVO();

	/**
	 * @param listFilterVO The listFilterVO to set.
	 */
	public void setListFilterVO(TransactionFilterVO listFilterVO);
	
	/**
	 * @return Returns the lovVO.
	 */
	public Page<String> getLovVO();

	/**
	 * @param lovVO The lovVO to set.
	 */
	public void setLovVO(Page<String> lovVO); 
	
	public Collection<OneTimeVO>  getConditionCodes();
	
	public void setConditionCodes(Collection<OneTimeVO> conditionCodes);
	
	public Collection<ULDConfigAuditVO> getConfigAuditColl();
	
	public void setConfigAuditColl(Collection<ULDConfigAuditVO> auditVOs);
	
	/**
	 * @return Returns the listStatus.
	 */
	String getListStatus();

	/**
	 * @param listStatus The listStatus to set.
	 */
	void setListStatus(String listStatus);

}
