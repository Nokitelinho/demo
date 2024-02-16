/*
 * ListCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailonhandlist;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailOnHandDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MainOnHandListSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailOnHandListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;

public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailonhandlist";
	private static final String ASSSIGNED_ALL = "ALL";
	private static final String FROM_SCREEN = "LISTCONTAINER";

	@Override
	public void execute(InvocationContext invocationcontext)
			throws CommandInvocationException {

		log.entering("ListCommand ", "execute");
		MailOnHandListForm mailHandlistform = (MailOnHandListForm) invocationcontext.screenModel;
		MainOnHandListSession mailonlistsession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Page<MailOnHandDetailsVO> MailonhandlistVOS = null;
		SearchContainerFilterVO searchContainerFilterVO = new SearchContainerFilterVO();
		if (FROM_SCREEN.equals(mailHandlistform.getFromScreen()) 
				&& mailonlistsession.getSearchContainerFilterVO() != null) {
			searchContainerFilterVO = mailonlistsession
					.getSearchContainerFilterVO();
			mailHandlistform.setFromScreen("");
			mailHandlistform.setDisplayPage("1");
			mailHandlistform.setLastPageNum("0");
		}
		else
		{
			errors = updateFilterVO(mailHandlistform,searchContainerFilterVO,logonAttributes);	
		}
		
		if (errors != null && errors.size() > 0) {
			invocationcontext.addAllError(errors);
			invocationcontext.target = LIST_FAILURE; 
		   mailonlistsession.setMailOnHandDetailsVO(null);
		 
			
		}else{ 
			String displayPage = mailHandlistform.getDisplayPage();
			int pageNumber = Integer.parseInt(displayPage);
			try {
				MailonhandlistVOS = new MailTrackingDefaultsDelegate()
						.findMailOnHandDetails(searchContainerFilterVO,
								pageNumber);
				
			} catch (BusinessDelegateException businessDelegateException) {
				
				errors = handleDelegateException(businessDelegateException);

			}
			if(MailonhandlistVOS != null && MailonhandlistVOS.size() >0){
				mailonlistsession.setMailOnHandDetailsVO(MailonhandlistVOS);
				mailHandlistform.setAirport(searchContainerFilterVO.getDeparturePort());
				mailonlistsession
						.setSearchContainerFilterVO(searchContainerFilterVO);
				mailHandlistform.setFromScreen(null);
				invocationcontext.target = LIST_SUCCESS;
		  }else{
				 errors.add(new ErrorVO(
				  "mailtracking.defaults.mailhandlist.err.norecords"));
				  invocationcontext.addAllError(errors);
				  invocationcontext.target = LIST_FAILURE; 
				  mailonlistsession.setMailOnHandDetailsVO(null);
				  return;
				  } 
				 
		}
		
		

	}

	
	
	private Collection<ErrorVO> updateFilterVO(
			MailOnHandListForm mailHandlistform,
			SearchContainerFilterVO searchContainerFilterVO,
			LogonAttributes logonAttributes){
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String companyCode = logonAttributes.getCompanyCode();
		searchContainerFilterVO.setCompanyCode(companyCode);
		LocalDate currentDate = new LocalDate(logonAttributes.getAirportCode(),
				ARP, false);
		String fromDate = mailHandlistform.getFromDate();
		if (fromDate != null) {
			searchContainerFilterVO.setStrFromDate(fromDate);
		} else {
			fromDate = searchContainerFilterVO.getStrFromDate();
		}
		String toDate = mailHandlistform.getToDate();
		if (toDate != null) {
			searchContainerFilterVO.setStrToDate(toDate);
		} else {
			toDate = searchContainerFilterVO.getStrToDate();
		}
		if (fromDate.trim().length() > 1 && toDate.trim().length() < 1) {
			errors.add(new ErrorVO(
					"mailtracking.defaults.mailhandlist.err.entertodate"));
			

		} else if (toDate.trim().length() > 1 && fromDate.trim().length() < 1) {
			errors.add(new ErrorVO(
					"mailtracking.defaults.mailhandlist.err.enterfromdate"));
			

		} else if((fromDate.trim().length() > 1)
					&& (toDate.trim().length() > 1)){
			
				if ((!DateUtilities.isLessThan(fromDate, toDate, "dd-MMM-yyyy"))&&(!fromDate.equals(toDate))) {
					errors.add(new ErrorVO(
							"mailtracking.defaults.mailhandlist.err.fromdategreatertodate"));
					
			
				} 		
				else if (toDate.trim().length() > 1
						&& DateUtilities.isGreaterThan(toDate,
								currentDate.toDisplayDateOnlyFormat(),
								"dd-MMM-yyyy")) {
					errors.add(new ErrorVO(
							"mailtracking.defaults.mailhandlist.err.todateandtodaydate"));
					
				}			
		}
		String searchMode = mailHandlistform.getAssignedto();
		if (searchMode != null && searchMode.length() > 1
				&& !ASSSIGNED_ALL.equalsIgnoreCase(searchMode)) {
			searchContainerFilterVO.setSearchMode(searchMode);
		} else {
			if (searchContainerFilterVO.getSearchMode() == null) {
				searchContainerFilterVO.setSearchMode(ASSSIGNED_ALL);
			} else if (ASSSIGNED_ALL.equalsIgnoreCase(searchContainerFilterVO
					.getSearchMode())
					|| searchContainerFilterVO.getSearchMode().length() < 1) {
				searchContainerFilterVO.setSearchMode(ASSSIGNED_ALL);
			}

		}
		String airPort = "";
		if (mailHandlistform.getAirport() != null) {
			airPort = mailHandlistform.getAirport();
		} else {
			if (searchContainerFilterVO.getDeparturePort() != null) {
				airPort = searchContainerFilterVO.getDeparturePort();
			}
		}

		// checking port
		if (airPort != null && airPort.trim().length() > 1) {
				searchContainerFilterVO.setDeparturePort(airPort.toUpperCase());
				AirportValidationVO airportValidationVO = null;

				try {
					airportValidationVO = new AreaDelegate()
							.validateAirportCode(
									logonAttributes.getCompanyCode(),
									airPort.toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				/*if (airportValidationVO == null) {
					errors.add(new ErrorVO(
							"mailtracking.defaults.mailhandlist.err.invalidairport",
							new Object[] { airPort.toUpperCase() }));
				
					  } */
					 

				

		} else {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.mailhandlist.err.airportmandatory");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		
	}
		return errors;
	}
	

}
