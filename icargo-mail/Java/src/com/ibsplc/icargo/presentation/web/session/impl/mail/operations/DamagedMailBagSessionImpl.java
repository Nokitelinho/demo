/*
 * DamagedMailBagSessionImpl.java Created on July 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DamagedMailBagSession;

/**
 * @author A-2047
 *
 */
public class DamagedMailBagSessionImpl extends AbstractScreenSession implements
		DamagedMailBagSession {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.damagedmailbag";
	
	private static final String DAMAGED_MAILBAG_VOS = "damagedMailbagVOs";
	private static final String MAILBAG_ID = "mailBagId";
	
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
     * @return Collection<DamagedMailbagVO>
     */
	public Collection<DamagedMailbagVO> getDamagedMailbagVOs() {
		return (Collection<DamagedMailbagVO>)getAttribute(DAMAGED_MAILBAG_VOS);
	}
	
	/**
     * @param damagedMailbagVOs
     */
	public void setDamagedMailbagVOs(Collection<DamagedMailbagVO> damagedMailbagVOs) {
		setAttribute(DAMAGED_MAILBAG_VOS,(ArrayList<DamagedMailbagVO>)damagedMailbagVOs);
	}
	
	 /**
     * @return String
     */
	public String getMailBagId(){
		return getAttribute(MAILBAG_ID);
    }
    
    /**
     * @param mailBagId
     */
	public void setMailBagId(String mailBagId){
		setAttribute(MAILBAG_ID,mailBagId);
    }

}
