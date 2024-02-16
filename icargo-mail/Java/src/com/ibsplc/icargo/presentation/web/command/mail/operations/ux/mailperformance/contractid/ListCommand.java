/*
 * ListCommand.java Created on Jul 25, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.contractid;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.GPAContractFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.GPAContractVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-6986
 *
 */
public class ListCommand extends BaseCommand{


	private static final String SUCCESS = "list_success";
	private static final String FAILURE = "list_failure";
	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String GPA_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.gpaempty";
	private static final String NO_RESULTS_FOUND = "mailtracking.defaults.ux.mailperformance.msg.err.noresultsfound";


	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE, "\n\n in the list command----------> \n\n");

    	MailPerformanceForm mailPerformanceForm =
							(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailPerformanceSession =
										getScreenSession(MODULE_NAME,SCREEN_ID);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();
	    GPAContractFilterVO gpaContractFilterVO = null;
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    ArrayList<GPAContractVO> gpaContractVOs = null;

	    MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
	    		new MailTrackingDefaultsDelegate();


	    gpaContractFilterVO = new GPAContractFilterVO();
	    gpaContractFilterVO.setCompanyCode(companyCode);
	    gpaContractFilterVO.setPaCode(mailPerformanceForm.getConPaCode());
	    gpaContractFilterVO.setContractID(mailPerformanceForm.getContractID());
	    gpaContractFilterVO.setDestination(mailPerformanceForm.getDestinationAirport());
	    gpaContractFilterVO.setOrigin(mailPerformanceForm.getOriginAirport());
	    gpaContractFilterVO.setRegion(mailPerformanceForm.getRegion());
	    if ("US001".equals(gpaContractFilterVO.getPaCode())){
	    	mailPerformanceForm.setPaCodeValue("US001");
	    }

	    if(mailPerformanceForm.getConPaCode() == null || ("").equals(mailPerformanceForm.getConPaCode())){

			log.log(Log.FINE, "\n\n GPA CODE EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(GPA_EMPTY);
	    	errors.add(error);
	    	invocationContext.addAllError(errors);
	    	mailPerformanceForm.setScreenFlag("cidRadiobtn");
	    	mailPerformanceForm.setStatusFlag("List_failure");
	    	invocationContext.target = FAILURE;
	    	mailPerformanceSession.setGPAContractVOs(null);
	    	return;

		}

	    try{
	    	gpaContractVOs = (ArrayList<GPAContractVO>)mailTrackingDefaultsDelegate.listContractDetails(gpaContractFilterVO);

	    }catch(BusinessDelegateException businessDelegateException) {

	    	errors = handleDelegateException(businessDelegateException);
	    }

	    if(gpaContractVOs == null || gpaContractVOs.size() == 0 ){
	    	ErrorVO error = new ErrorVO(NO_RESULTS_FOUND,new Object[]{""});
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			mailPerformanceForm.setScreenFlag("cidRadiobtn");
			 mailPerformanceForm.setStatusFlag("List_success");
			invocationContext.target = FAILURE;
			mailPerformanceSession.setGPAContractVOs(null);
			return;
	    }

	    if(gpaContractVOs!= null && gpaContractVOs.size()>0){
	    	mailPerformanceSession.setTotalRecords(gpaContractVOs.size());
			mailPerformanceSession.setGPAContractVOs(gpaContractVOs);
		    mailPerformanceForm.setScreenFlag("cidRadiobtn");
		    mailPerformanceForm.setStatusFlag("List_success");

		    invocationContext.target = SUCCESS;
	    }
	}
}


