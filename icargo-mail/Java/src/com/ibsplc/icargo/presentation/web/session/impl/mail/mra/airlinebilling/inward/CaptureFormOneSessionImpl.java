/*
 * CaptureFormOneSessionImpl.java Created on Jul 22,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.inward;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormOneSession;

/**
 * @author A-2391
 *
 */
public class CaptureFormOneSessionImpl extends AbstractScreenSession implements CaptureFormOneSession {

	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	/**
	 * screen id
	 *
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.captureformone";
	/**
	 * One time values KEY
	 */
	private static final String KEY_ONETIME_VOS="onetimevalues";
	
	/**
	 * One time values KEY
	 */
	private static final String KEY_FORMINVVO_VOS="exceptionininvoicevos";
	
	/**
	 * key for Filter
	 */
	
	
	private static final String KEY_FILTERVO="invoiceInFormOneVO";
   /**
    * 
    */
	
	
	
	/**
	 * @author a-3447 Key for getting one time values
	 * 
	 */

	private static final String KEY_ONETIME_CODES_MAP = "onetimecodesmap";
	
	
    @Override
    public String getScreenID() {

        return SCREENID;
    }

    /**
     * @return
     */
    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getModuleName()
     */
    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }
    /**

    *

    * @return

    */

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession#getOneTimeVOs()
     */
    public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

    return getAttribute(KEY_ONETIME_VOS);

    }
    /**

    *

    * @param cCCollectorVO

    */

      
    /* (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession#setOneTimeVOs(java.util.HashMap)
     */
   /**
    * @param oneTimeVOs
    */
    public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

    setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

    }
    /**
    *remove onetimes
    */

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession#removeOneTimeVOs()
     */
    public void removeOneTimeVOs() {

    removeAttribute(KEY_ONETIME_VOS);

    }
  
    /* (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession#getExceptionInInvoiceVOs()
     */
    /**
     * @return Collection<ExceptionInInvoiceVO>
     */
    public Collection<InvoiceInFormOneVO> getFormOneInvVOs(){
    	return (Collection<InvoiceInFormOneVO>)getAttribute(KEY_FORMINVVO_VOS);
    }
    
    /* (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession#setExceptionInInvoiceVOs(java.util.Collection)
     */
    /**
     * @param formOneInvVOs
     */
    public void setFormOneInvVOs(ArrayList<InvoiceInFormOneVO> formOneInvVOs){
    	setAttribute(KEY_FORMINVVO_VOS, (ArrayList<InvoiceInFormOneVO>)formOneInvVOs);
    }
   
    /* (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession#removeExceptionInInvoiceVOs()
     */
    /**
     * @param
     */
    public void removeFormOneInvVOs(){
    	removeAttribute(KEY_FORMINVVO_VOS);
    }
    

public FormOneVO getFormOneVO() {
	return ((FormOneVO) getAttribute(KEY_FILTERVO));
}

/**
 * @param AirlineCN51FilterVO
 */
public void setFormOneVO(FormOneVO formOneVO) {
	setAttribute(KEY_FILTERVO, (FormOneVO) formOneVO);
}

 /**
 * @param
 */
public void removeFormOneVO(){
	removeAttribute(KEY_FILTERVO);
}

public HashMap<String, Collection<OneTimeVO>> getOneTimeMap() {
	return (HashMap<String, Collection<OneTimeVO>>) getAttribute(KEY_ONETIME_CODES_MAP);
}

/**
 * Method to set the Onetimes map to session
 * 
 * @param oneTimeMap
 *            The one time map to be set to session
 */
public void setOneTimeMap(HashMap<String, Collection<OneTimeVO>> oneTimeMap) {
	setAttribute(KEY_ONETIME_CODES_MAP,
			(HashMap<String, Collection<OneTimeVO>>) oneTimeMap);
}

/**
 * Method to remove One Time Map from session
 */
public void removeOneTimeMap() {
	removeAttribute(KEY_ONETIME_CODES_MAP);

}

    
}
