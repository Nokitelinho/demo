/*
 * GPAReportsSessionImpl.java Created on Mar 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpareporting;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.GPAReportsSession;

/**
 * 
 * @author A-2245
 *
 */
public class GPAReportsSessionImpl extends AbstractScreenSession implements GPAReportsSession {

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREENID = "mailtracking.mra.gpareporting.gpareport";

	private static final String KEY_ONETIMEVOS="oneTimeValues";
    /**
     * @return
     */
    @Override
    public String getScreenID() {

        return SCREENID;
    }

    /**
     * @return
     */
    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }

    /**
     * method to get HashMap<String, Collection<OneTimeVO>> from session
     * @return HashMap<String, Collection<OneTimeVO>>
     */
     public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){
    	 return getAttribute(KEY_ONETIMEVOS);
     }
     /**
      * method to set oneTimeVOs to session
      * @param oneTimeVOs
      */
     public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){
     setAttribute(KEY_ONETIMEVOS, oneTimeVOs);
     }
     /**
      * method to remove oneTimeVOs from session
      */
     public void removeOneTimeVOs() {
     removeAttribute(KEY_ONETIMEVOS);
     }
}
