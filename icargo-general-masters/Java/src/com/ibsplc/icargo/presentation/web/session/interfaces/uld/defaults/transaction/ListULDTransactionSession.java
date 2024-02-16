/*
 * ListULDTransactionSession.javaCreated on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction;

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
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1347
 * 
 */
public interface ListULDTransactionSession extends ScreenSession {


	/**
	 * @return String
	 */
	public ULDTransactionDetailsVO getULDTransactionDetailsVO();

	/**
	 * @param uldTransactionDetailsVO
	 */
	public void setULDTransactionDetailsVO(
			ULDTransactionDetailsVO uldTransactionDetailsVO);

	/**
	 * @return String
	 */
	public AccessoryTransactionVO getAccessoryTransactionVO();

	/**
	 * @param accessoryTransactionVO
	 */
	public void setAccessoryTransactionVO(
			AccessoryTransactionVO accessoryTransactionVO);

	/**
	 * @return String
	 */
	public TransactionListVO getTransactionListVO();

	/**
	 * @param transactionListVO
	 */
	public void setTransactionListVO(TransactionListVO transactionListVO);

	/**
	 * @param key
	 */
	public void removeTransactionListVO(String key);

	/**
	 * @return String
	 */
	public TransactionListVO getReturnTransactionListVO();

	/**
	 * @param transactionListVO
	 */
	public void setReturnTransactionListVO(TransactionListVO transactionListVO);

	/**
	 * @param key
	 */
	public void removeReturnTransactionListVO(String key);

	/**
	 * @return String
	 */
	public TransactionFilterVO getTransactionFilterVO();

	/**
	 * @param transactionFilterVO
	 */
	public void setTransactionFilterVO(TransactionFilterVO transactionFilterVO);

	/**
	 * @param key
	 */
	public void removeTransactionFilterVO(String key);

	/**
	 * @return String
	 */
	public String getCompanyCode();

	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode);

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
	
	//Added by a-3278 for QF1015 on 24Jul08
	/**
	 * @return double
	 */
	public Double getTotalDemmurage();

	/**
	 * @param totalDemmurage
	 */
	public void setTotalDemmurage(Double totalDemmurage);
	//a-3278 ends
	
	/**
	 * @return String
	 */
	public String getBaseCurrency();

	/**
	 * @param baseCurrency
	 */
	public void setBaseCurrency(String baseCurrency);

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
	 * Method for getting txnTypes from session
	 * 
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getTxnTypes();

	/**
	 * Method for setting txnTypes to session
	 * 
	 * @param txnType
	 */
	public void setTxnTypes(Collection<OneTimeVO> txnTypes);

	/**
	 * Method for getting txnNatures from session
	 * 
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getTxnNatures();

	/**
	 * Method for setting txnNatures to session
	 * 
	 * @param txnNatures
	 */
	public void setTxnNatures(Collection<OneTimeVO> txnNatures);

	/**
	 * Method for getting accessoryCodes from session
	 * 
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getAccessoryCodes();

	/**
	 * Method for setting accessoryCodes to session
	 * 
	 * @param accessoryCodes
	 */
	public void setAccessoryCodes(Collection<OneTimeVO> accessoryCodes);

	/**
	 * Method for getting partyTypes from session
	 * 
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getPartyTypes();

	/**
	 * Method for setting partyTypes to session
	 * 
	 * @param partyTypes
	 */
	public void setPartyTypes(Collection<OneTimeVO> partyTypes);

	/**
	 * Method for getting txnStatus from session
	 * 
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getTxnStatus();

	/**
	 * Method for setting txnStatus to session
	 * 
	 * @param txnStatus
	 */
	public void setTxnStatus(Collection<OneTimeVO> txnStatus);
/**
 * 
 * @return
 */
	public ULDFlightMessageReconcileDetailsVO getULDFlightMessageReconcileDetailsVO();
/**
 * 
 * @param uldFlightMessageReconcileDetailsVO
 */
	public void setULDFlightMessageReconcileDetailsVO(
			ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO);
/**
 * 
 * @param reconcileDetailsVO
 */
	public void setULDSCMReconcileDetailsVO(
			ULDSCMReconcileDetailsVO reconcileDetailsVO);
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
	
	//Added by Preet on 4 th Jan For CRN Number starts
	//public Collection<OneTimeVO> getUldCondition();
	//public void setUldCondition(Collection<OneTimeVO> uldCondition);
	HashMap<String,Collection<OneTimeVO>> getOneTimeValues();
	void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
    public String getCtrlRcptNoPrefix();
	public void  setCtrlRcptNoPrefix(String ctrlRcptNoPrefix);
	public String getCtrlRcptNo();
	public void setCtrlRcptNo(String ctrlRcptNo);
//	Added by Preet on 4 th Jan For CRN Number ends
	
	// Added by nisha for UCR print starts
	public Collection<String> getUldNumbersSelected();
	public void  setUldNumbersSelected(Collection<String> uldNumbersSelected);
	public Collection<Integer> getTxnNumbersSelected();
	public void  setTxnNumbersSelected(Collection<Integer> txnNumbersSelected);
	//Added by nisha for UCR print ends
	//added by a-3045 for CR QF1142 starts
	/**
	 * Method for getting mucStatus from session
	 * 
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getMUCStatus();

	/**
	 * Method for setting mucStatus to session
	 * 
	 * @param mucStatus
	 */
	public void setMUCStatus(Collection<OneTimeVO> mucStatus);
	//added by a-3045 for CR QF1142 ends
	
	public void setTotalRecords(int totalRecords);
	
	public Integer getTotalRecords();
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
	
	/**
     * This method returns the systemParmeters from session
     * @return HashMap<String, String>
     */
	public HashMap<String, String> getSystemParameters();
	
	/**
	 * This method sets the systemParmeters to session
	 * @param systemParameters
	 */
	public void setSystemParameters(HashMap<String, String> systemParameters);
}
