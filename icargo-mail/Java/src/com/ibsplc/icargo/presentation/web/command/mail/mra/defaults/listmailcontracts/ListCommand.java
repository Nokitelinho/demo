/*
 * ListCommand.java created on APR 2, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listmailcontracts;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListMailContractsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailContractsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author A-1946
 *
 */
public class ListCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID =
		"mailtracking.mra.defaults.listmailcontracts";

	private static final String SCREEN_SUCCESS = "list_success";
	
	private static final String SCREEN_FAILURE = "list_failure";
	
	private static final String NO_RESULTS_FOUND="mailtracking.mra.defaults.listmailcontracts.noresults";
	
	
	private static final String BLANK = "";
	
	private static final String LATEST_VERSION = "LATEST";
	
	
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		ListMailContractsSession  session=	(ListMailContractsSession)getScreenSession(
				MODULE_NAME, SCREENID);
		Collection<MailContractVO> mailContractVOs =null;
		ListMailContractsForm form=(ListMailContractsForm)invocationContext.screenModel;
		/**
		 * Getting log on attribute here
		 */
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		
		MailContractFilterVO mailContractFilterVO=new MailContractFilterVO();
		/**
		 * Populating filter vo here
		 */
		if(session.getMailContractFilterVO()!=null && 
				"MailContractsList".equals(form.getMailContractsCloseFlag())){
			mailContractFilterVO=session.getMailContractFilterVO();
			setFilterVlauesFromSession(form,mailContractFilterVO);
		}
		else{			
			populateMailContractFilterVO(form,mailContractFilterVO);
		}
		mailContractFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		/**
		 * set filter VO to session
		 */
		session.setMailContractFilterVO(mailContractFilterVO);
		
		log.log(Log.FINE, "The mailContractFilterVO is-->",
				mailContractFilterVO);
		MailTrackingMRADelegate mailTrackingMRADelegate=new MailTrackingMRADelegate();
		try{
			mailContractVOs=mailTrackingMRADelegate.findMailContracts(mailContractFilterVO);
		}
		catch(BusinessDelegateException businessDelegateException){
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		if(mailContractVOs==null || mailContractVOs.size()==0){
			Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
			ErrorVO error=new ErrorVO(NO_RESULTS_FOUND);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target=SCREEN_FAILURE;
		}
		if(mailContractFilterVO.getVersionNumber()!=null){
			if(LATEST_VERSION.equals(mailContractFilterVO.getVersionNumber())){
				for(MailContractVO mailContractVO :mailContractVOs){
					mailContractVO.setVersionNumber(LATEST_VERSION);
				}
			}
		}
		for(MailContractVO mailContractVO :mailContractVOs){
			log.log(Log.FINE, "mailContractVO-->", mailContractVO);
		}
		session.setMailContractVOs(mailContractVOs);
		
		
		invocationContext.target=SCREEN_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
	/**
	 * 
	 * @param form
	 * @param mailContractFilterVO
	 */
	public void populateMailContractFilterVO(ListMailContractsForm form,
			MailContractFilterVO mailContractFilterVO){
		mailContractFilterVO.setAgreementStatus(form.getAgreementStatus());
		mailContractFilterVO.setAgreementType(form.getAgreementType());
		mailContractFilterVO.setAirlineCode(form.getAirlineCode());
		mailContractFilterVO.setVersionNumber(form.getVersionNumberFilter());
		if(!BLANK.equals(form.getContractDate())){
			mailContractFilterVO.setContractDate(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(form.getContractDate()));
		}		
		mailContractFilterVO.setContractReferenceNumber(form.getContractRefNo());
		if(!BLANK.equals(form.getFromDate())){
			mailContractFilterVO.setContractValidityFrom(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(form.getFromDate()));			
		}	
		mailContractFilterVO.setPaCode(form.getPaCode());
		if(!BLANK.equals(form.getToDate())){
			mailContractFilterVO.setContractValidityTo((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(form.getToDate())));		
		}		
	}
	/**
	 * 
	 * @param form
	 * @param mailContractFilterVO
	 * @return
	 */
	private ListMailContractsForm setFilterVlauesFromSession(ListMailContractsForm form,
			MailContractFilterVO mailContractFilterVO){
		form.setAgreementStatus(mailContractFilterVO.getAgreementStatus());
		form.setAgreementType(mailContractFilterVO.getAgreementType());
		form.setAirlineCode(mailContractFilterVO.getAirlineCode());
		form.setVersionNumberFilter(mailContractFilterVO.getVersionNumber());
		if(mailContractFilterVO.getContractDate()!=null){
			String creationDate=TimeConvertor.toStringFormat(mailContractFilterVO.getContractDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
			form.setContractDate(creationDate);
		}
		form.setContractRefNo(mailContractFilterVO.getContractReferenceNumber());
		if(mailContractFilterVO.getContractValidityFrom()!=null){
			String fromdate=TimeConvertor.toStringFormat(mailContractFilterVO.getContractValidityFrom().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
			form.setFromDate(fromdate);
		}		
		form.setPaCode(mailContractFilterVO.getPaCode());
		if(mailContractFilterVO.getContractValidityTo()!=null){
			String toDate=TimeConvertor.toStringFormat(mailContractFilterVO.getContractValidityTo().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
			form.setToDate(toDate);
		}
		
		return form;
	}
}
