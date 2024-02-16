/*
 * MissingUCMListSessionImpl.java Created on Jul 29, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.messaging;


import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MissingUCMListSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3459
 *
 */
public class MissingUCMListSessionImpl  extends AbstractScreenSession 
	implements MissingUCMListSession{
	
	private static final String KEY_MODULE_NAME = "uld.defaults";
    private static final String KEY_SCREEN_ID = "uld.defaults.missingucmlist";
    private static final String KEY_UCMMSGLST = "ULDFlightMessageReconcileDetailsVOs";
    private static final String KEY_TOTALRECORDS = "totalRecords";
    /**
	 * 
	 * /** Method to get ScreenID
	 * 
	 * @return ScreenID
	 */
	public String getScreenID() {
		return KEY_SCREEN_ID;
	}

	/**
	 * Method to get ProductName
	 * 
	 * @return ProductName
	 */
	public String getModuleName() {
		return KEY_MODULE_NAME;
	}
	/**
	 * 
	 * @return
	 */
	public Page<ULDFlightMessageReconcileVO> getULDFlightMessageReconcileDetailsVOs(){
	   return (Page<ULDFlightMessageReconcileVO>) 
	   		getAttribute(KEY_UCMMSGLST);
	}
	/**
	 * @param uldDamageChecklistVO
	 *            The ULDDamageChecklistVO to set.
	 */
	public void setULDFlightMessageReconcileDetailsVOs(Page<ULDFlightMessageReconcileVO> reconcileVO){
	   setAttribute(
			   KEY_UCMMSGLST, (Page<ULDFlightMessageReconcileVO>)reconcileVO);
	}
	/**
	 * 
	 *
	 */
	public void removeULDFlightMessageReconcileDetailsVOs() {
		removeAttribute(KEY_UCMMSGLST);	
	}

	@Override
	public void setTotalRecords(int totalRecords) {
		setAttribute(KEY_TOTALRECORDS, Integer.valueOf(totalRecords));
		
	}

	@Override
	public Integer getTotalRecords() {
		return ((Integer)getAttribute(KEY_TOTALRECORDS));
	}

	@Override
	public void removeTotalRecords() {
		removeAttribute(KEY_TOTALRECORDS);
		
	}
}
