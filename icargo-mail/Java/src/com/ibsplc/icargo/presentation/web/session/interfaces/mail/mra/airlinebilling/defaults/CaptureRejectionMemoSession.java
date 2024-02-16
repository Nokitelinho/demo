/*
 * CaptureRejectionMemoSession.java Created on Feb 21,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults;

import java.util.ArrayList;
//import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;


/**
 * @author Ruby Abraham
 * Session interface for CaptureRejectionMemo Screen.
 * 
 * Revision History
 * 
 * Version      Date           Author          		    Description
 * 
 *  0.1		Feb 21, 2007    Ruby Abraham				Initial draft
 */
public interface CaptureRejectionMemoSession extends ScreenSession {
	
	
	/**
     * 
     * @return memoFilterVO
     */
    
	public MemoFilterVO getMemoFilterVO();
	
	/**
	 * @param memoFilterVO The  memoFilterVO to set.
	 */
	public void setMemoFilterVO(MemoFilterVO memoFilterVO);
	
	/**
	 * @author A-2122
	 */	
	
	public void removeMemoFilterVO();
	
	
	
	/**
	 * @param memoInInvoiceVOs 
	 */
	public void setMemoInInvoiceVOs(ArrayList<MemoInInvoiceVO> memoInInvoiceVOs);
	
	
	/**
	 * @return ArrayList<MemoInInvoiceVO>
	 */
	public ArrayList<MemoInInvoiceVO> getMemoInInvoiceVOs();
	
	/**
	 * @author A-2122
	 *
	 */
	public void removeMemoInInvoiceVOs();
	
	
	/**
	 * @return Returns the interlineBillingType
	 */
	public ArrayList<OneTimeVO> getInterlineBillingType();
	
	/**
	 * @param interlineBillingType The  interlineBillingType to set.
	 */
	public void setInterlineBillingType(ArrayList<OneTimeVO> interlineBillingType);

	
	
}

