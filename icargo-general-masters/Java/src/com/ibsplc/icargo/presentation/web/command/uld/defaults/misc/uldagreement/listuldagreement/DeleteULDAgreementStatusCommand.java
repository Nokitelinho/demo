/*
 * DeleteULDAgreementStatusCommand.java Created on Dec 19, 2005
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
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

/**
 * @author A-1496
 *
 */
public class DeleteULDAgreementStatusCommand  extends BaseCommand {
    
	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String SCREENLOAD_FAILURE = "screenload_failure";

    private static final String MODULE_NAME = "uld.defaults";

    private static final String SCREEN_ID = "uld.defaults.listuldagreement";

    private static final String ACTIVESTATUS = "A";
    
    private static final String BLANK = "";
    
    private static final String ALL = "ALL";

	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	Log log = LogFactory.getLogger("ULD_MANAGEMENT");
		log.entering("Delete Command", "-------uldmnagement");
		ListULDAgreementForm form = (ListULDAgreementForm) invocationContext.screenModel;
		ListULDAgreementSession session = getScreenSession(MODULE_NAME,
				SCREEN_ID);
        ApplicationSessionImpl applicationSession = getApplicationSession();
 		LogonAttributes logonAttributes = applicationSession.getLogonVO();
        Page<ULDAgreementVO> agreementVOs = session.getUldAgreements();
        Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
        String companyCode = logonAttributes.getCompanyCode();
        ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		String[] agreementNumbers = form.getAgreementNo();
		
        
		if (validateStatus(form)) {
    	   String[] checked = form.getCheck();
			ArrayList<String> agreementNumberSelected = new ArrayList<String>();
    	    Collection<ErrorVO> error = new ArrayList<ErrorVO>();
//    		for(int k = 0;k < checked.length; k++){
//    			log.log(Log.INFO,"inside for---------");
//    			agreementNumberSelected.add(agreementNumbers[Integer.parseInt(checked[k])]);
//    			log.log(Log.INFO,"agreementNumber"+agreementNumberSelected.toString());
//    		}
//    		
//    		
//    	   try{
//    			//delegate.updateULDAgreementStatus(companyCode,agreementNumberSelected,OPERATION_FLAG_DELETE);
//    		   delegate.updateULDAgreementStatus(companyCode,agreementNumberSelected,OPERATION_FLAG_DELETE);
//    		}catch(BusinessDelegateException exception){
//printStackTrrace()();
//    			error = handleDelegateException(exception);
//    		}
    	   
//    	 added by A-2412 for optimistic locking
			Collection<ULDAgreementVO> uldAgrVOs = new ArrayList<ULDAgreementVO>();
    		
			/*for(int k = 0;k<checked.length;k++){
    		/*for(int k = 0;k<checked.length;k++){
    			ULDAgreementVO uldAgrVo=new ULDAgreementVO();
    			uldAgrVo.setCompanyCode(companyCode);
    			uldAgrVo.setAgreementNumber(agreementNumbers[Integer.parseInt(checked[k])]);
    			uldAgrVo.setLastUpdatedTime(session.getUldAgreements().get(k).getLastUpdatedTime());
    			uldAgrVo.setLastUpdatedUser(session.getUldAgreements().get(k).getLastUpdatedUser()); 	
    			uldAgrVOs.add(uldAgrVo);
			 }*/

			log.log(Log.INFO, "session.getUldAgreements().size()----------->"
					+ session.getUldAgreements().size());

			//added by A-2619 for bug-1789 begins
			for (int k = 0; k < checked.length; k++) {
				log.log(Log.INFO, "checked" + checked[k]);
				log.log(Log.INFO, "agreementNos" + agreementNumbers[Integer.parseInt(checked[k])]);
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
						log.log(Log.INFO, "vo.uldAgrVo()----------->"
								+ uldAgrVo);
						uldAgrVOs.add(uldAgrVo);
					}
				}
			}
			//added by A-2619 for bug-1789 ends

			try {
				delegate.updateULDAgreementStatus(uldAgrVOs,
						OPERATION_FLAG_DELETE);
			} catch (BusinessDelegateException exception) {
     		for(int k = 0;k<checked.length;k++){
     			agreementNumberSelected.add(agreementNumbers[Integer.parseInt(checked[k])]);
     		}
     		log.log(Log.INFO,"agreementNumberSelected----------->"+agreementNumberSelected);
     		
     		for(ULDAgreementVO vo:session.getUldAgreements()){
     			int agreementNumberSelectedSize = agreementNumberSelected.size();
     			ULDAgreementVO uldAgrVo=new ULDAgreementVO();
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
     		
    			exception.getMessage();
    			error = handleDelegateException(exception);
    		}
    		
    		//addition ends
			
			// addition by A-3278 for bug6569 starts
			
			form.setAgreementNumber(BLANK);
	    	form.setAgreementListDate(BLANK);
	    	form.setAgreementFromDate(BLANK);
	    	form.setAgreementToDate(BLANK);
	    	form.setPartyCode(BLANK);
	    	form.setAgreementStatus(ALL);
	    	form.setTransactionType(ALL);
	    	form.setPartyType(ALL);
	    	form.setListStatus("");
	    	session.setUldAgreements(null);
	    	session.setULDAgreementFilterVO(null);
	    	
			ErrorVO errorVO = new ErrorVO("uld.defaults.uldagreement.deletestatus");
			errors.add(errorVO);
 			invocationContext.addAllError(errors);
 			
 			//addition by A-3278 for bug6569 ends 
    	   
    	   invocationContext.target = SCREENLOAD_SUCCESS;
		} else {
    	  
    	   ErrorVO errorVO = new ErrorVO("uld.defaults.cannotdelete");
    	   errors.add(errorVO);
    	   invocationContext.addAllError(errors);
    	   invocationContext.target = SCREENLOAD_FAILURE;
    	   return;
    	   
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
				if (ACTIVESTATUS.equals(statusChanged[Integer
						.parseInt(checked[i])])) {
					return false;
				}

			}

		}
		if (checked.length == 1) {
			if (ACTIVESTATUS
					.equals(statusChanged[Integer.parseInt(checked[0])])) {
				return false;
			}
    		}
    		return true;
    }
}
