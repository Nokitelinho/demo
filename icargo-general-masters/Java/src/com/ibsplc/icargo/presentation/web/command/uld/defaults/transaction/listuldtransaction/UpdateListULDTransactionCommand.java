/*
 * UpdateListULDTransactionCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class UpdateListULDTransactionCommand  extends BaseCommand {

	 /**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAMET = "uld.defaults";
	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_IDT = "uld.defaults.loanborrowuld";

	/**
	 *Target  if success
	 */
	private static final String UPDATE_SUCCESS = "update_success";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("UpdateListULDTransactionCommand","execute");
    	ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;

		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAMET, SCREEN_IDT);

	 //  Collection<ErrorVO> errors=new ArrayList<ErrorVO>();

		/*ULDDefaultsDelegate uldDefaultsDelegate =
			   new ULDDefaultsDelegate();
*/
		TransactionVO transactionVO = new TransactionVO();
		TransactionListVO   transactionListVO = listULDTransactionSession.getTransactionListVO();
    	Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
    	Page<ULDTransactionDetailsVO>  uldTransactionDetailsVOs
													= transactionListVO.getTransactionDetailsPage();
    	//Added by A-2408 for pagination starts
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
								             uldTransactionDetailsVO.setOperationalFlag("U");
								             uldTransactionDetailsVO.setSelectNumber(cnt);
		        		    		         uldTxnDetailsVOs.add(uldTransactionDetailsVO);
		        		    	          	 cnt ++;
							   }
						  }
					}
				}
				transactionVO.setOperationalFlag("U");
				transactionVO.setUldTransactionDetailsVOs(uldTxnDetailsVOs);
				loanBorrowULDSession.setTransactionVO(transactionVO);
		//ends
		ULDTransactionDetailsVO modTxnVO = new ULDTransactionDetailsVO();
		if (uldTxnDetailsVOs != null && uldTxnDetailsVOs.size() != 0) {
			//for (ULDTransactionDetailsVO uldTxnDetailsVO : uldTxnDetailsVOs) {
				 //listULDTransactionSession.setULDTransactionDetailsVO(uldTxnDetailsVO);
			listULDTransactionForm.setModDisplayPage("1");
			listULDTransactionForm.setModCurrentPage("1");
			listULDTransactionForm.setModLastPageNum(Integer.toString(uldTxnDetailsVOs.size()));
			listULDTransactionForm.setModTotalRecords(Integer.toString(uldTxnDetailsVOs.size()));
				try {
					BeanHelper.copyProperties(modTxnVO, uldTxnDetailsVOs.iterator().next()) ;
				} catch (SystemException e) {
					log.log(Log.INFO,"Bean Excepton");
				}
				// break;
			//}
	   }
		listULDTransactionSession.setULDTransactionDetailsVO(modTxnVO);
	log.log(Log.INFO, "form.getTotalRecordsssss", listULDTransactionForm.getModTotalRecords());
			listULDTransactionForm.setMode(FLAG_YES);


		listULDTransactionForm.setScreenStatusFlag(
		      ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
        invocationContext.target =UPDATE_SUCCESS;

         	log.exiting("UpdateListULDTransactionCommand","execute");



    }

}
