/*
 * InvoiceExceptionsSessionImpl.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.inward;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1946
 *
 */
public class InvoiceExceptionsSessionImpl extends AbstractScreenSession implements InvoiceExceptionsSession {

	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra";
	/**
	 * screen id
	 *
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.invoiceexceptions";
	/**
	 * One time values KEY
	 */
	private static final String KEY_ONETIME_VOS="onetimevalues";
	
	/**
	 * One time values KEY
	 */
	private static final String KEY_EXCEPTIONININVOICEVO_VOS="exceptionininvoicevos";
	
	/**
	 * One time values KEY
	 */
	private static final String KEY_EXCEPTIONININVOICEFILTERVO_VO="exceptionininvoicefiltervo";
	/**
	 * 
	 */
	private static final String KEY_SELECT_ROWS="selectedrows";
	private static final String  TOTALRECORDS = "totalRecords";
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
    public Page<ExceptionInInvoiceVO> getExceptionInInvoiceVOs(){
    	return (Page<ExceptionInInvoiceVO>)getAttribute(KEY_EXCEPTIONININVOICEVO_VOS);
    }
    
    /* (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession#setExceptionInInvoiceVOs(java.util.Collection)
     */
    /**
     * @param exceptionInInvoiceVOs
     */
    public void setExceptionInInvoiceVOs(Page<ExceptionInInvoiceVO> exceptionInInvoiceVOs){
    	setAttribute(KEY_EXCEPTIONININVOICEVO_VOS, (Page<ExceptionInInvoiceVO>)exceptionInInvoiceVOs);
    }
   
    /* (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession#removeExceptionInInvoiceVOs()
     */
    /**
     * @param
     */
    public void removeExceptionInInvoiceVOs(){
    	removeAttribute(KEY_EXCEPTIONININVOICEVO_VOS);
    }
    
    /* (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession#getExceptionInInvoiceFilterVO()
     */
    /**
     * @param getExceptionInInvoiceFilterVO
     */
    public ExceptionInInvoiceFilterVO getExceptionInInvoiceFilterVO(){
    	return (ExceptionInInvoiceFilterVO)getAttribute(KEY_EXCEPTIONININVOICEFILTERVO_VO);
    }
    
    /* (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession#getExceptionInInvoiceFilterVO(com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceFilterVO)
     */
    /**
     *@param exceptionInInvoiceFilterVO
     * 
     */
    public void setExceptionInInvoiceFilterVO(ExceptionInInvoiceFilterVO exceptionInInvoiceFilterVO){
    	setAttribute(KEY_EXCEPTIONININVOICEFILTERVO_VO, exceptionInInvoiceFilterVO);
    }
    /**
     * remove all attribute
     */
    
    /* (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession#removeExceptionInInvoiceFilterVO()
     */
    public void removeExceptionInInvoiceFilterVO(){
    	removeAttribute(KEY_EXCEPTIONININVOICEFILTERVO_VO);
    }
    /**
     * 
     */
    public String[] getSelectedRows(){
    	return (String[])getAttribute(KEY_SELECT_ROWS);
    }
    /**
     * 
     */
    public void setSelectedRows(String[] str){
    	setAttribute(KEY_SELECT_ROWS, str);
    }
    /**
     * 
     */
    public void removeSelectedRows(){
    	removeAttribute(KEY_SELECT_ROWS);
    }
    /**
	 * @return Returns the totalRecords.
	 */
	public Integer getTotalRecords() {
		return getAttribute(TOTALRECORDS);
	}
	
	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(int totalRecords) {
		setAttribute(TOTALRECORDS,totalRecords);
	}
	
	/**
     * This method removes the totalRecords in session
     */
	public void removeTotalRecords() {
	 	removeAttribute(TOTALRECORDS);
	}
    
}
