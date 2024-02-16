/*
 * SaveSignatureCommand.java Created on Oct 25, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.shared.signature.vo.CaptureSignatureVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.signature.CaptureSignatureDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.shared.signature.CaptureSignatureForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class SaveSignatureCommand extends BaseCommand {
	/**
	 * Logger for SaveSignatureCommand
	 */
	private Log log = LogFactory.getLogger("Loan Borrow ULD");
	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowuld";

	private static final String ACTION_SUCCESS = "action_success";
	private static final String ACTION_FAILURE = "action_failure";
	private static final String ACTION_CLOSE = "Close";


    public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
    	log.entering(MODULE_NAME,"execute");
	    CaptureSignatureForm signatureForm =
	    	(CaptureSignatureForm)invocationContext.screenModel;
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
	    CaptureSignatureVO signatureVO = new CaptureSignatureVO();
	    CaptureSignatureDelegate signatureDelegate = new CaptureSignatureDelegate();
	    populateLogonAttributes(signatureVO);
	    populateSignatureVO(signatureVO,signatureForm);
	    ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
	    TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();

	    Collection<ErrorVO> errors = null;
		Collection<LockVO> locks = prepareLocksForSave(transactionVO);
	    try {
	    	Integer signatureId = signatureDelegate.saveSignature(signatureVO);
	    	log.log(Log.INFO, "\n\n--->SignatureID-->>:", signatureId);
			updateSignatureId(transactionVO,signatureId,signatureForm);
	    	updateTransactionRefNumber(transactionVO,loanBorrowULDSession);
	    	log.log(Log.INFO, "\n\n--->transactionVO-->>:", transactionVO);
			errors = delegate.saveULDTransaction(transactionVO,locks);
	    }catch (BusinessDelegateException businessDelegateException) {
	    	businessDelegateException.getMessage();
	    	errors = new ArrayList<ErrorVO>();
			errors = handleDelegateException(businessDelegateException);
		    if (errors != null && errors.size() > 0) {
	 			invocationContext.addAllError(errors);
	 			invocationContext.target = ACTION_FAILURE;
	 			return;
			}
		}
	    signatureForm.setSignatureAction(ACTION_CLOSE);
	    invocationContext.target = ACTION_SUCCESS;
	    log.exiting(MODULE_NAME,"execute");
    }
	/**
	 * @author a-3045
	 * @param CaptureSignatureVO
	 * @param CaptureSignatureForm
	 * @return
	 */
	private void populateSignatureVO(
			CaptureSignatureVO signatureVO, CaptureSignatureForm signatureForm){
		signatureVO.setSignature(signatureForm.getSignature());
	}

	/**
	 * @author a-3045
	 * @param CaptureSignatureVO
	 * @return
	 */
	private void populateLogonAttributes(CaptureSignatureVO signatureVO) {
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    signatureVO.setAirportCode(logonAttributes.getAirportCode());
	    signatureVO.setCompanyCode(logonAttributes.getCompanyCode());
	}

	/**
	 * @author a-3045
	 * @param TransactionVO
	 * @param int
	 * @return
	 */
	private void updateSignatureId(TransactionVO transactionVO,Integer signatureId,CaptureSignatureForm signatureForm) {
		log.entering("SaveSignatureCommand","updateSignatureId");
		transactionVO.setOperationalFlag(TransactionVO.OPERATION_FLAG_UPDATE);
		for(ULDTransactionDetailsVO vo:transactionVO.getUldTransactionDetailsVOs()){
			//vo.setSignatureId(signatureId);
			//vo.setSignedByName(signatureForm.getSignedByName());
			vo.setLastUpdateTime(null);
		}
	}

	/**
	 * @author a-3045
	 * @param transactionVO
	 * @return Collection<LockVO>
	 * @throws BusinessDelegateException
	 */
	private Collection<LockVO> prepareLocksForSave(
			TransactionVO transactionVO) {
		log.log(Log.FINE, "\n prepareLocksForSave------->>", transactionVO);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<LockVO> locks = new ArrayList<LockVO>();
		Collection<ULDTransactionDetailsVO> uldDetailsVOs = transactionVO
				.getUldTransactionDetailsVOs();
		if (uldDetailsVOs != null && uldDetailsVOs.size() > 0) {
			for (ULDTransactionDetailsVO uldVO : uldDetailsVOs) {
				ULDLockVO lock = new ULDLockVO();
				lock.setAction(LockConstants.ACTION_LOANBORROWULD);
				lock.setClientType(ClientType.WEB);
				lock.setCompanyCode(logonAttributes.getCompanyCode());
				lock.setScreenId(SCREEN_ID);
				lock.setStationCode(logonAttributes.getStationCode());
				lock.setUldNumber(uldVO.getUldNumber());
				lock.setRemarks(uldVO.getUldNumber());
				lock.setDescription(uldVO.getUldNumber());
				log.log(Log.FINE, "\n lock------->>", lock);
				locks.add(lock);
			}
		}
		return locks;
	}

	/**
	 * @author a-3045
	 * @param TransactionVO
	 * @param int
	 * @return
	 */
	private void updateTransactionRefNumber(TransactionVO transactionVO,LoanBorrowULDSession session) {
		log.entering("SaveSignatureCommand","updateTransactionRefNumber");
		transactionVO.setOperationalFlag(TransactionVO.OPERATION_FLAG_UPDATE);
		Collection<String> txnRefNums = session.getTxnRefNo();
		String[]txnrefNum = null;
		if(txnRefNums != null && txnRefNums.size() > 0){
			for(String refNum : txnRefNums){
				txnrefNum = refNum.split("~");
				for(ULDTransactionDetailsVO vo:transactionVO.getUldTransactionDetailsVOs()){
					if(vo.getUldNumber().equals(txnrefNum[0])){
						vo.setTransactionRefNumber(Integer.parseInt(txnrefNum[1]));
						log
								.log(Log.FINE, "\n txnrefNum[1]------->>",
										txnrefNum);
					}
				}
			}
		}
	}

}
