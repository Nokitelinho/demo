/*
 * ListULDAgreementSession.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc;

import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1347
 *
 */
public interface ListULDAgreementSession extends ScreenSession {
	 /**
	  * 
	  * @param companyCode
	  */
	 public void setCompanyCode(String companyCode);
     /**
      * 
      * @return
      */
     public String getCompanyCode();
	
    /**
     * 
     * @return
     */
    public ArrayList<OneTimeVO> getTransactionType();
    /**
     * 
     * @param transactionType
     */
    public void setTransactionType(ArrayList<OneTimeVO> transactionType);
    /**
     * 
     * @return
     */
    public ArrayList<OneTimeVO> getAgreementStatus();
    /**
     * 
     * @param agreementStatus
     */
    public void setAgreementStatus(ArrayList<OneTimeVO> agreementStatus);
    /**
     * 
     * @return
     */
    public ArrayList<OneTimeVO> getPartyType();
    /**
     * 
     * @param partyType
     */
    public void setPartyType(ArrayList<OneTimeVO> partyType);
  
    /**
     * 
     * @return
     */
    public Page<ULDAgreementVO> getUldAgreements();
    /**
     * 
     * @param agreements
     */
    public void setUldAgreements(Page<ULDAgreementVO> agreements);
    
    /**
     * 
     * @return
     */
    public ULDAgreementFilterVO getULDAgreementFilterVO();
    /**
     * 
     * @param filerVO
     */
    public void setULDAgreementFilterVO(ULDAgreementFilterVO filerVO);
    
    // Added by A-5183 for < ICRD-22824  > Starts	 
 	/**
	 * @return Returns the totalRecords.
	 */ 
 	public Integer getTotalRecordsCount(); 
 	/**
	 * @param totalRecords The totalRecords to set.
	 */ 	
 	public void setTotalRecordsCount(int totalRecordsCount);
 	public void removeTotalRecordsCount();	
 	// Added by A-5183 for < ICRD-22824 > Ends
    	
}
