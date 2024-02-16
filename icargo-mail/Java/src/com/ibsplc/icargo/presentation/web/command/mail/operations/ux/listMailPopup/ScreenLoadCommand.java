   /*
 * ScreenLoadCommand.java Created on Oct 22, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.listMailPopup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.addons.trucking.vo.TruckOrderFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.ListMailPopupSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ListMailbagPopupForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import static com.ibsplc.icargo.framework.util.time.Location.ARP;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.listMailPopup.ScreenLoadCommand..java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7929	:	22-Oct-2018	:	Draft
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("mail.operations.ux.listMailPopup");

	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.listmailbagpopup";
	private static final String NO = "N";
	private static final String YES = "Y";
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	private static final String NO_RECORDS = "mail.operations.ux.listmailbagpopup.norecords";
	private static final String LYING = "L";
	private static final String CARDIT = "C";
	 private static final String ONETIME_MAILSTATUS = "mailtracking.defaults.mailstatus";

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		this.log.entering("ScreenLoadCommand", "<---- Entering listMailPopup.ScreenLoadCommand---->");

		ListMailbagPopupForm listMailbagPopupForm = (ListMailbagPopupForm) invocationContext.screenModel;
		ListMailPopupSession listMailPopupSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = null;
		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		Page<MailbagVO> carditlistMailBags = null;
		Page<MailbagVO> listMailBags = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> error = null;

		
		listMailPopupSession.setOneTimeVOs(getOneTimeValues());
		String displayPage = listMailbagPopupForm.getDisplayPage();
		String displayPageforLyingList = listMailbagPopupForm.getDisplayPageForLyingList();
		int displayPageCount = Integer.parseInt(displayPage);
		int displayPageCountForLyingList = Integer.parseInt(displayPageforLyingList);
		String defaultSize = listMailbagPopupForm.getDefaultPageSize();
		int defaultPageSize = Integer.parseInt(defaultSize);

		// FOR CARDIT
		mailbagEnquiryFilterVO = populateFilterForCardit(listMailPopupSession.getTruckOrderFilterDetails(),
				displayPageCount, defaultPageSize,listMailbagPopupForm);
		listMailbagPopupForm.setPageNumber(mailbagEnquiryFilterVO.getPageNumber());
		try {
			carditlistMailBags = delegate.findMailbagsForTruckFlight(mailbagEnquiryFilterVO,
					mailbagEnquiryFilterVO.getPageNumber()); 
		} catch (BusinessDelegateException e) {
			error = handleDelegateException(e);
			e.getMessage();
		}
	
		if (carditlistMailBags == null || carditlistMailBags.size() == 0) {
			listMailPopupSession.setCarditMailbagVOsCollection(null);
			ErrorVO errorVO = new ErrorVO(NO_RECORDS);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
	    	invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		} else{
			
			listMailPopupSession.setCarditMailbagVOsCollection(carditlistMailBags);
		}
		// FOR LYING LIST
		mailbagEnquiryFilterVO = populateFilterForLyingList(listMailPopupSession.getTruckOrderFilterDetails(),
				displayPageCountForLyingList, defaultPageSize,listMailbagPopupForm);
		listMailbagPopupForm.setPageNumber(mailbagEnquiryFilterVO.getPageNumber());
		try {
			listMailBags = delegate.findMailbagsForTruckFlight(mailbagEnquiryFilterVO,
					mailbagEnquiryFilterVO.getPageNumber());
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
			e.getMessage();
		}
		if (carditlistMailBags==null && (listMailBags == null || listMailBags.size() == 0)) {
			listMailPopupSession.setLyingMailbagVOs(null);
			ErrorVO errorVO = new ErrorVO(NO_RECORDS);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
            return;
		} else{
			listMailPopupSession.setLyingMailbagVOs(listMailBags);
		}
		invocationContext.target = SCREENLOAD_SUCCESS;
		this.log.exiting("ScreenLoadCommand", "<---- Exiting listMailPopup.ScreenLoadCommand---->");
	}

	/**
	 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.listMailPopup.ScreenLoadCommand.populateFilterForCardit.java
	 *	Version		:	Name	:	Date			:	Updation
	 * ---------------------------------------------------
	 *		0.1		:	A-7929	:	22-Oct-2018	:	Draft
	 * @param listMailbagPopupForm 
	 */
	private MailbagEnquiryFilterVO populateFilterForCardit(TruckOrderFilterVO truckOrderFilterVO, int displayPageCountForLyingList,
			int defaultPageSize, ListMailbagPopupForm listMailbagPopupForm) {
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		mailbagEnquiryFilterVO.setPageNumber(displayPageCountForLyingList);
		mailbagEnquiryFilterVO.setDefaultPageSize(defaultPageSize);
		mailbagEnquiryFilterVO.setTotalRecords(-1);
		mailbagEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailbagEnquiryFilterVO.setLyingList(NO);
		mailbagEnquiryFilterVO.setCarrierCode(truckOrderFilterVO.getFlightCarrierCode());
		mailbagEnquiryFilterVO.setOrigin(truckOrderFilterVO.getAirportCode()); // pol
		
		//populating filter query
		if(CARDIT.equals(listMailbagPopupForm.getFilterType())){
			
			mailbagEnquiryFilterVO.setFilterType(CARDIT);
			if(listMailbagPopupForm.getFlightNumber()!=null){
			mailbagEnquiryFilterVO.setFlightNumber(listMailbagPopupForm.getFlightNumber());
			}
			if(listMailbagPopupForm.getFlightDate()!=null){
			 LocalDate flightDate = new LocalDate("***", Location.NONE, false);
	            if(mailbagEnquiryFilterVO.getFromDate()!=null){
	            	flightDate.setDate(listMailbagPopupForm.getFlightDate().toUpperCase());
	            	mailbagEnquiryFilterVO.setFlightDate(flightDate);
	            }
			}
			if((listMailbagPopupForm.getMailbagId()!=null) && (listMailbagPopupForm.getMailbagId().trim().length()>0)){
				mailbagEnquiryFilterVO.setMailbagId(listMailbagPopupForm.getMailbagId().toUpperCase());
			}
			
			if((listMailbagPopupForm.getOoe()!=null) && (listMailbagPopupForm.getOoe().trim().length()>0)){
				mailbagEnquiryFilterVO.setOoe(listMailbagPopupForm.getOoe().toUpperCase());
			}
			
			if((listMailbagPopupForm.getDoe()!=null) && (listMailbagPopupForm.getDoe().trim().length()>0)){
				mailbagEnquiryFilterVO.setDoe(listMailbagPopupForm.getDoe().toUpperCase());
			}
			
			if((listMailbagPopupForm.getMailCategoryCode()!=null) && (listMailbagPopupForm.getMailCategoryCode().trim().length()>0)){
				mailbagEnquiryFilterVO.setMailCategoryCode(listMailbagPopupForm.getMailCategoryCode().toUpperCase());
			}
			
			if((listMailbagPopupForm.getMailSubclass()!=null) && (listMailbagPopupForm.getMailSubclass().trim().length()>0)){
				mailbagEnquiryFilterVO.setMailSubclass(listMailbagPopupForm.getMailSubclass().toUpperCase());
			}
			
			if((listMailbagPopupForm.getYear()!=null) && (listMailbagPopupForm.getYear().trim().length()>0)){
				mailbagEnquiryFilterVO.setYear(listMailbagPopupForm.getYear().toUpperCase());
			}
			
			if((listMailbagPopupForm.getDespatchSerialNumber()!=null) && (listMailbagPopupForm.getDespatchSerialNumber().trim().length()>0)){
				mailbagEnquiryFilterVO.setDespatchSerialNumber(listMailbagPopupForm.getDespatchSerialNumber().toUpperCase());
			}
			
			if((listMailbagPopupForm.getReceptacleSerialNumber()!=null) && (listMailbagPopupForm.getReceptacleSerialNumber().trim().length()>0)){
				mailbagEnquiryFilterVO.setReceptacleSerialNumber(listMailbagPopupForm.getReceptacleSerialNumber().toUpperCase());
			}
			
			if((listMailbagPopupForm.getConsignmentNumber()!=null) && (listMailbagPopupForm.getConsignmentNumber().trim().length()>0)){
				mailbagEnquiryFilterVO.setConsigmentNumber(listMailbagPopupForm.getConsignmentNumber().toUpperCase());
			}
			
			if((listMailbagPopupForm.getFromDate()!=null) && (listMailbagPopupForm.getFromDate().trim().length()>0)){
				mailbagEnquiryFilterVO.setFromDate(listMailbagPopupForm.getFromDate().toUpperCase());
			}
			
			if((listMailbagPopupForm.getToDate()!=null) && (listMailbagPopupForm.getToDate().trim().length()>0)){
				mailbagEnquiryFilterVO.setToDate(listMailbagPopupForm.getToDate().toUpperCase());
			}
			
			if((listMailbagPopupForm.getPaCode()!=null) && (listMailbagPopupForm.getPaCode().trim().length()>0)){
				mailbagEnquiryFilterVO.setPacode(listMailbagPopupForm.getPaCode().toUpperCase());
			}
			
			if((listMailbagPopupForm.getFlightNumber()!=null) && (listMailbagPopupForm.getFlightNumber().trim().length()>0)){
				mailbagEnquiryFilterVO.setFlightNumber(listMailbagPopupForm.getFlightNumber().toUpperCase());
			}
			
			if((listMailbagPopupForm.getFlightDate()!=null) && (listMailbagPopupForm.getFlightDate().trim().length()>0)){
				mailbagEnquiryFilterVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(
						listMailbagPopupForm.getFlightDate().toUpperCase()));
			}
			
			if((listMailbagPopupForm.getUpliftAirport()!=null) && (listMailbagPopupForm.getUpliftAirport().trim().length()>0)){
				mailbagEnquiryFilterVO.setUpliftAirport(listMailbagPopupForm.getUpliftAirport().toUpperCase());
			}
			
			if((listMailbagPopupForm.getUldNumber()!=null) && (listMailbagPopupForm.getUldNumber().trim().length()>0)){
				mailbagEnquiryFilterVO.setUldNumber(listMailbagPopupForm.getUldNumber().toUpperCase());
			}
			
			if((listMailbagPopupForm.getOriginAirportCode()!=null) && (listMailbagPopupForm.getOriginAirportCode().trim().length()>0)){
				mailbagEnquiryFilterVO.setOriginAirportCode(listMailbagPopupForm.getOriginAirportCode().toUpperCase());
			}
			
			if((listMailbagPopupForm.getDestinationAirportCode()!=null) && (listMailbagPopupForm.getDestinationAirportCode().trim().length()>0)){
				mailbagEnquiryFilterVO.setDestinationAirportCode(listMailbagPopupForm.getDestinationAirportCode().toUpperCase());	
			}
			if((listMailbagPopupForm.getStatus()!=null) && (listMailbagPopupForm.getStatus().trim().length()>0)){
				mailbagEnquiryFilterVO.setCurrentStatus(listMailbagPopupForm.getStatus().toUpperCase());	
			}
		}

		return mailbagEnquiryFilterVO;

	}

	/**
	 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.listMailPopup.ScreenLoadCommand.populateFilterForLyingList.java
	 *	Version		:	Name	:	Date			:	Updation
	 * ---------------------------------------------------
	 *		0.1		:	A-7929	:	22-Oct-2018	:	Draft
	 * @param listMailbagPopupForm 
	 */
	private MailbagEnquiryFilterVO populateFilterForLyingList(TruckOrderFilterVO truckOrderFilterVO,
			int displayPageCount, int defaultPageSize, ListMailbagPopupForm listMailbagPopupForm) {
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		mailbagEnquiryFilterVO.setPageNumber(displayPageCount);
		mailbagEnquiryFilterVO.setDefaultPageSize(defaultPageSize);
		mailbagEnquiryFilterVO.setTotalRecords(-1);
		mailbagEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailbagEnquiryFilterVO.setLyingList(YES);
		mailbagEnquiryFilterVO.setCarrierCode(truckOrderFilterVO.getFlightCarrierCode()); 
		mailbagEnquiryFilterVO.setScanPort(truckOrderFilterVO.getAirportCode());
		mailbagEnquiryFilterVO.setScanFromDate(new LocalDate(logonAttributes.getAirportCode(), ARP, false).addDays(-7));
		mailbagEnquiryFilterVO.setScanToDate(new LocalDate(logonAttributes.getAirportCode(), ARP, false));

		//populating filter query
				if(LYING.equals(listMailbagPopupForm.getFilterType())){
					
					mailbagEnquiryFilterVO.setFilterType(LYING);
					
					if((listMailbagPopupForm.getMailbagId()!=null) && (listMailbagPopupForm.getMailbagId().trim().length()>0)){
						mailbagEnquiryFilterVO.setMailbagId(listMailbagPopupForm.getMailbagId().toUpperCase());
					}
					
					if((listMailbagPopupForm.getOoe()!=null) && (listMailbagPopupForm.getOoe().trim().length()>0)){
						mailbagEnquiryFilterVO.setOoe(listMailbagPopupForm.getOoe().toUpperCase());
					}
						
					if((listMailbagPopupForm.getDoe()!=null) && (listMailbagPopupForm.getDoe().trim().length()>0)){
						mailbagEnquiryFilterVO.setDoe(listMailbagPopupForm.getDoe().toUpperCase().toUpperCase());
					}
					
					if((listMailbagPopupForm.getMailCategoryCode()!=null) && (listMailbagPopupForm.getMailCategoryCode().trim().length()>0)){
						mailbagEnquiryFilterVO.setMailCategoryCode(listMailbagPopupForm.getMailCategoryCode().toUpperCase());
					}
					
					if((listMailbagPopupForm.getMailSubclass()!=null) && (listMailbagPopupForm.getMailSubclass().trim().length()>0)){
						mailbagEnquiryFilterVO.setMailSubclass(listMailbagPopupForm.getMailSubclass().toUpperCase());
					}
					
					if((listMailbagPopupForm.getYear()!=null) && (listMailbagPopupForm.getYear().trim().length()>0)){
						mailbagEnquiryFilterVO.setYear(listMailbagPopupForm.getYear().toUpperCase());
					}
					
					if((listMailbagPopupForm.getDespatchSerialNumber()!=null) && (listMailbagPopupForm.getDespatchSerialNumber().trim().length()>0)){
						mailbagEnquiryFilterVO.setDespatchSerialNumber(listMailbagPopupForm.getDespatchSerialNumber().toUpperCase());
					}
					
					if((listMailbagPopupForm.getReceptacleSerialNumber()!=null) && (listMailbagPopupForm.getReceptacleSerialNumber().trim().length()>0)){
						mailbagEnquiryFilterVO.setReceptacleSerialNumber(listMailbagPopupForm.getReceptacleSerialNumber().toUpperCase());
					}
					
					if((listMailbagPopupForm.getConsignmentNumber()!=null) && (listMailbagPopupForm.getConsignmentNumber().trim().length()>0)){
						mailbagEnquiryFilterVO.setConsigmentNumber(listMailbagPopupForm.getConsignmentNumber().toUpperCase());
					}
					
					if((listMailbagPopupForm.getFromDate()!=null) && (listMailbagPopupForm.getFromDate().trim().length()>0)){
						mailbagEnquiryFilterVO.setScanFromDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(
								listMailbagPopupForm.getFromDate().toUpperCase()));
					}
					
					if((listMailbagPopupForm.getToDate()!=null) && (listMailbagPopupForm.getToDate().trim().length()>0)){
						mailbagEnquiryFilterVO.setScanToDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(
								listMailbagPopupForm.getToDate().toUpperCase()));
					}
					
					if((listMailbagPopupForm.getPaCode()!=null) && (listMailbagPopupForm.getPaCode().trim().length()>0)){
						mailbagEnquiryFilterVO.setPacode(listMailbagPopupForm.getPaCode().toUpperCase());
					}
					
					if((listMailbagPopupForm.getFlightNumber()!=null) && (listMailbagPopupForm.getFlightNumber().trim().length()>0)){
						mailbagEnquiryFilterVO.setFlightNumber(listMailbagPopupForm.getFlightNumber().toUpperCase());
					}
					
					if((listMailbagPopupForm.getFlightDate()!=null) && (listMailbagPopupForm.getFlightDate().trim().length()>0)){
						mailbagEnquiryFilterVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(
								listMailbagPopupForm.getFlightDate().toUpperCase()));
					}
					
					if((listMailbagPopupForm.getUpliftAirport()!=null) && (listMailbagPopupForm.getUpliftAirport().trim().length()>0)){
						mailbagEnquiryFilterVO.setUpliftAirport(listMailbagPopupForm.getUpliftAirport().toUpperCase());
					}
					
					if((listMailbagPopupForm.getUldNumber()!=null) && (listMailbagPopupForm.getUldNumber().trim().length()>0)){
						mailbagEnquiryFilterVO.setUldNumber(listMailbagPopupForm.getUldNumber().toUpperCase());
					}
					
					if((listMailbagPopupForm.getOriginAirportCode()!=null) && (listMailbagPopupForm.getOriginAirportCode().trim().length()>0)){
						mailbagEnquiryFilterVO.setOriginAirportCode(listMailbagPopupForm.getOriginAirportCode().toUpperCase());
					}
					
					if((listMailbagPopupForm.getDestinationAirportCode()!=null) && (listMailbagPopupForm.getDestinationAirportCode().trim().length()>0)){
						mailbagEnquiryFilterVO.setDestinationAirportCode(listMailbagPopupForm.getDestinationAirportCode().toUpperCase());	
					}
					
				}
		
		return mailbagEnquiryFilterVO;
	}
	

	/**
	 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.listMailPopup.ScreenLoadCommand.getOneTimeParameterTypes.java
	 *	Version		:	Name	:	Date			:	Updation
	 * ---------------------------------------------------
	 *		0.1		:	A-7929	:	22-Oct-2018	:	Draft
	 * @param listMailbagPopupForm 
	 */
	 private Collection<String> getOneTimeParameterTypes() {
	    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
	    	ArrayList<String> parameterTypes = new ArrayList<String>();
	    	parameterTypes.add(ONETIME_MAILSTATUS);
	    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
	    	return parameterTypes;    	
	    }
	 /**
		 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.listMailPopup.ScreenLoadCommand.getOneTimeParameterTypes.java
		 *	Version		:	Name	:	Date			:	Updation
		 * ---------------------------------------------------
		 *		0.1		:	A-7929	:	22-Oct-2018	:	Draft
		 * @param listMailbagPopupForm 
		 */
	 
	 private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
			log.entering("ScreenLoadCommand","getOneTimeValues");
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			
			SharedDefaultsDelegate sharedDefaultsDelegate = 
				new SharedDefaultsDelegate();
			Map<String, Collection<OneTimeVO>> oneTimeValues = null;
			
			try {
				oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
						logonAttributes.getCompanyCode(), 
						getOneTimeParameterTypes());
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
			log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
			log.exiting("ScreenLoadCommand","getOneTimeValues");
			return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
		}
}
