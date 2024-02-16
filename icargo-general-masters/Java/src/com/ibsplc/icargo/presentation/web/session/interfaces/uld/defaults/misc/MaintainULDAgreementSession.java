/*
 * MaintainULDAgreementSession.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc;

import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * 
 * @author A-1862
 *
 */
public interface MaintainULDAgreementSession extends ScreenSession {
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
	     public ArrayList<OneTimeVO> getDemurrageFrequency();
	     /**
	      * 
	      * @param demurrageFrequency
	      */
	     public void setDemurrageFrequency(ArrayList<OneTimeVO> demurrageFrequency);
	     /**
	      * 
	      * @return
	      */
	     public ArrayList<CurrencyVO> getCurrency();
	     /**
	      * 
	      * @param currency
	      */
	     public void setCurrency(ArrayList<CurrencyVO> currency);
	     /**
	      * 
	      * @return
	      */
	     public ULDAgreementVO getUldAgreementDetails();
	     /**
	      * 
	      * @param agreementDetails
	      */
	     public void setUldAgreementDetails(ULDAgreementVO agreementDetails);
	     /**
	      * 
	      * @return
	      */
	     public ULDAgreementDetailsVO getUldAgreementDetailVO();
	     /**
	      * 
	      * @param uldAgreementDetailsVO
	      */
	     public void setUldAgreementDetailVO(ULDAgreementDetailsVO uldAgreementDetailsVO);	  
	     /**
	      * 
	      * @return
	      */
	     public ArrayList<ULDAgreementDetailsVO> getUldAgreementVOs();
	     /**
	      * 
	      * @param uldAgreementVOs
	      */
	     public void setUldAgreementVOs(ArrayList<ULDAgreementDetailsVO> uldAgreementVOs);
	     /**
	      * 
	      *
	      */
	     public void removeUldAgreementVOs();
	     /**
	      * 
	      * @return
	      */
	     public ArrayList<String> getAgreementNumbers();
	    /**
	     * 
	     * @param agreementNumbers
	     */	 
	     public void setAgreementNumbers(ArrayList<String> agreementNumbers);
	     //Added by A-8445 as a part of IASCB-28460
	     /**
	      * 
	      * @return
	      */
	     public Page<ULDAgreementDetailsVO> getUldAgreementPageDetails();
	     /**
	      * 
	      * @param pageULDAgreementDetailsVO
	      */
	     public void setUldAgreementPageDetails(Page<ULDAgreementDetailsVO> pageULDAgreementDetailsVO);
	     /**
	 	 * @return Returns the totalRecords.
	 	 */ 
	  	public Integer getTotalRecordsCount(); 
	  	/**
	 	 * @param totalRecords The totalRecords to set.
	 	 */ 	
	  	public void setTotalRecordsCount(int totalRecordsCount);
	  	public void removeTotalRecordsCount();	
}
