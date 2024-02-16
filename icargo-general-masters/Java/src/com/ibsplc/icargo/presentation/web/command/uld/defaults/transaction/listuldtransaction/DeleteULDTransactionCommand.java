/*
 * DeleteULDTransactionCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.INFO;

import java.util.ArrayList;
import java.util.Collection;



import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class DeleteULDTransactionCommand  extends BaseCommand {

	 /**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");

	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";

	/*
	 * target String if success
	 */
	private static final String DELETE_SUCCESS = "delete_success";

	/*
	 * target String if failure
	 */
	private static final String DELETE_FAILURE = "delete_failure";
	//added by a-3045 for bug 17084 starts
    private static final String UPDATED = "U";
	
	private static final String RESENT = "R";
	
	private static final String SENT = "S";
	
	private static final String MUCREQUIRED = "Q";
	
	private static final String NOT_TO_BE_REPORTED = "N";
	
	private static final String STR_MUCREQUIRED = "MUC Required";
	
	private static final String STR_NOT_TO_BE_REPORTED = "Not to be Reported";
	
	private static final String STR_GENERATED = "MUC Generated";
	//added by a-3045 for bug 17084 ends

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("DeleteULDTransactionCommand","execute");
    	ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;

		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

	Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
	Collection<ErrorVO> errs=new ArrayList<ErrorVO>();
	
		ULDDefaultsDelegate uldDefaultsDelegate =
			   new ULDDefaultsDelegate();

		TransactionVO transactionVO = new TransactionVO();
		TransactionListVO   transactionListVO = listULDTransactionSession.getTransactionListVO();
    	Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
    	Page<ULDTransactionDetailsVO>  uldTransactionDetailsVOs
													= transactionListVO.getTransactionDetailsPage();
		String[] primaryKey = listULDTransactionForm.getUldDetails();

		if (primaryKey != null && primaryKey.length > 0) {
			int cnt=0;
			int index = 0;
			int primaryKeyLen = primaryKey.length;
			if (uldTransactionDetailsVOs != null && uldTransactionDetailsVOs.size() != 0) {
				for (ULDTransactionDetailsVO uldTransactionDetailsVO : uldTransactionDetailsVOs) {
					index++;
					String primaryKeyFromVO =new StringBuilder(uldTransactionDetailsVO.getUldNumber())
																		.append(uldTransactionDetailsVO.getTransactionRefNumber())
																		.append(index).toString();
						if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
							equalsIgnoreCase(primaryKey[cnt].trim())) {
						             uldTransactionDetailsVO.setOperationalFlag("D");
        		    		         uldTxnDetailsVOs.add(uldTransactionDetailsVO);
        		    	          	 cnt ++;
					   }
				  }
			}
		}
		transactionVO.setOperationalFlag("D");
		transactionVO.setUldTransactionDetailsVOs(uldTxnDetailsVOs);
		/*
		 * Construct lock vos for implicit locking
		 */
		Collection<LockVO> locks = prepareLocksForSave(transactionVO);
		log.log(Log.FINE, "LockVO for implicit check", locks);
		try {
			 errors = uldDefaultsDelegate.saveULDTransaction(transactionVO,locks);

		     }catch (BusinessDelegateException businessDelegateException) {
			  businessDelegateException.getMessage();
	          errors = handleDelegateException(businessDelegateException);
		     }
		     if (errors != null && errors.size() > 0) {
					invocationContext.addAllError(errors);
			}
		     TransactionFilterVO transactionFilterVO =new TransactionFilterVO();
				transactionFilterVO =  listULDTransactionSession.getTransactionFilterVO();
				TransactionListVO   txnListVO = new TransactionListVO();
				try {
					txnListVO = uldDefaultsDelegate.listULDTransactionDetails(transactionFilterVO);
					 }catch (BusinessDelegateException businessDelegateException) {
					  businessDelegateException.getMessage();
					  errs = handleDelegateException(businessDelegateException);
				     }
				     if (errs != null && errs.size() > 0) {
							invocationContext.addAllError(errs);
					}
				     if(txnListVO == null) {
				    	 ErrorVO errorVO = new ErrorVO("uld.defaults.transaction.noenquiriesfound");
						 errorVO.setErrorDisplayType(INFO);
						 errs.add(errorVO);
						 invocationContext.addAllError(errs);
				     }
						log.log(Log.FINE,
							"****txnListVO try**************************",
							txnListVO.getTransactionDetailsPage());
					if(txnListVO.getTransactionDetailsPage() != null && txnListVO.getTransactionDetailsPage().size() > 0){
				     for(ULDTransactionDetailsVO vo: txnListVO.getTransactionDetailsPage()){
				    	 if(vo.getMucIataStatus().equals(UPDATED) 
									|| vo.getMucIataStatus().equals(RESENT) 
									|| vo.getMucIataStatus().equals(SENT)){
								
								vo.setMucIataStatus(STR_GENERATED);
								log
										.log(
												Log.FINE,
												"vo.getMucIataStatus() ~~~~~~~~~~~~~~~~~",
												vo.getMucIataStatus());
							}else if(vo.getMucIataStatus().equals(MUCREQUIRED)){
								vo.setMucIataStatus(STR_MUCREQUIRED);
							}else if(vo.getMucIataStatus().equals(NOT_TO_BE_REPORTED)){
								vo.setMucIataStatus(STR_NOT_TO_BE_REPORTED);
								log
										.log(
												Log.FINE,
												"vo.getMucIataStatus() ~~~~~~~~~~~~~~~~~",
												vo.getMucIataStatus());
							}
				     }
				     }
						//added by a-3045 for bug 17084 ends
				     listULDTransactionSession.setTransactionListVO(txnListVO);
					 listULDTransactionSession.setTransactionFilterVO(transactionFilterVO);
		listULDTransactionForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	//maintainULDForm.setStatusFlag("screenload");
    	invocationContext.target =DELETE_SUCCESS;

    }

	/*
	 * Added by Ayswarya
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
				lock.setDescription(uldVO.getUldNumber());
				lock.setRemarks(uldVO.getUldNumber());
				log.log(Log.FINE, "\n lock------->>", lock);
				locks.add(lock);
			}
		}
		return locks;
	}
}
