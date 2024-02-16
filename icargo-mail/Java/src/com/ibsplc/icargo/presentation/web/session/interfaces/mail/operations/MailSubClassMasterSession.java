/*
 * MailSubClassMasterSession.java Created on June 08, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2047
 *
 */
public interface MailSubClassMasterSession extends ScreenSession {

    /**
     * @return Collection<MailSubClassVO>
     */
    Collection<MailSubClassVO> getMailSubClassVOs();
    
    /**
     * @param mailSubClassVOs
     */
    void setMailSubClassVOs(Collection<MailSubClassVO> mailSubClassVOs);
    
    /**
     * @return
	 */	 
	public HashMap<String, Collection<OneTimeVO>> getSubClassGroups();

	/**
	 * @param iataCalendarVO
	 */
	public void setSubClassGroups(HashMap<String, Collection<OneTimeVO>> SubClassGroups) ;



	/**
	 * 
	 *
	 */
	public void removeSubClassGroups();

}
