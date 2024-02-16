/*
 * SurChargeBillingDetailSessionImpl.java Created on Jul 15, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SurchargeBillingDetailVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.SurChargeBillingDetailSession;

/**
 * @author A-5255 
 * @version	0.1, Jul 15, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jul 15, 2015	     A-5255		First draft
 */

public class SurChargeBillingDetailSessionImpl  extends AbstractScreenSession implements SurChargeBillingDetailSession{
	
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREENID = "mailtracking.mra.gpabilling.surcharge.surchargepopup";
	private static final String GPABILLINGFILTERVO_KEY="gpabillingfiltervo_key";
	private static final String SURCHARGEBILLINGDETAILVOS_KEY="surchargebillingdetailvos";
	private static final String KEY_ONETIME_VOS="onetimevalues";
	/**
	  * @author A-5255
	  * @return
	  * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getModuleName()
	  */
	
	
	public String getModuleName() {
		
		return MODULE_NAME;
	}

	/**
	  * @author A-5255
	  * @return
	  * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getScreenID()
	  */
	

	public String getScreenID() {
		
		return SCREENID;
	}

	/**
	  * @author A-5255
	  * @param cn51cn66FilterVO
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.SurChargeBillingDetailSession#setGpaBillingFilterVO(com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO)
	  */
	
	@Override
	public void setGpaBillingFilterVO(CN51CN66FilterVO cn51cn66FilterVO) {
		setAttribute(GPABILLINGFILTERVO_KEY, cn51cn66FilterVO);
		
	}

	/**
	  * @author A-5255
	  * @return
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.SurChargeBillingDetailSession#getGpaBillingFilterVO()
	  */
	
	@Override
	public CN51CN66FilterVO getGpaBillingFilterVO() {
		
		 return getAttribute(GPABILLINGFILTERVO_KEY);
	}

	/**
	  * @author A-5255
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.SurChargeBillingDetailSession#removeGpaBillingFilterVO()
	  */
	
	@Override
	public void removeGpaBillingFilterVO() {
		removeAttribute(GPABILLINGFILTERVO_KEY);
		
	}

	/**
	  * @author A-5255
	  * @param surchargeBillingDetailVOs
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.SurChargeBillingDetailSession#setSurchargeBillingDetailVOs(java.util.ArrayList)
	  */
	
	@Override
	public void setSurchargeBillingDetailVOs(
			ArrayList<SurchargeBillingDetailVO> surchargeBillingDetailVOs) {
		
		setAttribute(SURCHARGEBILLINGDETAILVOS_KEY, surchargeBillingDetailVOs);
		
	}

	/**
	  * @author A-5255
	  * @return
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.SurChargeBillingDetailSession#getSurchargeBillingDetailVOs()
	  */
	
	@Override
	public ArrayList<SurchargeBillingDetailVO> getSurchargeBillingDetailVOs() {
		 return getAttribute(SURCHARGEBILLINGDETAILVOS_KEY);
	}

	/**
	  * @author A-5255
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.SurChargeBillingDetailSession#removeSurchargeBillingDetailVOs()
	  */
	
	@Override
	public void removeSurchargeBillingDetailVOs() {
		removeAttribute(SURCHARGEBILLINGDETAILVOS_KEY);
		
	}
	/**
	 * 
	  * @author A-5255
	  * @return
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.SurChargeBillingDetailSession#getOneTimeVOs()
	 */
    public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

    return getAttribute(KEY_ONETIME_VOS);

    }
   
    /**
     * 
      * @author A-5255
      * @param oneTimeVOs
      * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.SurChargeBillingDetailSession#setOneTimeVOs(java.util.HashMap)
     */
    public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

    setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

    }
    /**
     * 
      * @author A-5255
      * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.SurChargeBillingDetailSession#removeOneTimeVOs()
     */
    public void removeOneTimeVOs() {

    removeAttribute(KEY_ONETIME_VOS);

    }

	
	

}
