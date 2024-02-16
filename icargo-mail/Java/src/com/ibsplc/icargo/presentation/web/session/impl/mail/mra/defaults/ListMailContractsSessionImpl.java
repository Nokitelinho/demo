/*
 * ListMailContractsSessionImpl.java Created on April 2, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListMailContractsSession;

/**
 * @author A-1946
 *
 */
public class ListMailContractsSessionImpl extends AbstractScreenSession implements ListMailContractsSession {

	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra";
	/**
	 * screen id
	 *
	 */
	private static final String SCREENID =
		"mailtracking.mra.defaults.listmailcontracts";

	/**
	 * One time values KEY
	 */
	private static final String KEY_ONETIME_VOS="onetimevalues";
	
	/**
	 * MailContractVOS  KEY
	 */
	private static final String KEY_MAILCONTRACTS_VOS="MailContractVOS";
	
	/**
	 * Filter Vo values KEY
	 */
	private static final String KEY_MAILCONTRACTSFILTER_VO="MailContractFilterVO";
	
	
	 /**
     * @return
     */
    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getScreenID()
     */
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
    *  return getAttribute(KEY_ONETIME_VOS);
    */
    /* (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession#getOneTimeVOs()
     */
     public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

    return getAttribute(KEY_ONETIME_VOS);

    }
         
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
    /**
	 * @return
	 */
    public Collection<MailContractVO> getMailContractVOs(){
    	return (Collection<MailContractVO>)getAttribute(KEY_MAILCONTRACTS_VOS);
    }
    
    /**
	 * 
	 * @param mailContractVOs
	 */
	public void setMailContractVOs(Collection<MailContractVO> mailContractVOs){
		setAttribute(KEY_MAILCONTRACTS_VOS,(ArrayList<MailContractVO>) mailContractVOs);
	}
	/**
	 * remove
	 */
	public void removeMailContractVOs(){
		 removeAttribute(KEY_MAILCONTRACTS_VOS);
	}
	
	 /**
	 * @return
	 */
	public MailContractFilterVO getMailContractFilterVO(){
    	return (MailContractFilterVO)getAttribute(KEY_MAILCONTRACTSFILTER_VO);
    }
    
    /**
	 * 
	 * @param mailContractVOs
	 */
	public void setMailContractFilterVO(MailContractFilterVO mailContractVO){
		setAttribute(KEY_MAILCONTRACTSFILTER_VO,(MailContractFilterVO) mailContractVO);
	}
	/**
	 * remove
	 */
	public void removMailContractFilterVO(){
		 removeAttribute(KEY_MAILCONTRACTSFILTER_VO);
	}
}
