/*
 * ProformaInvoiceDiffReportSessionImpl.java Created on Aug 08, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling;

import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ProformaInvoiceDiffReportSession;


/**
 * @author A-3271
 *
 */
public class ProformaInvoiceDiffReportSessionImpl extends AbstractScreenSession implements ProformaInvoiceDiffReportSession {

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.proformainvoicediffreport";



	private static final String KEY_ONETIME_VOS="onetimevalues";


    /**
     *
     */
    public ProformaInvoiceDiffReportSessionImpl() {
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

     public ArrayList<OneTimeVO> getOneTimeVOs(){

     return getAttribute(KEY_ONETIME_VOS);

     }
     /**
      *
      * @param oneTimeVOs
      */

     public void setOneTimeVOs(ArrayList<OneTimeVO> oneTimeVOs){

     setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

     }
     /**
      *
      * remove onetimes
      */

     public void removeOneTimeVOs() {

     removeAttribute(KEY_ONETIME_VOS);

     }

}
