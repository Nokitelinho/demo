/*
 * ListULDTransactionSessionImpl.java Created on Oct 10, 2005
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
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;

/**
 * @author A-1496
 * 
 */
public class ListULDTransactionSessionImpl extends AbstractScreenSession
		implements ListULDTransactionSession {
	private static final String KEY_SYSTEMPARAMETERS = "uld_defaults_loanBorrowSystemParameters";
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.loanborrowdetailsenquiry";

	private static final String KEY_PAGEURL = "pageurl";

	private static final String KEY_RECONCILEVO = "ReconcileVO";

	private static final String KEY_SCMRECONCILEVO = "SCMReconcileVO";
	
	private static final String TOTAL_RECORDS = "uld.defaults.totalrecords";

	/**
	 * companyCode
	 */
	private static final String KEY_COMPANYCODE = "companyCode";
	
	//Added by a-3278 for QF1015 on 24Jul08
	/**
	 * totalDemmurage
	 */
	private static final String KEY_TOTDEMMURAGE = "totalDemmurage";
	//a-3278 ends
	
	//Added by a-3278 for ULD771 on 21Oct08
	/**
	 * baseCurrency
	 */
	private static final String KEY_BASECURRENCY = "baseCurrency";
	//a-3278 ends

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
	 * transactionFilterVO
	 */
	private static final String KEY_TRANSACTIONFILTERVO = "transactionFilterVO";

	/**
	 * transactionListVO
	 */
	private static final String KEY_TRANSACTIONLISTVO = "transactionListVO";

	/**
	 * returnTransactionListVO
	 */
	private static final String KEY_RETURNTRANSACTIONLISTVO = "returnTransactionListVO";

	/**
	 * transactionVO
	 */
	private static final String KEY_TXNSTATUS = "txnStatus";
	//added by a-3045 for CR QF1142 starts
	/**
	 * transactionVO
	 */
	private static final String KEY_MUCSTATUS = "mucStatus";
	//added by a-3045 for CR QF1142 ends
	/**
	 * uldTransactionDetailsVO
	 */
	private static final String KEY_ULDTXNDTLVO = "uldTransactionDetailsVO";

	/**
	 * accessoryTransactionVO
	 */
	private static final String KEY_ACCTXNVO = "accessoryTransactionVO";
	
	
	private static final String KEY_ULDCONDITION = "uldCondition";
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	private static final String KEY_CTRLRCPTNOPREFIX = "ctrlRcptNoPrefix";
	private static final String KEY_CTRLRCPTNO = "ctrlRcptNo";
	private static final String KEY_ULDNUMS_SELECTED = "uldNumbersSelected";
	private static final String KEY_TXNNUMS_SELECTED = "txnNumbersSelected";
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
	 * @return uldTransactionDetailsVO
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getULDTransactionDetailsVO()
	 */

	public ULDTransactionDetailsVO getULDTransactionDetailsVO() {
		return getAttribute(KEY_ULDTXNDTLVO);
	}

	/**
	 * @param uldTransactionDetailsVO
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#setULDTransactionDetailsVO(uldTransactionDetailsVO)
	 */
	public void setULDTransactionDetailsVO(
			ULDTransactionDetailsVO uldTransactionDetailsVO) {
		setAttribute(KEY_ULDTXNDTLVO, uldTransactionDetailsVO);
	}

	/**
	 * @return accessoryTransactionVO
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getAccessoryTransactionVO()
	 */
	public AccessoryTransactionVO getAccessoryTransactionVO() {
		return getAttribute(KEY_ACCTXNVO);
	}

	/**
	 * @param accessoryTransactionVO
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#setAccessoryTransactionVO(accessoryTransactionVO)
	 */
	public void setAccessoryTransactionVO(
			AccessoryTransactionVO accessoryTransactionVO) {
		setAttribute(KEY_ACCTXNVO, accessoryTransactionVO);
	}

	/**
	 * @return transactionListVO
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getTransactionListVO()
	 */
	public TransactionListVO getTransactionListVO() {
		return getAttribute(KEY_TRANSACTIONLISTVO);
	}

	/**
	 * @param transactionListVO
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#setTransactionListVO(transactionListVO)
	 */
	public void setTransactionListVO(TransactionListVO transactionListVO) {
		setAttribute(KEY_TRANSACTIONLISTVO, transactionListVO);
	}

	/**
	 * @param key
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#removeTransactionListVO(java.lang.String)
	 */
	public void removeTransactionListVO(String key) {
		removeAttribute(key);
	}

	/**
	 * @return transactionListVO
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getReturnTransactionListVO()
	 */
	public TransactionListVO getReturnTransactionListVO() {
		return getAttribute(KEY_RETURNTRANSACTIONLISTVO);
	}

	/**
	 * @param transactionListVO
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#setReturnTransactionListVO(transactionListVO)
	 */
	public void setReturnTransactionListVO(TransactionListVO transactionListVO) {
		setAttribute(KEY_RETURNTRANSACTIONLISTVO, transactionListVO);
	}

	/**
	 * @param key
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#removeReturnTransactionListVO(java.lang.String)
	 */
	public void removeReturnTransactionListVO(String key) {
		removeAttribute(key);
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
	 * @author A-3278
	 * for QF1039
	 * This method displays the total demmurage amount
	 * @return totalDemmurage
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getTotalDemmurage()
	 */
	public Double getTotalDemmurage() {
		return getAttribute(KEY_TOTDEMMURAGE);
	}

	/**
	 * @param totalDemmurage
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#setTotalDemmurage(double)
	 */
	public void setTotalDemmurage(Double totalDemmurage) {
		setAttribute(KEY_TOTDEMMURAGE, totalDemmurage);
	}
	
	/**
	 * @author A-3278
	 * for populating the base currency with the total Demmurage	 
	 * @return baseCurrency
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getBaseCurrency()
	 */
	public String getBaseCurrency() {
		return getAttribute(KEY_BASECURRENCY);
	}

	/**
	 * @param baseCurrency
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#setBaseCurrency(String)
	 */
	public void setBaseCurrency(String baseCurrency) {
		setAttribute(KEY_BASECURRENCY, baseCurrency);
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
	 *      transaction.LoanBorrowULDSessionImpl#getTransactionFilterVO()
	 */
	public TransactionFilterVO getTransactionFilterVO() {
		return getAttribute(KEY_TRANSACTIONFILTERVO);
	}

	/**
	 * @param transactionFilterVO
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#setTransactionFilterVO(transactionFilterVO)
	 */
	public void setTransactionFilterVO(TransactionFilterVO transactionFilterVO) {
		setAttribute(KEY_TRANSACTIONFILTERVO, transactionFilterVO);
	}

	/**
	 * removeTransactionFilterVO
	 * 
	 * @param key
	 */
	public void removeTransactionFilterVO(String key) {
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
	 * @return String
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getTxnStatus()
	 */
	public Collection<OneTimeVO> getTxnStatus() {
		return (Collection<OneTimeVO>) getAttribute(KEY_TXNSTATUS);
	}

	/**
	 * txnStatus
	 * 
	 * @param txnStatus
	 * 
	 */
	public void setTxnStatus(Collection<OneTimeVO> txnStatus) {
		setAttribute(KEY_TXNSTATUS, (ArrayList<OneTimeVO>) txnStatus);
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
	//Added by Nisha for UCR print starts
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
	//Added by Nisha for UCR print ends
  
	
	public String getCtrlRcptNoPrefix(){
		 return (getAttribute(KEY_CTRLRCPTNOPREFIX));
	 }
	 public void  setCtrlRcptNoPrefix(String ctrlRcptNoPrefix){
		 setAttribute(KEY_CTRLRCPTNOPREFIX,ctrlRcptNoPrefix);
	 }
	 public String getCtrlRcptNo() {
			return getAttribute(KEY_CTRLRCPTNO);
		}

		
		public void setCtrlRcptNo(String ctrlRcptNo) {
			setAttribute(KEY_CTRLRCPTNO, ctrlRcptNo);
		}
 /* public Collection<OneTimeVO> getUldCondition(){
		return (Collection<OneTimeVO>) getAttribute(KEY_ULDCONDITION);
	}
	public void setUldCondition(Collection<OneTimeVO> uldCondition){
		setAttribute(KEY_ULDCONDITION, (ArrayList<OneTimeVO>) uldCondition);
	}*/
	
	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
		return getAttribute(KEY_ONETIMEVALUES);
	}

	public void setOneTimeValues(
			HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
		setAttribute(KEY_ONETIMEVALUES, oneTimeValues);
	}
	//added by a-3045 for CR QF1142 starts
	/**
	 * @return String
	 * @see com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.
	 *      transaction.LoanBorrowULDSessionImpl#getTxnStatus()
	 */
	public Collection<OneTimeVO> getMUCStatus() {
		return (Collection<OneTimeVO>) getAttribute(KEY_MUCSTATUS);
	}

	/**
	 * txnStatus
	 * 
	 * @param txnStatus
	 * 
	 */
	public void setMUCStatus(Collection<OneTimeVO> mucStatus) {
		setAttribute(KEY_MUCSTATUS, (ArrayList<OneTimeVO>) mucStatus);
	}
	//added by a-3045 for CR QF1142 ends

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession#getTotalRecords()
	 */
	public Integer getTotalRecords() {
		return getAttribute(TOTAL_RECORDS);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession#setTotalRecords(int)
	 */
	public void setTotalRecords(int totalRecords) {
		setAttribute(TOTAL_RECORDS,totalRecords);
		
		
	}
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

	/**
     * This method returns the systemParmeters from session
     * @return HashMap<String, String>
     * @author A-5782
     */
	public HashMap<String, String> getSystemParameters() {
		
		return getAttribute(KEY_SYSTEMPARAMETERS);
	}
	/**
	 * This method sets the systemParmeters to session
	 * @param systemParameters
	 * @author A-5782
	 */
	public void setSystemParameters(HashMap<String, String> systemParameters) {
		
		setAttribute(KEY_SYSTEMPARAMETERS, systemParameters);
	}
	
}
