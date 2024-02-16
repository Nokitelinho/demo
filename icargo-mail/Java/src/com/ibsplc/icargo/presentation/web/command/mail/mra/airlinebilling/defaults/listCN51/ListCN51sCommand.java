/*
 * ListCN51sCommand.java Created on Mar 16,2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listCN51;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListCN51ScreenSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListCN51ScreenForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2049
 *
 */
public class ListCN51sCommand extends BaseCommand {

private Log log = LogFactory.getLogger("MailTracking:Mra:Defaults");
	
	private static final String LISTCN51_SUCESS = "listCN51_success";
	
	private static final String LISTCN51_FAILED = "listCN51_failed";
			
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listCN51s";
	
	private static final String ERROR_NO_RESULTS = 
				"mailtracking.mra.defaults.listCN51s.msg.err.noResultsFound";
	private static final String FROMDATE_MANDATORY="mailtracking.mra.defaults.listCN51s.msg.err.fromdatemandatory";
	private static final String TODATE_MANDATORY="mailtracking.mra.defaults.listCN51s.msg.err.todatemandatory";
	private static final String FROMDATE_GREATER="mailtracking.mra.defaults.listCN51s.msg.err.fromdategreater";
	private static final String SCREENLOAD="screenload";	
	private static final String LIST="list";
	private static final String SYS_PARA_ACC_ENTRY="cra.accounting.isaccountingenabled";
	/**
	 * 
	 */
	public void execute(InvocationContext invContext)
							throws CommandInvocationException {
		log.entering("ListCN51sCommand","execute");
		ListCN51ScreenForm listCN51ScreenForm 
							= (ListCN51ScreenForm)invContext.screenModel;
		ListCN51ScreenSession listCN51ScreenSession
							= (ListCN51ScreenSession)this.getScreenSession(MODULE_NAME,SCREEN_ID);
		AirlineCN51FilterVO filterVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error=null;
		/*= listCN51ScreenSession.getAirlineCN51FilterVO() != null
										? listCN51ScreenSession.getAirlineCN51FilterVO()
										: constructFilterVO(listCN51ScreenForm);*/
		
		// code for populating filter Fields into the form
		if( "Y".equals(listCN51ScreenForm.getCloseFrmCN51Flag()) || 
				"Y".equals(listCN51ScreenForm.getCloseFrmCN66Flag()) ||
				"Y".equals(listCN51ScreenForm.getCloseAccEntryFlag())) {
			filterVO = listCN51ScreenSession.getAirlineCN51FilterVO();
			log.log(Log.INFO, " ##### the filterVO taken from session is ",
					filterVO);
			populateToForm(listCN51ScreenForm,listCN51ScreenSession);
			listCN51ScreenForm.setCloseFrmCN51Flag("N");
			listCN51ScreenForm.setCloseFrmCN66Flag("N");
			listCN51ScreenForm.setCloseAccEntryFlag("N");
//			 code for acc entry sys para starts
			Collection<String> systemParameterCodes = new ArrayList<String>();

			systemParameterCodes.add(SYS_PARA_ACC_ENTRY);

			Map<String, String> systemParameters = null;

			try {

				systemParameters = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);

			} catch (BusinessDelegateException e) {
//printStackTrrace()();
				invContext.addAllError(handleDelegateException(e));
			}

			String accountingEnabled = (systemParameters.get(SYS_PARA_ACC_ENTRY));
			log.log(Log.INFO, "IS acc enabled--->", accountingEnabled);
			if("N".equals(accountingEnabled)){
				listCN51ScreenForm.setAccEntryFlag("N");
			}else{
				listCN51ScreenForm.setAccEntryFlag("Y");
			}
//			code for acc entry sys para ends
		}else{			
			filterVO =  constructFilterVO(listCN51ScreenForm);
			if(filterVO.getBilledDateFrom()==null){
				error=new ErrorVO(FROMDATE_MANDATORY);
				errors.add(error);
			}
			if(filterVO.getBilledDateTo()==null){
				error=new ErrorVO(TODATE_MANDATORY);
				errors.add(error);
			}
			if(filterVO.getBilledDateFrom()!=null && filterVO.getBilledDateTo()!=null){
				if (!filterVO.getBilledDateFrom().equals(filterVO.getBilledDateTo())) {
					if(!(filterVO.getBilledDateFrom().before(filterVO.getBilledDateTo()))){
						error=new ErrorVO(FROMDATE_GREATER);
						errors.add(error);
					}
				}
			}
			if(errors != null && errors.size() > 0){
				//listCN51ScreenForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				listCN51ScreenForm.setScreenStatus(SCREENLOAD);
				invContext.addAllError(errors);
				invContext.target=LISTCN51_FAILED;
				
				return;
			}
			listCN51ScreenSession.setAirlineCN51FilterVO(filterVO);
			
			log.log(Log.INFO, " ######### filterVO taken from Form is ",
					filterVO);
		}
		
		// list call for fetching the summaryVOs
		
		Page<AirlineCN51SummaryVO> listedDetails = null;
		
				
				
		filterVO.setPageNumber(Integer.parseInt(listCN51ScreenForm.getDisplayPage()));
		try {
			listedDetails = new MailTrackingMRADelegate().findCN51s(filterVO);
		} catch (BusinessDelegateException businessException) {
			log.log(Log.SEVERE," ####### BusinessDelegateException ######## ");
			addAllErrorDetails(invContext , handleDelegateException(businessException));
			invContext.target = LISTCN51_FAILED;
			log.exiting("ListCN51sCommand","execute");
			return;
		}
		
		log.log(Log.INFO, " #### the summaryVOs from listServer call ",
				listedDetails);
		if(listedDetails == null || listedDetails.size() <= 0 ){
			log.log(Log.INFO," #### No Records Found ##### ");
			listCN51ScreenSession.setAirlineCN51SummaryVOs(null);
			//listCN51ScreenForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD); 
			listCN51ScreenForm.setScreenStatus(SCREENLOAD);
			error = new ErrorVO(ERROR_NO_RESULTS);
			error.setErrorDisplayType(ERROR);
			invContext.addError(error);
			listCN51ScreenSession.setTotalRecords(0);
			invContext.target = LISTCN51_FAILED;
			log.exiting("ListCN51sCommand","execute");
			return;
		}
		//Added by A-7929 as part Of ICRD-265471
		if(listedDetails != null && listedDetails.size() > 0 ){
		for(AirlineCN51SummaryVO airlineCN51SummaryVO : listedDetails ){
		
			if("N".equals(airlineCN51SummaryVO.getInvStatus()))
				airlineCN51SummaryVO.setInvStatus("New");
			else if("F".equals(airlineCN51SummaryVO.getInvStatus()))
			   airlineCN51SummaryVO.setInvStatus("Finalized");
			else if("P".equals(airlineCN51SummaryVO.getInvStatus()))
				   airlineCN51SummaryVO.setInvStatus("Processed");
			else if("W".equals(airlineCN51SummaryVO.getInvStatus()))
				   airlineCN51SummaryVO.setInvStatus("Withdrawn");
			
			if("P".equals(airlineCN51SummaryVO.getBillingType()))
				airlineCN51SummaryVO.setBillingType("Prime Billing");
			else if("R".equals(airlineCN51SummaryVO.getInvStatus()))
				   airlineCN51SummaryVO.setInvStatus("Rejection Memo");
			
		}
		}
		// records found for listing
		listCN51ScreenSession.setAirlineCN51SummaryVOs(listedDetails);
		listCN51ScreenForm.setScreenStatus(LIST);
		invContext.target = LISTCN51_SUCESS;
		log.exiting("ListCN51sCommand","execute");		
	}
	
	/**
	 * method that constructs the filterVO using the Form Class
	 * @param listCN51ScreenForm
	 * @return
	 */
	private AirlineCN51FilterVO constructFilterVO(ListCN51ScreenForm listCN51ScreenForm ) {
		log.entering("ListCN51sCommand","constructFilterVO");
		
		AirlineCN51FilterVO formFilterVO = new AirlineCN51FilterVO();
		if(listCN51ScreenForm.getAirlineCode() != null && 
					listCN51ScreenForm.getAirlineCode().length() > 0 ){
			formFilterVO.setAirlineCode(listCN51ScreenForm.getAirlineCode().toUpperCase().trim());
		}		
		if(listCN51ScreenForm.getBlgFromDateStr()!=null && listCN51ScreenForm.getBlgFromDateStr().trim().length()>0){
		formFilterVO.setBilledDateFrom
					(new LocalDate(LocalDate.NO_STATION,Location.NONE,false)
							.setDate(listCN51ScreenForm.getBlgFromDateStr()));
		}
		if(listCN51ScreenForm.getBlgToDateStr()!=null && listCN51ScreenForm.getBlgToDateStr().trim().length()>0){
		formFilterVO.setBilledDateTo
					(new LocalDate(LocalDate.NO_STATION,Location.NONE,false)
							.setDate(listCN51ScreenForm.getBlgToDateStr()));
		}
		LogonAttributes logonAttributes = this.getApplicationSession().getLogonVO();
		formFilterVO.setCompanyCode(logonAttributes.getCompanyCode());		
		formFilterVO.setInterlineBillingType(listCN51ScreenForm.getInterlineBlgType());		
		//added as part of ICRD-265471
		formFilterVO.setInvoiceNo(listCN51ScreenForm.getInvoiceNo());
		formFilterVO.setBillingType(listCN51ScreenForm.getBillingType());
		return formFilterVO;
	}
	
	/**
	 * 
	 * @param invContext
	 * @param errors
	 */
	private void addAllErrorDetails(InvocationContext invContext,Collection<ErrorVO> errors) {
		if(errors != null && errors.size() > 0 ) {
			invContext.addAllError(errors);
		}
	}
	
	/**
	 * method that populates the filterFields inside form class
	 * from the corresponding filterFields inside the FilterVO
	 * storred inside session
	 * called when returning from the ViewCn51Details and ViewCn66Details 
	 * screen.....  
	 * @param listCN51ScreenForm
	 * @param listCN51ScreenSession
	 */
	private void populateToForm(ListCN51ScreenForm listCN51ScreenForm ,
							    ListCN51ScreenSession listCN51ScreenSession) {
		log.entering("ListCN51sCommand","populateToForm");
			listCN51ScreenForm.setAirlineCode
					(listCN51ScreenSession.getAirlineCN51FilterVO()
							  							.getAirlineCode());
			listCN51ScreenForm.setBlgFromDateStr
					(listCN51ScreenSession.getAirlineCN51FilterVO()
							  			  .getBilledDateFrom()
							  			  .toDisplayDateOnlyFormat());
			listCN51ScreenForm.setBlgToDateStr
					(listCN51ScreenSession.getAirlineCN51FilterVO()
								          .getBilledDateTo()
								          .toDisplayDateOnlyFormat());
			listCN51ScreenForm.setInterlineBlgType
					(listCN51ScreenSession.getAirlineCN51FilterVO()
										  .getInterlineBillingType());
		log.exiting("ListCN51sCommand","populateToForm");
	}
	
	

}
