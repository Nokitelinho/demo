/*
 * ViewCN51DetailsCommand.java Created on Mar 14,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listCN51;



import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListCN51ScreenSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListCN51ScreenForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2049
 *
 */
public class ViewCN51DetailsCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MailTracking:Mra:Defaults");
	
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listCN51s";
	
	private static final String VIEW_CN51_SUCCESS = "view_success";
	
	private static final String VIEW_CN51_FAILURE = "view_failure";
	
	private static final String MODULE_NAME_CRA_ARLBLG = "mailtracking.mra.airlinebilling";

	private static final String SCREEN_ID_CAPTURE_CN51 = "mailtracking.mra.airlinebilling.defaults.capturecn51";
	

	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invContext)
			throws CommandInvocationException {
		log.entering("ViewCN51DetailsCommand","execute");
		ListCN51ScreenForm cn51ScreenForm 
								= (ListCN51ScreenForm)invContext.screenModel;
		ListCN51ScreenSession cn51ScreenSession 
								= (ListCN51ScreenSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		// setting the filterVO into the session for displaying the details back
		updateFilterVOInSession(cn51ScreenSession,cn51ScreenForm);
		
		CaptureCN51Session captureCN51Session 
								= (CaptureCN51Session)getScreenSession(MODULE_NAME_CRA_ARLBLG,
																	   SCREEN_ID_CAPTURE_CN51);
		
		Page<AirlineCN51SummaryVO> listedVOs = null;		
		listedVOs =(Page<AirlineCN51SummaryVO>)cn51ScreenSession.getAirlineCN51SummaryVOs();
							
		String[] selectedRowIds = cn51ScreenForm.getTableRowId();

		if(selectedRowIds != null && selectedRowIds.length > 0 ){
			log.log(Log.INFO, "#### No of Rows Selected ",
					selectedRowIds.length);
			log.log(Log.INFO, "#### selected Row Index ", selectedRowIds);					
		}else{
			log.log(Log.INFO,"#### Now Rows Selected ");
			invContext.target = VIEW_CN51_FAILURE;
			log.exiting("ViewCN51DetailsCommand","execute");
			return;
		}
				
		AirlineCN51SummaryVO selectedVO 
						= listedVOs.get( Integer.parseInt(selectedRowIds[0]) );	
		captureCN51Session.setFilterDetails(constructAirlineCN51FilterVO(selectedVO) );
		// setting the current screenId as the parentId for the invoked screen
		captureCN51Session.setParentId(SCREEN_ID);
		
		invContext.target = VIEW_CN51_SUCCESS;
		log.exiting("ViewCN51DetailsCommand","execute");
	}
	
	/**
	 * 
	 * @param cn51FilterVO
	 * @param selectedVO
	 */
	private AirlineCN51FilterVO constructAirlineCN51FilterVO
							(AirlineCN51SummaryVO selectedVO ) {
		log.entering("ViewCN51DetailsCommand","populateAirlineCN51FilterVO");
			AirlineCN51FilterVO cn51FilterVO = new AirlineCN51FilterVO();
			cn51FilterVO.setCompanyCode(selectedVO.getCompanycode());
			cn51FilterVO.setAirlineCode(selectedVO.getAirlinecode());
			cn51FilterVO.setAirlineIdentifier(selectedVO.getAirlineidr());
			cn51FilterVO.setInterlineBillingType(selectedVO.getInterlinebillingtype());
			cn51FilterVO.setInvoiceReferenceNumber(selectedVO.getInvoicenumber());
			cn51FilterVO.setIataClearancePeriod(selectedVO.getClearanceperiod());	
			log.log(Log.INFO, "###### filter VO to viewCN51 Screen ",
					cn51FilterVO);
		log.exiting("ViewCN51DetailsCommand","populateAirlineCN51FilterVO");
		return cn51FilterVO;
	}
	
	private void updateFilterVOInSession(ListCN51ScreenSession cn51ScreenSession,
										 ListCN51ScreenForm cn51ScreenForm ) {
		AirlineCN51FilterVO formFilterVO = new AirlineCN51FilterVO();
		if(cn51ScreenForm.getAirlineCode() != null && 
				cn51ScreenForm.getAirlineCode().length() > 0 ){
			formFilterVO.setAirlineCode(cn51ScreenForm.getAirlineCode().toUpperCase().trim());
		}		
		formFilterVO.setBilledDateFrom
					(new LocalDate(LocalDate.NO_STATION,Location.NONE,false)
							.setDate(cn51ScreenForm.getBlgFromDateStr()));
		formFilterVO.setBilledDateTo
					(new LocalDate(LocalDate.NO_STATION,Location.NONE,false)
							.setDate(cn51ScreenForm.getBlgToDateStr()));
		LogonAttributes logonAttributes = this.getApplicationSession().getLogonVO();
		formFilterVO.setCompanyCode(logonAttributes.getCompanyCode());		
		formFilterVO.setInterlineBillingType(cn51ScreenForm.getInterlineBlgType());		
		
		cn51ScreenSession.setAirlineCN51FilterVO(formFilterVO);
		
	}

}
