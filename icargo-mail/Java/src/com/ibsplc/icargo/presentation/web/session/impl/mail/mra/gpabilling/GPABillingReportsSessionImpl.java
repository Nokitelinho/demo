/*
 * GPABillingReportsSessionImpl.java Created on Mar 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingReportsSession;


/**
 * @author A-2408
 *
 */
public class GPABillingReportsSessionImpl extends AbstractScreenSession implements GPABillingReportsSession {

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.gpabillingreports";

	

	private static final String KEY_ONETIME_VOS="onetimevalues";

	
    /**
     *
     */
    public GPABillingReportsSessionImpl() {
        super();

    }
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

     *

     * @return

     */

     public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

     return getAttribute(KEY_ONETIME_VOS);

     }
     /**

     *

     * @param oneTimeVOs

     */

     public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

     setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

     }
     /**

     *

     *remove onetimes

     */

     public void removeOneTimeVOs() {

     removeAttribute(KEY_ONETIME_VOS);

     }
   

}
