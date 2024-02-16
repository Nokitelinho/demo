/*
 * MailSubClassMasterSessionImpl.java Created on June 08, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailSubClassMasterSession;

/**
 * @author A-2047
 *
 */
public class MailSubClassMasterSessionImpl extends AbstractScreenSession
		implements MailSubClassMasterSession {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.masters.subclass";
	
	private static final String SUB_CLASS_VOS = "mailSubClassVOs";
	private static final String SUB_CLASS_ONETIME = "mailSubClassOneTimes";
	
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
     * @return Collection<MailSubClassVO>
     */
	public Collection<MailSubClassVO> getMailSubClassVOs() {
		return (Collection<MailSubClassVO>)getAttribute(SUB_CLASS_VOS);
	}
	
	/**
     * @param mailSubClassVOs
     */
	public void setMailSubClassVOs(Collection<MailSubClassVO> mailSubClassVOs) {
		setAttribute(SUB_CLASS_VOS,(ArrayList<MailSubClassVO>)mailSubClassVOs);
	}

	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getSubClassGroups(){

		return getAttribute(SUB_CLASS_ONETIME);

	}

	/**
	 * @param SubClassGroups
	 */
	public void setSubClassGroups(HashMap<String, Collection<OneTimeVO>> SubClassGroups){

		setAttribute(SUB_CLASS_ONETIME, SubClassGroups);

	}

	/**
	 * 
	 */
	public void removeSubClassGroups() {

		removeAttribute(SUB_CLASS_ONETIME);

	}
	

}
