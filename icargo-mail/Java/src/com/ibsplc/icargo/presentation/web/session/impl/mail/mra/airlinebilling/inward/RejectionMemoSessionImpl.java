/*
 * RejectionMemoSessionImpl.java Created on May 18, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.inward;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.RejectionMemoSession;

/**
 * @author A-2408
 *
 */
public class RejectionMemoSessionImpl extends AbstractScreenSession 
implements RejectionMemoSession{
	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	/**
	 * screen id
	 *
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.rejectionmemo";
	
	private static final String KEY_REJECTIONMEMO = "rejectionmemo";
	
	private static final String KEY_ONETIME_VOS="onetimevalues";
	
	/**
	 * 
	 */
	@Override
	public String getScreenID() {
		
		return SCREEN_ID;
	}
	
	/**
	 * 
	 */
	@Override
	public String getModuleName() {
		
		return MODULE_NAME;
	}

	/**
	 * @return
	 */
	public RejectionMemoVO getRejectionMemoVO(){
		return getAttribute(KEY_REJECTIONMEMO);
	}
	
	/**
	 * @param rejectionMemoVO
	 */
	public void setRejectionMemoVO(RejectionMemoVO rejectionMemoVO){
		setAttribute(KEY_REJECTIONMEMO,rejectionMemoVO);
	}
	
	/**
	 * 
	 */
	public void removeRejectionMemoVO(){
		removeAttribute(KEY_REJECTIONMEMO);
	}
	
	
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
  
    
}