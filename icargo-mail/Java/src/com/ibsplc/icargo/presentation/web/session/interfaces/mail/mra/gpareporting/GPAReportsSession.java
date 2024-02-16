/*
 * GPAReportsSession.java Created on Mar 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting;



import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2245
 *
 */
	public interface GPAReportsSession extends ScreenSession {

 	    /**
	     * method to get HashMap<String, Collection<OneTimeVO>> from session
	     * @return HashMap<String, Collection<OneTimeVO>>
	     */
	     public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();
	     /**
	      * method to set oneTimeVOs to session
	      * @param oneTimeVOs
	      */
	     public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);
	     /**
	      * method to remove oneTimeVOs from session
	      */
	     public void removeOneTimeVOs();
}
