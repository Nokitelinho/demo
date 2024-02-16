/*
 * ListContainerCommand.java Created on Sep 26, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.forcemajeurerequest;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestLockVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.ForceMajeureRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.forcemajeurerequest.RequestForceMajeureCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-8527	:	22-Nov-2018	:	Draft
 */

public class RequestForceMajeureCommand extends BaseCommand {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.forcemajeure";
	private static final String TARGET = "RequestFM_success";
	private Log log = LogFactory.getLogger("Mail Operations force majeure request");
	private static final String FORCE_MAJEURE_INITIATED = "Force Majeure request Initiated";
	public static final String ACTION_REQFORCEMAJ = "REQFORMJR";
	private static final String FORCE_MAJEURE_FAILED = "Force_majeure_failed";
	/**
	 * 
	 */
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE, "\n\n in the list command of New tab force Majeure Request----------> \n\n");
		ForceMajeureRequestForm forceMajeureRequestForm = (ForceMajeureRequestForm) invocationContext.screenModel;
		invocationContext.target = TARGET;
		ForceMajeureRequestSession forceMajeureRequestSession = 
				getScreenSession(MODULE_NAME,SCREEN_ID);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();
	    log.log(Log.FINE, forceMajeureRequestForm);		
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    ForceMajeureRequestFilterVO forceMajeureRequestFilterVO = new ForceMajeureRequestFilterVO();
		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		
		if("".equals(forceMajeureRequestForm.getNewTabRemarks()) || forceMajeureRequestForm.getNewTabRemarks().length()==0){
			ErrorVO error = new ErrorVO("mailtracking.defaults.forcemajeure.remarksmandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
            invocationContext.target = FORCE_MAJEURE_FAILED;
            return;
		}
		else{
		forceMajeureRequestFilterVO.setReqRemarks(forceMajeureRequestForm.getNewTabRemarks());
		}
		
		invoiceTransactionLogVO.setCompanyCode(companyCode);
		invoiceTransactionLogVO.setInvoiceType(MailConstantsVO.FORCE_MAJEURE_REQUEST);
		invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
   		invoiceTransactionLogVO.setPeriodFrom( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
   		invoiceTransactionLogVO.setPeriodTo( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
   		invoiceTransactionLogVO.setInvoiceGenerationStatus(MailConstantsVO.INITIATED_STATUS);
   		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
		invoiceTransactionLogVO.setRemarks(FORCE_MAJEURE_INITIATED);
		invoiceTransactionLogVO.setSubSystem(MailConstantsVO.MAIL_SUBSYSTEM);
		invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
		try{
			invoiceTransactionLogVO = delegate.initTxnForForceMajeure(invoiceTransactionLogVO);
		}catch (BusinessDelegateException businessDelegateException) {
		}
		forceMajeureRequestFilterVO=forceMajeureRequestSession.getFilterParamValues();
		//Added by A-8527 for IASCB-41731
		forceMajeureRequestFilterVO.setReqRemarks(forceMajeureRequestForm.getNewTabRemarks());
		forceMajeureRequestFilterVO.setTransactionCode(invoiceTransactionLogVO.getTransactionCode());
		forceMajeureRequestFilterVO.setTxnSerialNumber(invoiceTransactionLogVO.getSerialNumber());
		StringBuilder filterParameters = new StringBuilder();
		if(forceMajeureRequestFilterVO!=null){

		if(forceMajeureRequestFilterVO.getOrginAirport()!=null && forceMajeureRequestFilterVO.getOrginAirport().trim().length()>0){
		filterParameters.append("ORG:"
				+ forceMajeureRequestFilterVO.getOrginAirport().trim() + ";");
		}else{
			forceMajeureRequestFilterVO.setOrginAirport("");
		}
		if(forceMajeureRequestFilterVO.getDestinationAirport()!=null && forceMajeureRequestFilterVO.getDestinationAirport().trim().length()>0){
		filterParameters.append("DST:"
				+ forceMajeureRequestFilterVO.getDestinationAirport().trim() + ";");
		}else{
			forceMajeureRequestFilterVO.setDestinationAirport("");
		}
		if(forceMajeureRequestFilterVO.getPoaCode()!=null && forceMajeureRequestFilterVO.getPoaCode().trim().length()>0){
		filterParameters.append("POA:"
				+ forceMajeureRequestFilterVO.getPoaCode().trim() + ";");
		}else{
			forceMajeureRequestFilterVO.setPoaCode("");
		}
		if(forceMajeureRequestFilterVO.getViaPoint()!=null && forceMajeureRequestFilterVO.getViaPoint().trim().length()>0){
		filterParameters.append("VPT:"
				+ forceMajeureRequestFilterVO.getViaPoint().trim() + ";");
		}else{
			forceMajeureRequestFilterVO.setViaPoint("");
		}
		if(forceMajeureRequestFilterVO.getAffectedAirport()!=null && forceMajeureRequestFilterVO.getAffectedAirport().trim().length()>0){
		filterParameters.append("AFAPT:"
				+ forceMajeureRequestFilterVO.getAffectedAirport().trim() + ";");
		}else{
			forceMajeureRequestFilterVO.setAffectedAirport("");
		}
		if(forceMajeureRequestFilterVO.getFlightNumber()!=null && forceMajeureRequestFilterVO.getFlightNumber().trim().length()>0){
		filterParameters.append("FLNO:"
				+ forceMajeureRequestFilterVO.getFlightNumber().trim() + ";");
		}else{
			forceMajeureRequestFilterVO.setFlightNumber("");
		}
		if(forceMajeureRequestFilterVO.getFlightDate()!=null ){
		filterParameters.append("FLDAT:"
				+ forceMajeureRequestFilterVO.getFlightDate().toDisplayFormat() + ";");
		}
		if(forceMajeureRequestFilterVO.getFromDate()!=null ){
		filterParameters.append("FRDAT:"
				+ forceMajeureRequestFilterVO.getFromDate().toDisplayFormat(true) + ";");
		}
		if(forceMajeureRequestFilterVO.getToDate()!=null){
		filterParameters.append("TODAT:"
				+ forceMajeureRequestFilterVO.getToDate().toDisplayFormat(true) + ";");   
		}
		if(forceMajeureRequestFilterVO.getSource()!=null && forceMajeureRequestFilterVO.getSource().trim().length()>0){
		filterParameters.append("SRC:"
				+ forceMajeureRequestFilterVO.getSource()+ ";");
		}
		if(forceMajeureRequestFilterVO.getScanType()!=null && forceMajeureRequestFilterVO.getScanType().trim().length()>0){
			filterParameters.append("SCNTYP:"
					+ forceMajeureRequestFilterVO.getScanType().trim());
		}
		forceMajeureRequestFilterVO.setFilterParameters(filterParameters.toString());
		forceMajeureRequestFilterVO.setLastUpdatedUser(logonAttributes.getUserId());
		/*if("".equals(forceMajeureRequestForm.getNewTabRemarks()) || forceMajeureRequestForm.getNewTabRemarks().length()==0){
			ErrorVO error = new ErrorVO("mailtracking.defaults.forcemajeure.remarksmandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
            invocationContext.target = FORCE_MAJEURE_FAILED;
            return;
		}
		else{
		forceMajeureRequestFilterVO.setReqRemarks(forceMajeureRequestForm.getNewTabRemarks());
		}*/
		try {
			
		Collection<LockVO> savelocks = prepareLocksForSave(forceMajeureRequestFilterVO);
			
		delegate.saveForceMajeureRequest(savelocks,false,forceMajeureRequestFilterVO );
		
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
		}
		  ErrorVO error = null;
	      Collection<ErrorVO> saveerrors = new ArrayList<ErrorVO>();
	      error = new ErrorVO("mailtracking.defaults.forcemajeure.generateinvoiceconfirmation");
	      error.setErrorDisplayType(ErrorDisplayType.INFO);
	      saveerrors.add(error);
	      invocationContext.addAllError(saveerrors);
		}
		log.exiting("ListCommand", "execute");

	}
		
	private Collection<LockVO> prepareLocksForSave( ForceMajeureRequestFilterVO forceMajeureRequestFilterVO)
	  {
	    LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

	    Collection <LockVO>locks = new ArrayList<LockVO>();
	    ForceMajeureRequestLockVO lock = new ForceMajeureRequestLockVO();
	    lock.setForceLockEntity(ACTION_REQFORCEMAJ);
	    lock.setAction(ACTION_REQFORCEMAJ);
	    lock.setClientType(ClientType.WEB);
	    lock.setCompanyCode(logonAttributes.getCompanyCode());
	    lock.setDescription("FMR " + forceMajeureRequestFilterVO.getFilterParameters());
	    lock.setRemarks("REMARKS" + forceMajeureRequestFilterVO.getReqRemarks());
	    lock.setScreenId("mail.operations.ux.forcemajeure");
	    lock.setStationCode(logonAttributes.getStationCode());

	    locks.add(lock);

	    return locks;
	  }

	
}