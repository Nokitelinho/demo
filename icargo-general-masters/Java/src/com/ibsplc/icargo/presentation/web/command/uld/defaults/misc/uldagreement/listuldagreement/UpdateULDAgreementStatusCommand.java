/*
 * UpdateULDAgreementStatusCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.listuldagreement;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListULDAgreementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDAgreementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class UpdateULDAgreementStatusCommand  extends BaseCommand {
    
	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String SCREENLOAD_FAILURE = "screenload_failure";

    private static final String MODULE_NAME = "uld.defaults";

    private static final String SCREEN_ID = "uld.defaults.listuldagreement";

    private static final String BLANK = "";
    
    private static final String ALL = "ALL";

	private static final String ACTIVATEFLAG = "true";

	private static final String INACTIVATEFLAG = "false";

    private static final String ACTIVESTATUS = "A";

    private static final String INACTIVESTATUS = "I";

	private static final String DELETED = "D";

    /**
     * execute method
	 * 
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	Log log = LogFactory.getLogger("ULD_DEFAULTS");        
		log.entering("ListCommand", "ULDAgreement");
		ListULDAgreementForm form = (ListULDAgreementForm) invocationContext.screenModel;
    	 ApplicationSessionImpl applicationSession = getApplicationSession();
 		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ListULDAgreementSession session = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		String[] agreementNumbers = form.getAgreementNo();
 		String companyCode = logonAttributes.getCompanyCode();
 		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
 		
		if (validateStatus(form)) {
 		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
 		String[] statusChanged = form.getStatusvalues();
 		String[] checked = form.getCheck();
			log.log(Log.INFO, "checked.length----------->" + checked.length);
			log.log(Log.INFO, "statusChanged.length----------->"
					+ statusChanged.length);
 		String status = statusChanged[Integer.parseInt(checked[0])];
			String changedStatus = BLANK;
			if (DELETED.equals(status)) {
 			ErrorVO errorVO = null;
				if (ACTIVATEFLAG.equals(form.getFlag())) {
 				errorVO = new ErrorVO("uld.defaults.cannotactivate");
 			}
				if (INACTIVATEFLAG.equals(form.getFlag())) {
 				errorVO = new ErrorVO("uld.defaults.cannotinactivate");
 			}
 			errors.add(errorVO);
 			invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
 			return;
 		}
			if (ACTIVATEFLAG.equals(form.getFlag())
					&& ACTIVESTATUS.equals(status)) {
 			ErrorVO errorVO = new ErrorVO("uld.defaults.alreadyactivated");
 			errors.add(errorVO);
 			invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
 			return;
 			
 		}
			if (INACTIVATEFLAG.equals(form.getFlag())
					&& INACTIVESTATUS.equals(status)) {
 			ErrorVO errorVO = new ErrorVO("uld.defaults.alreadyinactivated");
 			errors.add(errorVO);
 			invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
 			return;
 		}
			if (ACTIVESTATUS.equals(status)) {
				changedStatus = INACTIVESTATUS;
			} else if (INACTIVESTATUS.equals(status)) {
 			changedStatus = ACTIVESTATUS;
 		}
 		
			log.log(Log.INFO, "status----------->" + status);
			log.log(Log.INFO, "status----------->" + status);
 		
 		ArrayList<String> agreementNumberSelected = new ArrayList<String>();
 		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			/*
			 * for(int k = 0;k<checked.length;k++){
			 * agreementNumberSelected.add(agreementNumbers[Integer.parseInt(checked[k])]); }
			 * try{
			 * delegate.updateULDAgreementStatus(companyCode,agreementNumberSelected,changedStatus);
			 * }catch(BusinessDelegateException exception){
//printStackTrrace()(); error =
			 * handleDelegateException(exception); }
			 */

			// added by A-2412 for optimistic locking
			Collection<ULDAgreementVO> uldAgrVOs = new ArrayList<ULDAgreementVO>();

			log.log(Log.INFO, "session.getUldAgreements().size()----------->"
					+ session.getUldAgreements().size());

			// added by A-2619 for bug-1789 begins
			/*for (int k = 0; k < checked.length; k++) {
				agreementNumberSelected.add(agreementNumbers[Integer
						.parseInt(checked[k])]);
			}
			log.log(Log.INFO, "agreementNumberSelected----------->"
					+ agreementNumberSelected);

			for (ULDAgreementVO vo : session.getUldAgreements()) {
				int agreementNumberSelectedSize = agreementNumberSelected.size();
				ULDAgreementVO uldAgrVo = new ULDAgreementVO();
				for (int i = 0; i < agreementNumberSelectedSize; i++) {
					log.log(Log.INFO, "vo.getAgreementNumber()----------->"+ vo.getAgreementNumber());
					log.log(Log.INFO,
							"agreementNumberSelected.get(i----------->"
									+ agreementNumberSelected.get(i));
					if (vo.getAgreementNumber().equals(
							agreementNumberSelected.get(i))) {
 		log.log(Log.INFO,"session.getUldAgreements().size()----------->"+session.getUldAgreements().size());
 		
 			agreementNumberSelected.add(agreementNumbers[Integer.parseInt(checked[k])]);
 		}
 		log.log(Log.INFO,"agreementNumberSelected----------->"+agreementNumberSelected);
 		
 		for(ULDAgreementVO vo:session.getUldAgreements()){
 			int agreementNumberSelectedSize = agreementNumberSelected.size();
 			for(int i=0;i<agreementNumberSelectedSize;i++){
 				log.log(Log.INFO,"vo.getAgreementNumber()----------->"+vo.getAgreementNumber());
 				log.log(Log.INFO,"agreementNumberSelected.get(i----------->"+agreementNumberSelected.get(i));
	 			if(vo.getAgreementNumber().equals(agreementNumberSelected.get(i))){
 			uldAgrVo.setCompanyCode(companyCode);
	 				uldAgrVo.setAgreementNumber(vo.getAgreementNumber());
	 	 			uldAgrVo.setLastUpdatedTime(vo.getLastUpdatedTime());
	 	 			uldAgrVo.setLastUpdatedUser(vo.getLastUpdatedUser());
	 	 			log.log(Log.INFO,"vo.uldAgrVo()----------->"+uldAgrVo);
 			uldAgrVOs.add(uldAgrVo);
 		}
 			}
 		}
 		
 		try{
 			log.log(Log.INFO,"uldAgrVOs----------->"+uldAgrVOs);
 			log.log(Log.INFO,"changedStatus----------->"+changedStatus);
				}
			}*/
//			 added by A-2619 for bug-1789 begins
			for (int k = 0; k < checked.length; k++) {
				agreementNumberSelected.add(agreementNumbers[Integer
						.parseInt(checked[k])]);
			}
			log.log(Log.INFO, "agreementNumberSelected----------->"
					+ agreementNumberSelected);

			for (ULDAgreementVO vo : session.getUldAgreements()) {
				int agreementNumberSelectedSize = agreementNumberSelected.size();
				ULDAgreementVO uldAgrVo = new ULDAgreementVO();
				for (int i = 0; i < agreementNumberSelectedSize; i++) {
					log.log(Log.INFO, "vo.getAgreementNumber()----------->"
							+ vo.getAgreementNumber());
					log.log(Log.INFO,
							"agreementNumberSelected.get(i----------->"
									+ agreementNumberSelected.get(i));
					if (vo.getAgreementNumber().equals(
							agreementNumberSelected.get(i))) {
						uldAgrVo.setCompanyCode(companyCode);
						uldAgrVo.setAgreementNumber(vo.getAgreementNumber());
						uldAgrVo.setLastUpdatedTime(vo.getLastUpdatedTime());
						uldAgrVo.setLastUpdatedUser(vo.getLastUpdatedUser());
						//uldAgrVo.setTxnType(vo.getTxnType());
						//uldAgrVo.setAgreementStatus(vo.getAgreementStatus());
						log.log(Log.INFO, "vo.uldAgrVo()----------->"
								+ uldAgrVo);
						uldAgrVOs.add(uldAgrVo);
					}
				}
			}
			// added by A-2619 for bug-1789 ends
			try {
				delegate.updateULDAgreementStatus(uldAgrVOs, changedStatus);
			} catch (BusinessDelegateException exception) {
				exception.getMessage();
				error = handleDelegateException(exception);
			} 
			if(error!=null && error.size()>0){
				invocationContext.addAllError(error);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
			// addition ends
			
			//addition for bug6569
			
		/*	form.setAgreementNumber(BLANK);
	    	form.setAgreementListDate(BLANK);
	    	form.setAgreementFromDate(BLANK);
	    	form.setAgreementToDate(BLANK);
	    	form.setPartyCode(BLANK);
	    	form.setAgreementStatus(ALL);
	    	form.setTransactionType(ALL);
	    	form.setPartyType(ALL);
	    	form.setListStatus("");
	    	session.setUldAgreements(null);
	    	session.setULDAgreementFilterVO(null);*/
			form.setAgreementStatus(ALL);
	    	form.setTransactionType(ALL);
	    	form.setPartyType(ALL);
	    	
			ErrorVO errorVO = new ErrorVO("uld.defaults.uldagreement.savedstatus");
			errors.add(errorVO);
 			invocationContext.addAllError(errors);
 			
 			//addition for bug6569 ends
 			
			invocationContext.target = SCREENLOAD_SUCCESS;

		} else {
 			ErrorVO errorVO = new ErrorVO("uld.defaults.statusmismatch");
 			errors.add(errorVO);
 			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
 		}
    }
    
	private boolean validateStatus(ListULDAgreementForm form) {
	
	String[] checked = form.getCheck();
	String[] statusChanged = form.getStatusvalues();
		if (checked.length == 0) {
		return false;
	}
		if (checked.length > 1) {
			for (int i = 0; i < checked.length; i++) {
				if (!(i + 1 == checked.length)) {
					
					if (!statusChanged[Integer.parseInt(checked[i])]
							.equals(statusChanged[Integer
									.parseInt(checked[i + 1])])) {
						return false;

					}
				}
			}

		}

			return true;
}

}
