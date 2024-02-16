/*
 * ListSearchConsignmentCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchConsignmentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ListSearchConsignmentCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("ListSearchConsignmentCommand");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";	
   private static final String SCREEN_ID_MAILBAG = "mailtracking.defaults.mailBagEnquiry";
	
   
   private static final String ONETIME_CATEGORY = "mailtracking.defaults.mailcategory";
   private static final String ONETIME_CLASS = "mailtracking.defaults.mailclass";
   private static final String ONETIME_RESDITEVENT = "mailtracking.defaults.resditevent";
   private static final String ONETIME_FLIGHTTYPE = "mailtracking.defaults.carditenquiry.flighttype";
   private static final String ONETIME_SEARCHMODE = "mailtracking.defaults.carditenquiry.searchmode";
   private static final String INVOKING_SCREEN = "FROMMAILACCEPTANCE";
   private static final String CARDIT_SCREEN = "carditEnquiry";
   private static final String ONETIME_MAILSTATUS = "mailtracking.defaults.mailstatus";
   private static final String MANDATORY_FILELD_MUST_NOT_BE_NULL ="mailtracking.defaults.searchconsignment.mandatoryfieldnull";
   private static final String MAIL_COUNT_LIMIT_SYSPAR = "mail.operations.mailcountlimitforawbhandling";
   
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.log(Log.FINE,"****inside mail search command*************************");

    	log.entering("ListSearchConsignmentCommand","execute");
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();  
		SearchConsignmentForm carditEnquiryForm = 
    		(SearchConsignmentForm)invocationContext.screenModel;
		SearchConsignmentSession carditEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ErrorVO error=null;
    	CarditEnquiryFilterVO carditEnquiryFilterVO =null;
    	String displayPage="";
    	log.log(Log.INFO, "carditEnquiryForm.getInvokingScreen() ",
				carditEnquiryForm.getInvokingScreen());
    	
    	Collection<String> systemParameterCodes = new ArrayList<String>();   
    	systemParameterCodes.add(MAIL_COUNT_LIMIT_SYSPAR);
    	
    	String mailCount = findSystemParameterByCodes(systemParameterCodes).get(MAIL_COUNT_LIMIT_SYSPAR);
    	if(mailCount==null){    
    		mailCount="0";
    	}
    	carditEnquiryForm.setMailCount(mailCount);
    	
		if(CARDIT_SCREEN.equals(carditEnquiryForm.getInvokingScreen())){
    		log.log(log.INFO,"INSIDE INVOKING carditEnquiry SCREEN");
    		carditEnquiryFilterVO=carditEnquirySession.getCarditEnquiryFilterVO();
    		log.log(Log.INFO,
					"INSIDE INVOKING carditEnquiry SCREEN filter vo before",
					carditEnquiryFilterVO);
			populateForm(carditEnquiryForm,carditEnquiryFilterVO);
    		log.log(Log.INFO,
					"INSIDE INVOKING carditEnquiry SCREEN filter vo after",
					carditEnquiryFilterVO);
			carditEnquiryForm.setScreenMode("MAIN");
		}
    	else if(INVOKING_SCREEN.equals(carditEnquiryForm.getInvokingScreen())){
    		carditEnquiryFilterVO=carditEnquirySession.getCarditEnquiryFilterVO();
    		populateForm(carditEnquiryForm,carditEnquiryFilterVO);
    		carditEnquiryForm.setScreenMode("MAIN");
		}else{
    	carditEnquiryFilterVO =new CarditEnquiryFilterVO();
    	if(carditEnquiryForm.getOoe() != null && carditEnquiryForm.getOoe().trim().length() > 0) {
    		carditEnquiryFilterVO.setOoe(carditEnquiryForm.getOoe());
    	}
    	if(carditEnquiryForm.getDoe() != null && carditEnquiryForm.getDoe().trim().length() > 0) {
    		carditEnquiryFilterVO.setDoe(carditEnquiryForm.getDoe());
    	}
    	if(carditEnquiryForm.getMailCategoryCode() != null && carditEnquiryForm.getMailCategoryCode().trim().length() > 0) {
    		carditEnquiryFilterVO.setMailCategoryCode(carditEnquiryForm.getMailCategoryCode());
    	}
    	if(carditEnquiryForm.getMailSubclass() != null && carditEnquiryForm.getMailSubclass().trim().length() > 0) {
        	carditEnquiryFilterVO.setMailSubclass(carditEnquiryForm.getMailSubclass());
    	}
    	if(carditEnquiryForm.getYear() != null && carditEnquiryForm.getYear().trim().length() > 0) {
    		carditEnquiryFilterVO.setYear(carditEnquiryForm.getYear());
        }
    	if(carditEnquiryForm.getDespatchSerialNumber() != null && carditEnquiryForm.getDespatchSerialNumber().trim().length() > 0) {
    		carditEnquiryFilterVO.setDespatchSerialNumber(carditEnquiryForm.getDespatchSerialNumber());
        }
    	if(carditEnquiryForm.getReceptacleSerialNumber() != null && carditEnquiryForm.getReceptacleSerialNumber().trim().length() > 0) {
        	carditEnquiryFilterVO.setReceptacleSerialNumber(carditEnquiryForm.getReceptacleSerialNumber());
    	}
    	if(carditEnquiryForm.getConsignmentDocument() != null && carditEnquiryForm.getConsignmentDocument().trim().length() > 0) {
        	carditEnquiryFilterVO.setConsignmentDocument(carditEnquiryForm.getConsignmentDocument());
    	}
    	if(carditEnquiryForm.getPao() != null && carditEnquiryForm.getPao().trim().length() > 0) {
        	carditEnquiryFilterVO.setPaoCode(carditEnquiryForm.getPao());
    	}
    	if(logonAttributes.getCompanyCode() != null && logonAttributes.getCompanyCode().trim().length() > 0) {
    		carditEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
        }
    	if(carditEnquiryForm.getFlightNumber() != null && carditEnquiryForm.getFlightNumber().trim().length() > 0) {
        	carditEnquiryFilterVO.setFlightNumber(carditEnquiryForm.getFlightNumber());
    	}        	
    	if(carditEnquiryForm.getPol() != null && carditEnquiryForm.getPol().trim().length() > 0) {
        	carditEnquiryFilterVO.setPol(carditEnquiryForm.getPol());
    	}
    	if(carditEnquiryForm.getUldNumber()!= null && carditEnquiryForm.getUldNumber().trim().length() > 0) {
        	carditEnquiryFilterVO.setUldNumber(carditEnquiryForm.getUldNumber());
    	}
    	if(carditEnquiryForm.getIsAwbAttached()!= null && carditEnquiryForm.getIsAwbAttached().trim().length() > 0) {
    		if("Yes".equals(carditEnquiryForm.getIsAwbAttached())){
    			carditEnquiryFilterVO.setIsAWBAttached(MailConstantsVO.FLAG_YES);  
    		}else if("No".equals(carditEnquiryForm.getIsAwbAttached())){
    			carditEnquiryFilterVO.setIsAWBAttached(MailConstantsVO.FLAG_NO);  
    		}
    			
    		
        	
    	}
    	//Added for ICRD-205027 starts
    	if(carditEnquiryForm.getMailbagId()!= null && carditEnquiryForm.getMailbagId().trim().length() > 0) {
        	carditEnquiryFilterVO.setMailbagId(carditEnquiryForm.getMailbagId());
    	}
    	//Added for ICRD-205027 ends
    	//Added for ICRD-214795 starts
    	if(carditEnquiryForm.getReqDeliveryDate()!=null&&
    			carditEnquiryForm.getReqDeliveryDate().trim().length()>0){
    			//carditEnquiryForm.getReqDeliveryTime()!=null&&
    			//carditEnquiryForm.getReqDeliveryTime().trim().length()>0)
    		LocalDate rqdDlvTim=new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
    		StringBuilder reqDeliveryTime=new StringBuilder(carditEnquiryForm.getReqDeliveryDate());
    		if(carditEnquiryForm.getReqDeliveryTime()!=null&&
    				carditEnquiryForm.getReqDeliveryTime().trim().length()>0){
    			reqDeliveryTime.append(" ").append(carditEnquiryForm.getReqDeliveryTime()).append(":00");
    			rqdDlvTim.setDateAndTime(reqDeliveryTime.toString());
    		}else{
    			rqdDlvTim.setDate(reqDeliveryTime.toString());
    		}    		
    		carditEnquiryFilterVO.setReqDeliveryTime(rqdDlvTim);
    		
    	}
    	//Added for ICRD-214795 ends
    	//validating date..  	
    	
    	if(  (carditEnquiryForm.getFromDate()==null || carditEnquiryForm.getFromDate().trim().length()==0  || carditEnquiryForm.getToDate()==null || carditEnquiryForm.getToDate().trim().length()==0) 
    			&& (carditEnquiryForm.getMailbagId()==null||carditEnquiryForm.getMailbagId().trim().length()==0) 
    			&& (carditEnquiryForm.getConsignmentDocument()==null || carditEnquiryForm.getConsignmentDocument().trim().length()==0 )
    			&& (carditEnquiryForm.getConsignmentDate()==null || carditEnquiryForm.getConsignmentDate().trim().length()==0)
    			&& (carditEnquiryForm.getDocumentNumber()==null ||carditEnquiryForm.getDocumentNumber().trim().length()==0 ||carditEnquiryForm.getShipmentPrefix()==null || carditEnquiryForm.getShipmentPrefix().trim().length()==0)
    			&& (carditEnquiryForm.getFlightDate()==null ||carditEnquiryForm.getFlightDate().trim().length()==0 ||
    			carditEnquiryForm.getCarrierCode()==null || carditEnquiryForm.getCarrierCode().trim().length()==0||
    			carditEnquiryForm.getFlightNumber()==null||carditEnquiryForm.getFlightNumber().trim().length()==0) 
    			){
    		error=new ErrorVO(MANDATORY_FILELD_MUST_NOT_BE_NULL);
 			errors.add(error);
    		invocationContext.addAllError(errors);
			invocationContext.target = TARGET;
			return;
    	}
    	
    	
    /*	if(carditEnquiryForm.getFromDate()==null || carditEnquiryForm.getFromDate().trim().length()==0){
			error=new ErrorVO(FROMDATE_MUST_NOT_BE_NULL);
 			errors.add(error);	
		}
    	
    	if(carditEnquiryForm.getToDate()==null || carditEnquiryForm.getToDate().trim().length()==0){
			error=new ErrorVO(TODATE_MUST_NOT_BE_NULL);
 			errors.add(error);		
		}	
    	
		if(errors!=null && errors.size()>0){
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET;
				return;
		}*/
		
		
    	LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
    	LocalDate toDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
    	
    	if(carditEnquiryForm.getFromDate() != null && carditEnquiryForm.getFromDate().trim().length() > 0) {	    
			fromDate.setDate(carditEnquiryForm.getFromDate().toUpperCase());
			carditEnquiryFilterVO.setFromDate(fromDate);
	    }
    	
    	if(carditEnquiryForm.getToDate() != null && carditEnquiryForm.getToDate().trim().length() > 0) {	       
			toDate.setDate(carditEnquiryForm.getToDate().toUpperCase());
			carditEnquiryFilterVO.setToDate(toDate);
	    }
    	
    	if(fromDate.isGreaterThan(toDate)){
    		invocationContext.addError(new ErrorVO("mailtracking.defaults.searchconsignment.greaterdate"));
			invocationContext.target = TARGET;
			return;			
		}	
    	
    	
    	if(carditEnquiryForm.getFlightDate() != null && carditEnquiryForm.getFlightDate().trim().length() > 0) {
    		LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
    		flightDate.setDate(carditEnquiryForm.getFlightDate().toUpperCase());
    		carditEnquiryFilterVO.setFlightDate(flightDate);
    	}
		
    	
    	
		carditEnquiryFilterVO.setCarrierCode(carditEnquiryForm.getCarrierCode());
    	carditEnquiryFilterVO.setFlightNumber(carditEnquiryForm.getFlightNumber());
    	
    	carditEnquiryFilterVO.setPol(carditEnquiryForm.getPol());
    	if(carditEnquiryForm.getMailStatus() != null && carditEnquiryForm.getMailStatus().trim().length() > 0) {
    		carditEnquiryFilterVO.setMailStatus(carditEnquiryForm.getMailStatus());
    	}
    	//Added by a-7531 for icrd-192536 starts
    	if(carditEnquiryForm.getShipmentPrefix() != null && carditEnquiryForm.getShipmentPrefix().trim().length() > 0) {
    		carditEnquiryFilterVO.setShipmentPrefix(carditEnquiryForm.getShipmentPrefix());
    	}
    	if(carditEnquiryForm.getDocumentNumber() != null && carditEnquiryForm.getDocumentNumber().trim().length() > 0) {
    		carditEnquiryFilterVO.setDocumentNumber(carditEnquiryForm.getDocumentNumber());
    	}
    	//Added by a-7531 for icrd-192536 ends
    	
    	
    	//A-8061 Added for ICRD-82434 starts
    	LocalDate consignmentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
    	
    	if(carditEnquiryForm.getConsignmentDate() != null && carditEnquiryForm.getConsignmentDate().trim().length() > 0) {	    
    		consignmentDate.setDate(carditEnquiryForm.getConsignmentDate().toUpperCase());
			carditEnquiryFilterVO.setConsignmentDate(consignmentDate);
	    }
    	if(carditEnquiryForm.getIsPendingResditChecked()!=null){
    		
    		carditEnquiryFilterVO.setPendingResditChecked(true);
    	}
    	if(carditEnquiryForm.getFlightType() != null && carditEnquiryForm.getFlightType().trim().length() > 0) {
    		carditEnquiryFilterVO.setFlightType(carditEnquiryForm.getFlightType());
    	}

    	//A-8061 Added for ICRD-82434 ends
    	
    	
    	
    	log.log(Log.FINE, "Company Code", carditEnquiryFilterVO.getCompanyCode());
		log.log(Log.FINE, "From Date", carditEnquiryFilterVO.getFromDate());
		log.log(Log.FINE, "To Date", carditEnquiryFilterVO.getToDate());
		log.log(Log.FINE, "Flight Date", carditEnquiryFilterVO.getFlightDate());
		log.log(Log.FINE, "Port", carditEnquiryFilterVO.getPol());
		log.log(Log.FINE, "Pao", carditEnquiryFilterVO.getPaoCode());
		log.log(Log.FINE, "Ooe", carditEnquiryFilterVO.getOoe());
		log.log(Log.FINE, "Doe", carditEnquiryFilterVO.getDoe());
		log.log(Log.FINE, "RSN", carditEnquiryFilterVO.getReceptacleSerialNumber());
		log.log(Log.FINE, "DSN", carditEnquiryFilterVO.getDespatchSerialNumber());
		log.log(Log.FINE, "Mail Class", carditEnquiryFilterVO.getMailClass());
		log.log(Log.FINE, "Mail Category", carditEnquiryFilterVO.getMailCategoryCode());
		log.log(Log.FINE, "Year", carditEnquiryFilterVO.getYear());
		log.log(Log.FINE, "Consignment No", carditEnquiryFilterVO.getConsignmentDocument());
		log.log(Log.FINE, "FlightCarrierCode", carditEnquiryFilterVO.getFlightNumber());
		log.log(Log.FINE, "MailStatus", carditEnquiryFilterVO.getMailStatus()); 
		//Added by a-7531 for icrd-192536
		log.log(Log.FINE, "Document number", carditEnquiryFilterVO.getDocumentNumber());
		log.log(Log.FINE, "ShipmentPrefix", carditEnquiryFilterVO.getDocumentNumber()); 
		}
//    	 Added by Sever for handling PageAwareMultiMapper implementation
		HashMap<String, String> indexMap = null;
		HashMap<String, String> finalMap = null;
		if (carditEnquirySession.getIndexMap() != null) {
			indexMap = carditEnquirySession.getIndexMap();
		}

		if (indexMap == null) {
			log.log(Log.INFO, "INDEX MAP IS NULL");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
		}

		int nAbsoluteIndex = 0;
		displayPage = carditEnquiryForm.getDisplayPage();
		String strAbsoluteIndex = indexMap.get(displayPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		if(!INVOKING_SCREEN.equals(carditEnquiryForm.getInvokingScreen())
				|| !CARDIT_SCREEN.equals(carditEnquiryForm.getInvokingScreen())){
			carditEnquiryFilterVO.setAbsoluteIndex(nAbsoluteIndex);
			carditEnquiryForm.setInvokingScreen("");
		}
		// String displayPage = listFltSchForm.getDisplayPage();
		// Server ends
		//Added by A-5220 for ICRD-21098 starts
		carditEnquiryFilterVO.setPageNumber(1);
		if(SearchConsignmentForm.NAV_MOD_LIST.equalsIgnoreCase(carditEnquiryForm.getNavigationMode()) || carditEnquiryForm.getNavigationMode() == null ){
			log.log(Log.INFO, "list action");
			carditEnquiryFilterVO.setTotalRecordsCount(-1);
			carditEnquiryFilterVO.setPageNumber(1);
		}else if(SearchConsignmentForm.NAV_MOD_PAGINATION.equalsIgnoreCase(carditEnquiryForm.getNavigationMode())){
			log.log(Log.INFO, "navigation action");
			carditEnquiryFilterVO.setTotalRecordsCount(carditEnquirySession.getTotalRecordCount());
			carditEnquiryFilterVO.setPageNumber(Integer.parseInt(carditEnquiryForm.getDisplayPage()));
		}
		if(mailCount!=null && !mailCount.isEmpty()){
	    	carditEnquiryFilterVO.setMailCount(Integer.parseInt(mailCount));
	    	}
		//Added by A-5220 for ICRD-21098 ends
		carditEnquirySession.setCarditEnquiryFilterVO(carditEnquiryFilterVO);
    	MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
    	Page<MailbagVO> listBags=null;
    	try {
    		if("POPUP".equals(carditEnquiryForm.getScreenMode())){
    			listBags = delegate.findConsignmentDetails(carditEnquiryFilterVO,Integer.parseInt(displayPage));
    		}
    		else{
    			listBags = delegate.findCarditMails(carditEnquiryFilterVO,Integer.parseInt(displayPage));
    		}
			log.log(Log.FINE,
					"<<<<<<<<<<<<<<-Values in VOs>>>>>>>>>>>>>>>>>>-*",
					listBags);
			if( listBags == null || listBags.size() == 0 )
			{	
				carditEnquiryForm.setDisableButton("N");
				invocationContext.addError(new ErrorVO("mailtracking.defaults.searchconsignment.mailbagdoesntexists"));
				invocationContext.target = TARGET;
			}
			else{
				log.log(Log.FINE, "----*-*--*-size-*-*-*-*-*-*", listBags.size());
				carditEnquiryForm.setDisableButton("Y");
			}
			carditEnquirySession.setCarditEnquiryFilterVO(carditEnquiryFilterVO);
			//A-8061 added for ICRD-233692 starts
			
			if(displayPage!=null && "1".equals(displayPage) && listBags!=null){
			String[] grandTotals=null;
			
			grandTotals =  delegate.findGrandTotals(carditEnquiryFilterVO);
			
			if(grandTotals!=null&&grandTotals.length==2){
				carditEnquirySession.setTotalPcs(Integer.valueOf(grandTotals[0]));
				carditEnquirySession.setTotalWeight(new Measure(UnitConstants.MAIL_WGT ,Double.parseDouble(grandTotals[1])));
			}
	
			}
		    
			//A-8061 added for ICRD-233692 ends
			
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}
    	
		/*
		 * To Mark the Mailbags as InList, once it is added to the List. 
		 */
		Collection<MailbagVO> selectedMailbagVOsInList = null;
		Collection<MailbagVO> removeDummyMailbagVOsFromList = new ArrayList<MailbagVO>();
		Collection<MailbagVO> selectedDummyMailbagVOsInList = new ArrayList<MailbagVO>();
		if(carditEnquirySession.getMailBagVOsForListing ()!= null &&
				carditEnquirySession.getMailBagVOsForListing().size() >0 ) {
			selectedMailbagVOsInList=carditEnquirySession.getMailBagVOsForListing();
			for(MailbagVO mailInList : selectedMailbagVOsInList) {
				selectedDummyMailbagVOsInList.add(mailInList);
			} 
		}
		if(selectedDummyMailbagVOsInList != null && selectedDummyMailbagVOsInList.size() > 0) {
			if(listBags != null && listBags.size() > 0) {
				for(MailbagVO mailVO : listBags) {
					String outerKey = null;		
					String innerKey = null;
					if(mailVO.getMailbagId() != null &&
							mailVO.getMailbagId().trim().length() > 0) {
						outerKey = new StringBuilder()
										.append(mailVO.getMailbagId())
										.toString();
					}else {
						outerKey = new StringBuilder()
										.append(mailVO.getOoe())
										.append(mailVO.getDoe())
										.append(mailVO.getMailCategoryCode())
										.append(mailVO.getMailSubclass())
										.append(mailVO.getYear())
										.append(mailVO.getDespatchSerialNumber())
										.toString();
					}
					for(MailbagVO newmailVO : selectedDummyMailbagVOsInList) {
						if(newmailVO.getMailbagId() != null &&
								newmailVO.getMailbagId().trim().length() > 0) {
							innerKey = new StringBuilder()
							.append(newmailVO.getMailbagId())
							.toString();
						}else {
							innerKey = new StringBuilder()
											.append(newmailVO.getOoe())
											.append(newmailVO.getDoe())
											.append(newmailVO.getMailCategoryCode())
											.append(newmailVO.getMailSubclass())
											.append(newmailVO.getYear())
											.append(newmailVO.getDespatchSerialNumber())
											.toString();
						}
						if(outerKey.equals(innerKey)) {
							mailVO.setInList(MailConstantsVO.FLAG_YES);
							removeDummyMailbagVOsFromList.add(newmailVO);
							break;
						}
					}
					if(removeDummyMailbagVOsFromList != null && 
							removeDummyMailbagVOsFromList.size() > 0) {
						selectedDummyMailbagVOsInList.removeAll(removeDummyMailbagVOsFromList);
					}
				}
			}
		}
    	
//		 Added by Server on handling PageAwareMultiMapper
		finalMap = indexMap;
		if (carditEnquirySession.getMailbagVOsCollection() != null) {
			finalMap = buildIndexMap(indexMap, carditEnquirySession.getMailbagVOsCollection());
		}
		carditEnquirySession.setIndexMap(finalMap);
		log.log(Log.INFO, "GOING TO PUT FINAL MAP IN SESSION---------*****",
				finalMap);
		carditEnquirySession.removeCarditEnquiryVO();
    	carditEnquirySession.setOneTimeVOs(getOneTimeValues());
		carditEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = TARGET;
    	log.exiting("ScreenloadCarditEnquiryCommand","execute");
    	
    	MailBagEnquirySession mailBagEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID_MAILBAG);
    	
    	if(listBags!=null && listBags.size()>0){
    		if(listBags.size()<listBags.getActualPageSize()){
    			ArrayList<MailbagVO> listMailBags = new ArrayList<>();
        		for(int i =0; i<listBags.size();i++){
        			listMailBags.add(listBags.get(i));
        		}
        		Page<MailbagVO> listmails= new Page<MailbagVO>(listMailBags, listBags.getPageNumber(), listBags.getDefaultPageSize(), listBags.size(), listBags.getStartIndex() , listBags.getEndIndex(), listBags.hasNextPage());
        		if(!listBags.hasNextPage() && carditEnquirySession.getTotalPcs()<listBags.getTotalRecordCount()){
        			listmails.setTotalRecordCount(listBags.getTotalRecordCount());
        		}
        		else{
        			listmails.setTotalRecordCount(listBags.size());
        		}
        		carditEnquirySession.setMailbagVOsCollection(listmails);
        		carditEnquirySession.setTotalRecordCount(listBags.getTotalRecordCount());
    		}
    		else{
    		carditEnquirySession.setMailbagVOsCollection(listBags);
    		//Added by A-5220 for ICRD-21098 starts
    		carditEnquirySession.setTotalRecordCount(listBags.getTotalRecordCount());
    		//Added by A-5220 for ICRD-21098 ends
    		}
    	}else{
    		carditEnquirySession.setMailbagVOsCollection(null);
    		carditEnquirySession.setTotalPcs(0);
			carditEnquirySession.setTotalWeight(null);
    	}
    }
    
	// Added by Server on handling PageAwareMultiMapper
	/**
	 * method to bulid the hashmap to maintain absoluteindex
	 *
	 * @param existingMap HashMap<String, String>
	 * @param page Page
	 * @return HashMap<String, String>
	 */
	private HashMap<String, String> buildIndexMap(HashMap<String,
			String> existingMap, Page page) {

		HashMap<String, String> finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber() + 1));
		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}
		if (!isPageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}
		return finalMap;
	}
    
    
    
    /**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("ScreenLoadCommand","getOneTimeValues");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		
		try {
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
//printStackTrrace()();
			handleDelegateException(businessDelegateException);
		}
		updateMilitaryClass(oneTimeValues.get(ONETIME_CLASS));
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	 
	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	parameterTypes.add(ONETIME_CATEGORY);
    	parameterTypes.add(ONETIME_CLASS);
    	parameterTypes.add(ONETIME_RESDITEVENT);
    	parameterTypes.add(ONETIME_FLIGHTTYPE);
    	parameterTypes.add(ONETIME_SEARCHMODE);
    	parameterTypes.add(ONETIME_MAILSTATUS);
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
       
	/**
	 * Appends the values of various oneTimeVOs's fieldValues if they all have the
	 * same description 
	 * Feb 7, 2007, A-1739
	 * @param mailClasses
	 */
	private void updateMilitaryClass(Collection<OneTimeVO> mailClasses) {
		log.exiting("ScreenLoadCommand","updateClass");
		if(mailClasses != null && mailClasses.size() > 0) {
			Collection<Collection<OneTimeVO>> mailClassVOs = 
				new ArrayList<Collection<OneTimeVO>>();
			String classDesc = null;
			String fildDes=null;
			Collection<OneTimeVO> militaryVOs = 
				new ArrayList<OneTimeVO>();
			for(OneTimeVO mailClassVO : mailClasses) {
				//finding vos with same description, possibly military mail
				if(mailClassVO.getFieldDescription().equals(classDesc)) {
					militaryVOs.add(mailClassVO);
				} else {
					classDesc = mailClassVO.getFieldDescription();
					militaryVOs = new ArrayList<OneTimeVO>();
					militaryVOs.add(mailClassVO);
					
				}
				if(militaryVOs.size() > 1) {
					String fieldDesc=((ArrayList<OneTimeVO>)militaryVOs).get(0).getFieldDescription();
					if(!fieldDesc.equals(fildDes)){
						mailClassVOs.add(militaryVOs);	
						fildDes=fieldDesc;
					}
				}	
			}
			if(mailClassVOs.size() > 0) {
				for(Collection<OneTimeVO> oneTimeVOs : mailClassVOs) {
					if(oneTimeVOs.size() > 0) {
						mailClasses.removeAll(oneTimeVOs);				
						StringBuilder oneTimeVal = new StringBuilder(); 
						for(OneTimeVO militaryVO : oneTimeVOs) {
							oneTimeVal.append(militaryVO.getFieldValue()).
										append(MailConstantsVO.MALCLS_SEP);
						}
						OneTimeVO mailClassVO = oneTimeVOs.iterator().next();				
						mailClassVO.setFieldValue(
								oneTimeVal.substring(0,oneTimeVal.length()-1).toString());						
						mailClasses.add(mailClassVO);
					}
				}
			}
		}
		log.exiting("ScreenLoadCommand","updateClass");
	}
	private void populateForm(SearchConsignmentForm carditEnquiryForm,CarditEnquiryFilterVO carditEnquiryFilterVO) {
		if(carditEnquiryFilterVO.getOoe()!=null && carditEnquiryFilterVO.getOoe().trim().length()>0 ){
			carditEnquiryForm.setOoe(carditEnquiryFilterVO.getOoe());
		}
		else{
			carditEnquiryForm.setOoe("");
		}
		if(carditEnquiryFilterVO.getDoe()!=null && carditEnquiryFilterVO.getDoe().trim().length()>0){
			carditEnquiryForm.setDoe(carditEnquiryFilterVO.getDoe());
		}
		else{
			carditEnquiryForm.setDoe("");
		}
		if(carditEnquiryFilterVO.getMailCategoryCode()!=null && carditEnquiryFilterVO.getMailCategoryCode().trim().length()>0){
			carditEnquiryForm.setMailCategoryCode(carditEnquiryFilterVO.getMailCategoryCode());
		}
		else{
			carditEnquiryForm.setMailCategoryCode("");
		}
		if(carditEnquiryFilterVO.getMailSubclass()!=null && carditEnquiryFilterVO.getMailSubclass().trim().length()>0){
			carditEnquiryForm.setMailSubclass(carditEnquiryFilterVO.getMailSubclass());
		}
		else{
			carditEnquiryForm.setMailSubclass("");
		}
		if(carditEnquiryFilterVO.getYear()!=null && carditEnquiryFilterVO.getYear().trim().length()>0){
			carditEnquiryForm.setYear(carditEnquiryFilterVO.getYear());
		}
		else{
			carditEnquiryForm.setYear("");
		}
		if(carditEnquiryFilterVO.getDespatchSerialNumber()!=null && carditEnquiryFilterVO.getDespatchSerialNumber().trim().length()>0){
			carditEnquiryForm.setDespatchSerialNumber(carditEnquiryFilterVO.getDespatchSerialNumber());
		}
		else{
			carditEnquiryForm.setDespatchSerialNumber("");
		}
		if(carditEnquiryFilterVO.getReceptacleSerialNumber()!=null && carditEnquiryFilterVO.getReceptacleSerialNumber().trim().length()>0){
			carditEnquiryForm.setReceptacleSerialNumber(carditEnquiryFilterVO.getReceptacleSerialNumber());
		}
		else{
			carditEnquiryForm.setReceptacleSerialNumber("");
		}
		if(carditEnquiryFilterVO.getConsignmentDocument() != null && carditEnquiryFilterVO.getConsignmentDocument().trim().length() > 0) {
			carditEnquiryForm.setConsignmentDocument(carditEnquiryFilterVO.getConsignmentDocument());
    	}
		else{
			carditEnquiryForm.setConsignmentDocument("");
		}
    	if(carditEnquiryFilterVO.getPaoCode() != null && carditEnquiryFilterVO.getPaoCode().trim().length() > 0) {
    		carditEnquiryForm.setPao(carditEnquiryFilterVO.getPaoCode());
    	}
    	else{
			carditEnquiryForm.setPao("");
		}
    	if(carditEnquiryFilterVO.getCarrierCode() != null && carditEnquiryFilterVO.getCarrierCode().trim().length() > 0) {
    		carditEnquiryForm.setCarrierCode(carditEnquiryFilterVO.getCarrierCode());
    	} 
    	else{      
			carditEnquiryForm.setCarrierCode("");
		}
    	if(carditEnquiryFilterVO.getFlightNumber() != null && carditEnquiryFilterVO.getFlightNumber().trim().length() > 0) {
    		carditEnquiryForm.setFlightNumber(carditEnquiryFilterVO.getFlightNumber());
    	} 
    	else{
			carditEnquiryForm.setFlightNumber("");
		}
    	if(carditEnquiryFilterVO.getFlightDate() != null ) {
    		carditEnquiryForm.setFlightDate(carditEnquiryFilterVO.getFlightDate().toDisplayDateOnlyFormat());
    	} 
    	else{
    		carditEnquiryForm.setFlightDate("");
    	}
    	if(carditEnquiryFilterVO.getFromDate() != null) {
    		carditEnquiryForm.setFromDate(carditEnquiryFilterVO.getFromDate().toDisplayDateOnlyFormat());
    	} 
    	else{
    		carditEnquiryForm.setFromDate("");
    	}
    	if(carditEnquiryFilterVO.getToDate() != null) {
    		carditEnquiryForm.setToDate(carditEnquiryFilterVO.getToDate().toDisplayDateOnlyFormat());
    	} 
    	else{
    		carditEnquiryForm.setToDate("");
    	}
    	if(carditEnquiryFilterVO.getPol()!=null  && carditEnquiryFilterVO.getPol().trim().length() > 0){
    		carditEnquiryForm.setPol(carditEnquiryFilterVO.getPol());
    	}
    	else{
    		carditEnquiryForm.setPol("");
    	}
    	if(carditEnquiryFilterVO.getMailStatus()!=null  && carditEnquiryFilterVO.getMailStatus().trim().length() > 0){
    		carditEnquiryForm.setMailStatus(carditEnquiryFilterVO.getMailStatus());
    	}
    	else{
    		carditEnquiryForm.setMailStatus("");
    	}
    	//Added for ICRD-205027 starts
    	if(carditEnquiryFilterVO.getMailbagId()!=null  && carditEnquiryFilterVO.getMailbagId().trim().length() > 0){
    		carditEnquiryForm.setMailbagId(carditEnquiryFilterVO.getMailbagId());
    	}
    	else{
    		carditEnquiryForm.setMailbagId("");
    	}
    	//Added for ICRD-205027 ends 
        //Added by a7531 for icrd-192536
        if(carditEnquiryFilterVO.getShipmentPrefix()!=null  && carditEnquiryFilterVO.getShipmentPrefix().trim().length() > 0){
    		carditEnquiryForm.setShipmentPrefix(carditEnquiryFilterVO.getShipmentPrefix());
    	}
    	else{
    		carditEnquiryForm.setShipmentPrefix("");
    	}
    	if(carditEnquiryFilterVO.getDocumentNumber()!=null  && carditEnquiryFilterVO.getDocumentNumber().trim().length() > 0){
    		carditEnquiryForm.setDocumentNumber(carditEnquiryFilterVO.getDocumentNumber());
    	}
    	else{
    		carditEnquiryForm.setDocumentNumber("");
    	}
    	
    	//A-8061 Added for ICRD-82434 starts
     	if(carditEnquiryFilterVO.getConsignmentDate() != null ) {	    
    		carditEnquiryForm.setConsignmentDate(carditEnquiryFilterVO.getConsignmentDate().toDisplayDateOnlyFormat());
	    }
    	if(carditEnquiryFilterVO.isPendingResditChecked()){
    		carditEnquiryForm.setIsPendingResditChecked("ON");
    	}
    	if(carditEnquiryFilterVO.getFlightType() != null && carditEnquiryFilterVO.getFlightType().trim().length() > 0) {
    		carditEnquiryForm.setFlightType(carditEnquiryFilterVO.getFlightType());
    	}
    	//A-8061 Added for ICRD-82434 ends
    	
	}
	
	private Map<String, String> findSystemParameterByCodes(Collection<String> systemParameterCodes) {
		log.entering("UploadMailDetailsCommand","findSystemParameterByCodes");
    	Map<String, String> results = null;
    	try {
    		results = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
    	} catch(BusinessDelegateException businessDelegateException) {
    		handleDelegateException(businessDelegateException);
    	}
		log.exiting("UploadMailDetailsCommand","findSystemParameterByCodes");
		return results;
	}
}
