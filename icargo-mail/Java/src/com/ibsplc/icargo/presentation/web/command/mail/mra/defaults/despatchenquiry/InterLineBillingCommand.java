/*
 * InterLineBillingCommand.java Created on Sep 11,2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchenquiry;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineBillingDetailVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnqSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2554
 *
 */
public class InterLineBillingCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS DespatchEnquiry");

	private static final String MODULE = "mailtracking.mra.defaults";
	private static final String SCREENID = "mailtracking.mra.defaults.despatchenquiry";
	private static final String CLASS_NAME = "InterLineBillingCommand";
	private static final String COMMAND_SUCCESS = "command_success";
	private static final String COMMAND_FAILURE = "command_failure";
	private static final String NO_RESULTS = "mailtracking.mra.defaults.despatchenquiry.noresultsfound";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.dsnselectpopup";


	/**
	 * Method  implementing ListingInterline Billings
	 * @author A-2554
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");
    	DespatchEnqSession despatchEnqSession=getScreenSession(MODULE,SCREENID);
    	DSNPopUpSession popUpSession=getScreenSession(MODULE,SCREEN_ID);
    	DespatchEnquiryForm despatchEnquiryForm=(DespatchEnquiryForm)invocationContext.screenModel;
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	Collection<AirlineBillingDetailVO> airlineBillingDetailVOs = new ArrayList<AirlineBillingDetailVO>();
    	AirlineBillingDetailVO airlineBillingDetailVO =new AirlineBillingDetailVO();

		DSNPopUpVO popUpVO = null;
		popUpVO = popUpSession.getSelectedDespatchDetails();
		if(popUpVO == null) {
			popUpVO = despatchEnqSession.getDispatchFilterVO();
		}
		if(popUpVO != null) {
			airlineBillingDetailVO.setCompanycode(popUpVO.getCompanyCode());
			airlineBillingDetailVO.setBillingBasis(popUpVO.getBlgBasis());
			airlineBillingDetailVO.setPoaCode(popUpVO.getGpaCode());
			airlineBillingDetailVO.setConsignmentDocumentNumber(popUpVO.getCsgdocnum());
			airlineBillingDetailVO.setConsignmentSequenceNumber(popUpVO.getCsgseqnum());
		}
		
		if(despatchEnquiryForm.getListed()!=null  && 
				despatchEnquiryForm.getListed().trim().length()>0 &&
				("N".equals(despatchEnquiryForm.getListed())) && 
				!("".equals(despatchEnquiryForm.getListed()))){
			despatchEnquiryForm.setListed("N");
			invocationContext.target=COMMAND_FAILURE;
			return;
		}else{
    	MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();

    	try {
			airlineBillingDetailVOs=delegate.findInterLineBillingDetails(airlineBillingDetailVO);
		} catch (BusinessDelegateException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			ErrorVO err=new ErrorVO(NO_RESULTS);
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			err.setErrorCode(e.getMessage());
			errors.add(err);
			errors=handleDelegateException(e);

		}

    	if(airlineBillingDetailVOs==null || airlineBillingDetailVOs.size()==0){

    	//despatchEnquiryForm.setListed("N");
		//despatchEnqSession.removeAllAttributes();
		ErrorVO err=new ErrorVO(NO_RESULTS);
		err.setErrorDisplayType(ErrorDisplayType.ERROR);
		errors.add(err);
		invocationContext.addAllError(errors);
		invocationContext.target=COMMAND_FAILURE;
    	}

    	despatchEnquiryForm.setListed("Y");
    	despatchEnqSession.setAirlineBillingDetailsVO(airlineBillingDetailVOs);
       	invocationContext.target = COMMAND_SUCCESS;

		}

    }

}
