/*
 * LoanBorrowULDSession.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction;

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
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.time.LocalDate;
/**
 * @author A-1347
 *
 */
public interface LoanBorrowULDSession extends ScreenSession {
	    
    
	/**
	 * @return String
	 */
	public String getCompanyCode();

	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode);
	/**
	 * @return String
	 */
	public String getCtrlRcptNo();

	/**
	 * @param ctrlRcptNo
	 */
	public void setCtrlRcptNo(String ctrlRcptNo);

	/**
	 * @param key
	 */
	public void removeCompanyCode(String key);

	/**
	 * @return String
	 */
	public String getStationCode();

	/**
	 * @param stationCode
	 */
	public void setStationCode(String stationCode);

	/**
	 * @param key
	 */
	public void removeStationCode(String key);

	/**
	 * @return String
	 */
	public String getUserId();

	/**
	 * @param userId
	 */
	public void setUserId(String userId);

	/**
	 * @param key
	 */
	public void removeUserId(String key);
	
	/**
	 * @return TransactionVO
	 */
    public TransactionVO getTransactionVO();
    /**
	 * @param transactionVO
	 */
    public void setTransactionVO(TransactionVO transactionVO);
    
    /**
	 * @param key
	 */
	public void removeTransactionVO(String key);
      
	/**
	 *  Method for getting txnTypes from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getTxnTypes();
	/**
	 * Method for setting txnTypes to session
	 * @param txnType
	 */
	public void setTxnTypes(Collection<OneTimeVO> txnTypes);
	
	/**
	 *  Method for getting txnNatures from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getTxnNatures();
	/**
	 * Method for setting txnNatures to session
	 * @param txnNatures
	 */
	public void setTxnNatures(Collection<OneTimeVO> txnNatures);
	
	/**
	 *  Method for getting accessoryCodes from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getAccessoryCodes();
	/**
	 * Method for setting accessoryCodes to session
	 * @param accessoryCodes
	 */
	public void setAccessoryCodes(Collection<OneTimeVO> accessoryCodes);
	
	/**
	 *  Method for getting partyTypes from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getPartyTypes();
	/**
	 * Method for setting partyTypes to session
	 * @param partyTypes
	 */
	public void setPartyTypes(Collection<OneTimeVO> partyTypes);
	/**
	 * 
	 * @return
	 */
	public ULDFlightMessageReconcileDetailsVO getULDFlightMessageReconcileDetailsVO();
/**
 * 
 * @param uldFlightMessageReconcileDetailsVO
 */
	public void setULDFlightMessageReconcileDetailsVO(ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO);
	/**
	 * 
	 * @param reconcileDetailsVO
	 */
	public void setULDSCMReconcileDetailsVO(ULDSCMReconcileDetailsVO reconcileDetailsVO);
	/**
	 * 
	 * @return
	 */
	public ULDSCMReconcileDetailsVO getULDSCMReconcileDetailsVO();
	/**
	 * 
	 * @return
	 */
	public String getPageURL();
/**
 * 
 * @param pageurl
 */
	public void setPageURL(String pageurl);
	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getReceiptNo(); 
	/**
	 * 
	 * @param receiptNo
	 */
	public void setReceiptNo(ArrayList<String> receiptNo);
	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getTxnRefNo(); 
	/**
	 * 
	 * @param txnRefNo
	 */
	public void setTxnRefNo(ArrayList<String> txnRefNo);
	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getULDReceipt(); 
	/**
	 * 
	 * @param uldReceipt
	 */
	public void setULDReceipt(ArrayList<String> uldReceipt);
	
	/**
	 * @return Returns the oneTimeValues.
	 */
	HashMap<String,Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */

	void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<ULDTransactionDetailsVO> getSelectedULDColl(); 
	/**
	 * 
	 * @param selectedULDColl
	 */
	public void setSelectedULDColl(ArrayList<ULDTransactionDetailsVO> selectedULDColl);
    
	//Added by A-2412
	public Collection<OneTimeVO>  getConditionCodes();
	public void setConditionCodes(Collection<OneTimeVO> conditionCodes);
	// Addition ends
	
//	Added by A-2619
	 public LocalDate getUldLastUpdateTime();
	 public void  setUldLastUpdateTime(LocalDate dte);	 
	 public String getUldLastUpdateUser();
	 public void  setUldLastUpdateUser(String lastUpdateUser);
	//ends
	 
	 //Added by A-2412 on 18th Oct For CRN Editable CR 
	 public String getCtrlRcptNoPrefix();
	 public void  setCtrlRcptNoPrefix(String ctrlRcptNoPrefix);
	// Addition by A-2412 on 18th Oct For CRN Editable CR ends 
	 //Added by Preet on 12 th Dec for UCR print starts
		public Collection<String> getUldNumbersSelected();
		public void  setUldNumbersSelected(Collection<String> uldNumbersSelected);
		public Collection<Integer> getTxnNumbersSelected();
		public void  setTxnNumbersSelected(Collection<Integer> txnNumbersSelected);
		public String getLoanUcrPrint();
	    public void  setLoanUcrPrint(String isLoanUcrPrint);
	//Added by Preet on 12 th Dec for UCR print ends
	    
//	  added by a-3045
		/**
		 *  Method for getting uldNature from session
		 * @return Collection<OneTimeVO>
		 */
		public Collection<OneTimeVO>  getUldNature();
		/**
		 * Method for setting uldNature to session
		 * @param txnType
		 */
		public void setUldNature(Collection<OneTimeVO> uldNature);
		
		public ULDAgreementVO getAgreementVO();

		public void setAgreementVO(ULDAgreementVO uldAgreementVO);
		
//		added by a-3045
		/**
	     * This method returns the ULDDamageDetailsListVO in session
	     * @return Page<ClearanceListingVO>
	     */

		public Collection<ULDServiceabilityVO> getULDServiceabilityVOs();

		/**
		 * This method sets the ULDServiceabilityVO in session
		 * @param paramCode
		 */
		public void setULDServiceabilityVOs(Collection<ULDServiceabilityVO> paramCode);
}
