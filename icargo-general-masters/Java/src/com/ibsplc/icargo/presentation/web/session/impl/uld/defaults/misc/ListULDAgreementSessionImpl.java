/*
 * ListULDAgreementSessionImpl.java Created on Oct 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;


import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListULDAgreementSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1496
 *
 */


public class ListULDAgreementSessionImpl extends AbstractScreenSession
		implements ListULDAgreementSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.listuldagreement";
	private static final String KEY_COMPANYCODE = "companyCode";
	private static final String KEY_TXNTYP = "transactionType";
	private static final String KEY_AGREEMENTSTATUS = "agreementStatus";
	private static final String KEY_PARTYTYP = "partyType";
	private static final String KEY_AGREEMENTS = "agreements";
	private static final String KEY_FILTER = "filter";
	private static final String TOTAL_RECORDS_COUNT = "totalRecordsCount";

	// Added by A-5183 for < ICRD-22824 > Starts 
	
	public Integer getTotalRecordsCount() {
		
		return getAttribute(TOTAL_RECORDS_COUNT);
	}	
	public void setTotalRecordsCount(int totalRecordsCount) {
		
		setAttribute(TOTAL_RECORDS_COUNT, totalRecordsCount);
				
	}
	public void removeTotalRecordsCount(){
		removeAttribute(TOTAL_RECORDS_COUNT);
	}	
	// Added by A-5183 for < ICRD-22824 > Ends
	
	/**
	 * @return ScreenID
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * Method to get ProductName
	 *
	 * @return ProductName
	 */
	public String getModuleName() {
		return MODULE;
	}
	/**
	 * @return
	 */
	public String getCompanyCode() {
		return getAttribute(KEY_COMPANYCODE);
	}
	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		setAttribute(KEY_COMPANYCODE, companyCode);
	}
	/**
	 * @return
	 */
	public ArrayList<OneTimeVO> getTransactionType(){
		return getAttribute(KEY_TXNTYP);
	}
	/**
	 * @param transactionType
	 */
	public void setTransactionType(ArrayList<OneTimeVO> transactionType){
		setAttribute(KEY_TXNTYP,transactionType);
	}
	/**
	 * @return
	 */
	public ArrayList<OneTimeVO> getAgreementStatus(){
		return getAttribute(KEY_AGREEMENTSTATUS);
	}
	/**
	 * @param agreementStatus
	 */
	public void setAgreementStatus(ArrayList<OneTimeVO> agreementStatus){
		setAttribute(KEY_AGREEMENTSTATUS,agreementStatus);
	}
	/**
	 * @return
	 */
	public ArrayList<OneTimeVO> getPartyType(){
		return getAttribute(KEY_PARTYTYP);
	}
	/**
	 * @param partyType
	 */
	public void setPartyType(ArrayList<OneTimeVO> partyType){
		setAttribute(KEY_PARTYTYP,partyType);
	}
	/**
	 * @return
	 */
	public Page<ULDAgreementVO> getUldAgreements(){
		return getAttribute(KEY_AGREEMENTS);
	}
	/**
	 * @param agreements
	 */
	public void setUldAgreements(Page<ULDAgreementVO> agreements){
		setAttribute(KEY_AGREEMENTS,agreements);
	}
	/**
	 * @return
	 */
	public ULDAgreementFilterVO getULDAgreementFilterVO(){
		return getAttribute(KEY_FILTER);
	}
	/**
	 * @param filterVO
	 */
   public void setULDAgreementFilterVO(ULDAgreementFilterVO filterVO){
	   setAttribute(KEY_FILTER,filterVO);
   }
}
