/*
 * MaintainULDAgreementSessionImpl.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;

import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainULDAgreementSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1347
 *
 */

public class MaintainULDAgreementSessionImpl extends AbstractScreenSession
				implements MaintainULDAgreementSession{
	
	private static final String MODULENAME = "uld.defaults";
	private static final String SCREENID = "uld.defaults.maintainuldagreement";
	private static final String KEY_COMPANYCODE = "companyCode";
	private static final String KEY_TXNTYP = "transactionType";
	private static final String KEY_AGREEMENTSTATUS = "agreementStatus";
	private static final String KEY_PARTYTYP = "partyType";
	private static final String KEY_DEMURRAGEFREQ = "demurrageFrequency";
	private static final String KEY_CURRENCY = "currency";
	private static final String KEY_AGREEMENTDETAILS = "agreementDetails";
	private static final String KEY_AGREEMENTVO = "agreementVO";
	private static final String KEY_AGREEMENTVOS = "agreementVOs";
	private static final String KEY_AGREEMENTNUMBERS="sgreementNumbers";
	//Added by A-8445
	private static final String KEY_AGREEMENTS = "agreements";
	private static final String TOTAL_RECORDS_COUNT = "totalRecordsCount";
	
	/**
	 * return screen id
	 */
	public String getScreenID() {
		return SCREENID;
	}
	/**
	 * @return
	 */
	public String getModuleName() {
		return MODULENAME;
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
	 * 
	 */
	public ArrayList<OneTimeVO> getDemurrageFrequency(){
		return getAttribute(KEY_DEMURRAGEFREQ);
	}
	/**
	 * @param demurrageFrequency
	 */
	public void setDemurrageFrequency(ArrayList<OneTimeVO> demurrageFrequency){
		setAttribute(KEY_DEMURRAGEFREQ,demurrageFrequency);
	}
	/**
	 * @return
	 */
	public ArrayList<CurrencyVO> getCurrency(){
		return getAttribute(KEY_CURRENCY);
	}
	/**
	 * @param currency
	 */
	public void setCurrency(ArrayList<CurrencyVO> currency){
		setAttribute(KEY_CURRENCY,currency);	
		}
	/**
	 * @return
	 */
	public ULDAgreementVO getUldAgreementDetails(){
		return getAttribute(KEY_AGREEMENTDETAILS);
	}
	/**
	 * @param agreementDetails
	 */
	public void setUldAgreementDetails(ULDAgreementVO agreementDetails){
		setAttribute(KEY_AGREEMENTDETAILS,agreementDetails);
	}
	/**
	 * @return
	 */
	public ULDAgreementDetailsVO getUldAgreementDetailVO(){
		return getAttribute(KEY_AGREEMENTVO);
	}
	/**
	 * @param uldAgreementDetailsVO
	 */
	public void setUldAgreementDetailVO(ULDAgreementDetailsVO uldAgreementDetailsVO){
		setAttribute(KEY_AGREEMENTVO,uldAgreementDetailsVO);
	}
	/**
	 * @return
	 */
	public ArrayList<ULDAgreementDetailsVO> getUldAgreementVOs(){
		return getAttribute(KEY_AGREEMENTVOS);
	}
	/**
	 * @param agreementVOs
	 */
	public void setUldAgreementVOs(ArrayList<ULDAgreementDetailsVO> agreementVOs){
		setAttribute(KEY_AGREEMENTVOS,agreementVOs);
	}
	/**
	 * 
	 */
	public void removeUldAgreementVOs(){
		removeAttribute(KEY_AGREEMENTVOS);
	}
	/**
	 * return
	 */
	public ArrayList<String> getAgreementNumbers(){
		return getAttribute(KEY_AGREEMENTNUMBERS);
	}
	/**
	 * @param agreementNumbers
	 */
	public void setAgreementNumbers(ArrayList<String> agreementNumbers){
		setAttribute(KEY_AGREEMENTNUMBERS,agreementNumbers);
	}
	//Added by A-8445
	/**
	 * @return
	 */
	public Page<ULDAgreementDetailsVO> getUldAgreementPageDetails(){
		return getAttribute(KEY_AGREEMENTS);
	}
	/**
	 * @param agreements
	 */
	public void setUldAgreementPageDetails(Page<ULDAgreementDetailsVO> agreements){
		setAttribute(KEY_AGREEMENTS,agreements);
	}
	public Integer getTotalRecordsCount() {
		
		return getAttribute(TOTAL_RECORDS_COUNT);
	}	
	public void setTotalRecordsCount(int totalRecordsCount) {
		
		setAttribute(TOTAL_RECORDS_COUNT, totalRecordsCount);
				
	}
	public void removeTotalRecordsCount(){
		removeAttribute(TOTAL_RECORDS_COUNT);
	}	
}
