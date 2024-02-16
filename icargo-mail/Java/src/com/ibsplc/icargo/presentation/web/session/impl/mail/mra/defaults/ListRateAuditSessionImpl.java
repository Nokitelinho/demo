/*
 * ListRateAuditSessionImpl.java Created on July 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;


import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListRateAuditSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * @author A-3108
 *
 */
public  class ListRateAuditSessionImpl extends AbstractScreenSession implements ListRateAuditSession{

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listrateaudit";
	private static final String KEY_ONETIME="onetimevalues";
	private static final String KEY_RATEAUDIT = "rateauditvos";
	private static final String KEY_RATEAUDITFILTERVO = "rateauditfiltervo";
	private static final String FROM_SCREEN = "fromScreen";
	
	public ListRateAuditSessionImpl() {
        super();

    }
	
	/**
	 * 
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
		// TODO Auto-generated method stub
		return getAttribute(KEY_ONETIME);
	}

	/**
	 * 
	 */
	public void setOneTimeValues(HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
		setAttribute(KEY_ONETIME,oneTimeValues);
		
	}

	/**
	 * 
	 */
	public void removeOneTimeValues() {
		removeAttribute(KEY_ONETIME);
		
	}
	/**
	 * 
	 */
	@Override
	public String getScreenID() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	/**
	 * 
	 */
	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return MODULE_NAME;
	}

	public void setRateAuditVOs(Page<RateAuditVO> rateAuditVOs) {
		setAttribute(KEY_RATEAUDIT,(Page<RateAuditVO>)rateAuditVOs);
		
	}

	public Page<RateAuditVO> getRateAuditVOs() {
		return (Page<RateAuditVO>)getAttribute(KEY_RATEAUDIT);
	}
	
	public void removeRateAuditVOs(){
		removeAttribute(KEY_RATEAUDIT);
	}
	 /**
	 * @return String
	 */
	public String getFromScreen(){
		return getAttribute(FROM_SCREEN);
	}
	
	/**
	 * @param closeStatus
	 */
	public void setFromScreen(String fromscreen){
		setAttribute(FROM_SCREEN, fromscreen);
	}
	public void setRateAuditFilterVO(RateAuditFilterVO rateAuditFilterVO) {
		setAttribute(KEY_RATEAUDITFILTERVO,rateAuditFilterVO);
		
	}

	public RateAuditFilterVO getRateAuditFilterVO() {
		return  getAttribute(KEY_RATEAUDITFILTERVO);
	}
	
	public void removeRateAuditFilterVO(){
		removeAttribute(KEY_RATEAUDITFILTERVO);
	}
}
