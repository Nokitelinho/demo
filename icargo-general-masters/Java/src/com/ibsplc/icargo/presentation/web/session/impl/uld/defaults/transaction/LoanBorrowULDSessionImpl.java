/*
 * LoanBorrowULDSessionImpl.java Created on Oct 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;

/**
 * @author A-1496
 * 
 */
public class LoanBorrowULDSessionImpl extends AbstractScreenSession implements
		LoanBorrowULDSession {

	/**
	 * MODULE Name
	 */
	private static final String MODULE = "uld.defaults";

	/**
	 * SCREENID
	 */
	private static final String SCREENID = "uld.defaults.loanborrowuld";

	/**
	 * companyCode
	 */
	private static final String KEY_COMPANYCODE = "companyCode";

	/**
	 * stationCode
	 */
	private static final String KEY_STATIONCODE = "stationCode";

	/**
	 * userId
	 */
	private static final String KEY_USERID = "userId";

	/**
	 * txnTypes
	 */
	private static final String KEY_TXNTYPES = "txnTypes";
	
//	added by a-3045
	/**
	 * uldNature
	 */
	private static final String KEY_ULDNATURE = "uldNature";
	private static final String KEY_AGREEMENTVO = "uldAgreementVO";
//	added by a-3045

	/**
	 * txnNatures
	 */
	private static final String KEY_TXNNATURES = "txnNatures";

	/**
	 * partyTypes
	 */
	private static final String KEY_PARTYTYPES = "partyTypes";

	/**
	 * accessoryCodes
	 */
	private static final String KEY_ACCESSORYCODES = "accessoryCodes";

	/**
	 * transactionVO
	 */
	private static final String KEY_TRANSACTIONVO = "transactionVO";

	private static final String KEY_RECONCILEVO = "ReconcileVO";

	private static final String KEY_SCMRECONCILEVO = "SCMReconcileVO";

	private static final String KEY_PAGEURL = "pageurl";

	private static final String KEY_ULDRECEIPT = "uldreceipt";

	private static final String KEY_RECEIPTNO = "receiptno";

	private static final String KEY_TXNREFNO = "txnrefno";
	
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	
	private static final String KEY_CTRLRCPTNO = "ctrlRcptNo";
	private static final String KEY_SELULDS = "selectedULDColl";

	//Added by A-2412 
	private static final String KEY_CONDITIONCODES = "conditionCodes";
	//Addtion ends 
	
   //Added by A-2619 
	private static final String KEY_ULDLASTUPDATETIME = "uldLastUpdateTime";
	private static final String KEY_ULDLASTUPDATEUSER = "uldLastUpdateUser";
	
	
	//Added by A-2412 on 18th Oct For CRN Editable CR 
	private static final String KEY_CTRLRCPTNOPREFIX = "ctrlRcptNoPrefix";
	// Addition by A-2412 on 18th Oct For CRN Editable CR ends 
	 
	private static final String KEY_ULDNUMS_SELECTED = "uldNumbersSelected";
	private static final String KEY_TXNNUMS_SELECTED = "txnNumbersSelected";
	private static final String KEY_ISLOANUCRPRINT = "isLoanUcrPrint";
	
	private static final String LIST_DETAILS = "ULDServiceabilityVO";
	/**
	 * 
	 * /** Method to get ScreenID
	 * 
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
	 * @return companycode
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getCompanyCode()
	 */
	public String getCompanyCode() {
		return getAttribute(KEY_COMPANYCODE);
	}

	/**
	 * @param companyCode
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#setCompanyCode(java.lang.String)
	 */
	public void setCompanyCode(String companyCode) {
		setAttribute(KEY_COMPANYCODE, companyCode);
	}
	
	

	/**
	 * @param key
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#removeCompanyCode(java.lang.String)
	 */
	public void removeCompanyCode(String key) {
		removeAttribute(key);
	}

	/**
	 * @return String
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getStationCode()
	 */
	public String getStationCode() {
		return getAttribute(KEY_STATIONCODE);
	}

	/**
	 * setStationCode
	 * 
	 * @param stationCode
	 * 
	 */
	public void setStationCode(String stationCode) {
		setAttribute(KEY_STATIONCODE, stationCode);
	}

	/**
	 * removeStationCode
	 * 
	 * @param key
	 */
	public void removeStationCode(String key) {
		removeAttribute(key);
	}

	/**
	 * @return String
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getStationCode()
	 */
	public String getUserId() {
		return getAttribute(KEY_USERID);
	}

	/**
	 * @param userId
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#setCompanyCode(java.lang.String)
	 */
	public void setUserId(String userId) {
		setAttribute(KEY_USERID, userId);
	}

	/**
	 * removeUserId
	 * 
	 * @param key
	 */
	public void removeUserId(String key) {
		removeAttribute(key);
	}

	/**
	 * @return String
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getTransactionVO()
	 */
	public TransactionVO getTransactionVO() {
		return getAttribute(KEY_TRANSACTIONVO);
	}

	/**
	 * @param transactionVO
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#setTransactionVO(transactionVO)
	 */
	public void setTransactionVO(TransactionVO transactionVO) {
		setAttribute(KEY_TRANSACTIONVO, transactionVO);
	}

	/**
	 * removeTransactionVO
	 * 
	 * @param key
	 */
	public void removeTransactionVO(String key) {
		removeAttribute(key);
	}

	/**
	 * @return String
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getTxnTypes()
	 */
	public Collection<OneTimeVO> getTxnTypes() {
		return (Collection<OneTimeVO>) getAttribute(KEY_TXNTYPES);
	}

	/**
	 * txnTypes
	 * 
	 * @param txnTypes
	 * 
	 */
	public void setTxnTypes(Collection<OneTimeVO> txnTypes) {
		setAttribute(KEY_TXNTYPES, (ArrayList<OneTimeVO>) txnTypes);
	}

	/**
	 * @return String
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getTxnNatures()
	 */
	public Collection<OneTimeVO> getTxnNatures() {
		return (Collection<OneTimeVO>) getAttribute(KEY_TXNNATURES);
	}

	/**
	 * txnNatures
	 * 
	 * @param txnNatures
	 * 
	 */
	public void setTxnNatures(Collection<OneTimeVO> txnNatures) {
		setAttribute(KEY_TXNNATURES, (ArrayList<OneTimeVO>) txnNatures);
	}

	/**
	 * @return String
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getPartyTypes()
	 */
	public Collection<OneTimeVO> getPartyTypes() {
		return (Collection<OneTimeVO>) getAttribute(KEY_PARTYTYPES);
	}

	/**
	 * partyTypes
	 * 
	 * @param partyTypes
	 * 
	 */
	public void setPartyTypes(Collection<OneTimeVO> partyTypes) {
		setAttribute(KEY_PARTYTYPES, (ArrayList<OneTimeVO>) partyTypes);
	}

	/**
	 * @return String
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getAccessoryCodes()
	 */
	public Collection<OneTimeVO> getAccessoryCodes() {
		return (Collection<OneTimeVO>) getAttribute(KEY_ACCESSORYCODES);
	}

	/**
	 * accessoryCodes
	 * 
	 * @param accessoryCodes
	 * 
	 */
	public void setAccessoryCodes(Collection<OneTimeVO> accessoryCodes) {
		setAttribute(KEY_ACCESSORYCODES, (ArrayList<OneTimeVO>) accessoryCodes);
	}
/**
 * @return
 */
	public ULDFlightMessageReconcileDetailsVO getULDFlightMessageReconcileDetailsVO() {
		return getAttribute(KEY_RECONCILEVO);
	}
	/**
	 * @param uldFlightMessageReconcileDetailsVO
	 */
	public void setULDFlightMessageReconcileDetailsVO(
			ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO) {
		setAttribute(KEY_RECONCILEVO, uldFlightMessageReconcileDetailsVO);
	}
	/**
	 * @return
	 */
	public String getPageURL() {
		return getAttribute(KEY_PAGEURL);
	}
	/**
	 * @param pageUrl
	 */
	public void setPageURL(String pageUrl) {
		setAttribute(KEY_PAGEURL, pageUrl);
	}
	/**
	 * @return
	 */
	public ArrayList<String> getULDReceipt() {
		return getAttribute(KEY_ULDRECEIPT);
	}
/**
 * @param uldReceipt
 */
	public void setULDReceipt(ArrayList<String> uldReceipt) {
		setAttribute(KEY_ULDRECEIPT, uldReceipt);
	}
	/**
	 * @return
	 */
	public ArrayList<String> getReceiptNo() {
		return getAttribute(KEY_RECEIPTNO);
	}
	/**
	 * @param receiptNo
	 */
	public void setReceiptNo(ArrayList<String> receiptNo) {
		setAttribute(KEY_RECEIPTNO, receiptNo);
	}
	/**
	 * @return
	 */
	public ArrayList<String> getTxnRefNo() {
		return getAttribute(KEY_TXNREFNO);
	}
	/**
	 * @param txnRefNo
	 */
	public void setTxnRefNo(ArrayList<String> txnRefNo) {
		setAttribute(KEY_TXNREFNO, txnRefNo);
	}
	/**
	 * @param reconcileDetailsVO
	 */
	public void setULDSCMReconcileDetailsVO(
			ULDSCMReconcileDetailsVO reconcileDetailsVO) {
		setAttribute(KEY_SCMRECONCILEVO, reconcileDetailsVO);

	}
	/**
	 * @return
	 */
	public ULDSCMReconcileDetailsVO getULDSCMReconcileDetailsVO() {
		return getAttribute(KEY_SCMRECONCILEVO);
	}
	/**
	 * @return Returns the oneTimeValues.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
		return getAttribute(KEY_ONETIMEVALUES);
	}

	/**
	 * @param oneTimeValues
	 *            The oneTimeValues to set.
	 */
	public void setOneTimeValues(
			HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
		setAttribute(KEY_ONETIMEVALUES, oneTimeValues);
	}
	/**
	 * @return ctrlRcptNo
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getCtrlRcptNo()
	 */
	public String getCtrlRcptNo() {
		return getAttribute(KEY_CTRLRCPTNO);
	}

	/**
	 * @param ctrlRcptNo
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#setCtrlRcptNo(java.lang.String)
	 */
	public void setCtrlRcptNo(String ctrlRcptNo) {
		setAttribute(KEY_CTRLRCPTNO, ctrlRcptNo);
	}
	/**
	 * @return
	 */
	public ArrayList<ULDTransactionDetailsVO> getSelectedULDColl() {
		return getAttribute(KEY_SELULDS);
	}
	/**
	 * @param selectedULDColl
	 */
	public void setSelectedULDColl(ArrayList<ULDTransactionDetailsVO> selectedULDColl) {
		setAttribute(KEY_SELULDS, selectedULDColl);
	}
	
	//Added by A-2412
	public Collection<OneTimeVO>  getConditionCodes(){
		return (Collection<OneTimeVO>)getAttribute(KEY_CONDITIONCODES);
	}
	public void setConditionCodes(Collection<OneTimeVO> conditionCodes){
		setAttribute(KEY_CONDITIONCODES, (ArrayList<OneTimeVO>)conditionCodes);
	}
	// Addition ends
	
   //  Added by A-2619
	 public LocalDate getUldLastUpdateTime(){
		 return (getAttribute(KEY_ULDLASTUPDATETIME));
	 }
	 public void  setUldLastUpdateTime(LocalDate dte){
		 setAttribute(KEY_ULDLASTUPDATETIME,dte);
	 }
	 
	 public String getUldLastUpdateUser(){
		 return (getAttribute(KEY_ULDLASTUPDATEUSER));
	 }
	 public void  setUldLastUpdateUser(String lastUpdateUser){
		 setAttribute(KEY_ULDLASTUPDATEUSER,lastUpdateUser);
	 }
	//ends
	 
	 //Added by A-2412 on 18th Oct For CRN Editable CR 
	 public String getCtrlRcptNoPrefix(){
		 return (getAttribute(KEY_CTRLRCPTNOPREFIX));
	 }
	 public void  setCtrlRcptNoPrefix(String ctrlRcptNoPrefix){
		 setAttribute(KEY_CTRLRCPTNOPREFIX,ctrlRcptNoPrefix);
	 }
	 //Addition by A-2412 on 18th Oct For CRN Editable CR ends
	 //Added by Preet on 12 th Dec for UCR print starts
		public Collection<String> getUldNumbersSelected(){
			return (Collection<String>) getAttribute(KEY_ULDNUMS_SELECTED);
		}
		public void  setUldNumbersSelected(Collection<String> uldNumbersSelected){
			setAttribute(KEY_ULDNUMS_SELECTED,(ArrayList<String>)uldNumbersSelected);
		}
		public Collection<Integer> getTxnNumbersSelected(){
			return (Collection<Integer>) getAttribute(KEY_TXNNUMS_SELECTED);
		}
		public void  setTxnNumbersSelected(Collection<Integer> txnNumbersSelected){
			setAttribute(KEY_TXNNUMS_SELECTED,(ArrayList<Integer>)txnNumbersSelected);
		}
		public String getLoanUcrPrint(){
			 return (getAttribute(KEY_ISLOANUCRPRINT));
		}
	    public void  setLoanUcrPrint(String isLoanUcrPrint){
	    	 setAttribute(KEY_ISLOANUCRPRINT,isLoanUcrPrint);
	    }
	//Added by Preet on 12 th Dec for UCR print ends
	    
	    //added by a-3045			
			/**
			 * @return String
			 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
			 *      transaction.LoanBorrowULDSessionImpl#getUldNature()
			 */
			public Collection<OneTimeVO> getUldNature() {
				return (Collection<OneTimeVO>) getAttribute(KEY_ULDNATURE);
			}

			/**
			 * uldNature
			 * 
			 * @param uldNature
			 * 
			 */
			public void setUldNature(Collection<OneTimeVO> uldNature) {
				setAttribute(KEY_ULDNATURE, (ArrayList<OneTimeVO>) uldNature);
			}
			
			public ULDAgreementVO getAgreementVO() {
				return getAttribute(KEY_AGREEMENTVO);
			}

			public void setAgreementVO(ULDAgreementVO uldAgreementVO) {
				setAttribute(KEY_AGREEMENTVO, uldAgreementVO);
			}
			
			//added by a-3045
			/**
			 * This method returns the ULDServiceabilityVO in session
			 * @return Page<ULDServiceabilityVO>
			 */

			public Collection<ULDServiceabilityVO> getULDServiceabilityVOs() {
				return (Collection<ULDServiceabilityVO>) getAttribute(LIST_DETAILS);
			}

			/**
			 * This method sets the ULDServiceabilityVO in session
			 * @param paramCode
			 */
			public void setULDServiceabilityVOs(
					Collection<ULDServiceabilityVO> paramCode) {
		 		setAttribute(LIST_DETAILS, (ArrayList<ULDServiceabilityVO>) paramCode);
			}
}
