/*
 * CaptureRejectionMemoSessionImpl.java Created on Feb 21, 2007 
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.defaults;


import java.util.ArrayList;
//import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureRejectionMemoSession;



/**
 * @author Ruby Abraham
 * Session implementation for Capture Rejection Memo screen
 * 
 * Revision History     
 * 
 * Version      Date           Author          		    Description
 * 
 *  0.1		Feb 21, 2007     Ruby Abraham				Initial draft
 */
public class CaptureRejectionMemoSessionImpl extends AbstractScreenSession implements
										CaptureRejectionMemoSession{

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturerejectionmemo";
	
	/**
	 * Constant for the session variable memoFilterVO
	 */
	private static final String KEY_LISTFILTER = "memoFilterVO";
	
	/**
	 * Constant for the session variable memoInInvoiceVOs
	 */
	private static final String KEY_MEMOININVOICEVOS = "memoInInvoiceVOs";
	
	
	/**
	 * Constant for the session variable interlineBillingType
	 */
	private static final String KEY_BILLINGTYPE = "interlineBillingType";

	/*
     * (non-Javadoc)
     *
     * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getScreenID()
     */
	/**
	 * @return screenID 
	 */
    public String getScreenID() {

        return SCREEN_ID;
    }

    /**
     * @return moduleName
     */
    /*
     * (non-Javadoc)
     *
     * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getModuleName()
     */
    public String getModuleName() {
        return MODULE_NAME;
    }
    
    
    
    /**
     * 
     * @return memoFilterVO
     */
    
	public MemoFilterVO getMemoFilterVO() {
		return getAttribute(KEY_LISTFILTER);
	}
	/**
	 * @param memoFilterVO The  memoFilterVO to set.
	 */
	public void setMemoFilterVO(MemoFilterVO memoFilterVO) {
		setAttribute(KEY_LISTFILTER,memoFilterVO);
	}
	
	/**
	 * @author A-2122
	 */	
	
	public void removeMemoFilterVO() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_LISTFILTER);
	}
   
	/**
	 * @param memoInInvoiceVOs 
	 */
	public void setMemoInInvoiceVOs(ArrayList<MemoInInvoiceVO> memoInInvoiceVOs){
		setAttribute(KEY_MEMOININVOICEVOS,memoInInvoiceVOs);
	}
	/**
	 * @return ArrayList<MemoInInvoiceVO>
	 */
	public ArrayList<MemoInInvoiceVO> getMemoInInvoiceVOs(){
		return getAttribute(KEY_MEMOININVOICEVOS);
	}
	
	/**
	 * @author A-2122
	 *
	 */
	public void removeMemoInInvoiceVOs(){
		
		removeAttribute(KEY_MEMOININVOICEVOS);
	}
	
	
	/**
	 * @return Returns the interlineBillingType
	 */
	public ArrayList<OneTimeVO> getInterlineBillingType() {
		return getAttribute(KEY_BILLINGTYPE);
	}
	
	/**
	 * @param interlineBillingType The  interlineBillingType to set.
	 */
	public void setInterlineBillingType(ArrayList<OneTimeVO> interlineBillingType) {
		
		// TODO Auto-generated method stub
		setAttribute(KEY_BILLINGTYPE,interlineBillingType);
		
	}


	
}
