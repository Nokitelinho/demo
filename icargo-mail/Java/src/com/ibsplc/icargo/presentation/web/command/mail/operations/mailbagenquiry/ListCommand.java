/*
 * ListCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagenquiry;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
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
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailExportListSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;


/**
 * @author A-5991
 *
 */
public class ListCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "list_success";
   private static final String TARGET_FAILURE = "list_failure";
   private static final String BLANKSPACE = "";
   private static final String FLAG_YES = "Y";
   private static final String FLAG_NO = "N";
   private static final String FLAG_ACCEPTED = "ACP";
   //Removed by A-7531 as part of CRQ ICRD-197238 as these screen has no relevance now
   /*private static final String CONST_DSN = "DSNENQUIRY";
   private static final String CONST_INVENTORY = "INVENTORYLIST";*/
   private static final String CONST_ASSIGNCONTAINER = "ASSIGNCONTAINER";
   private static final String CONST_MAILEXPORTLIST = "MAILEXPORTLIST";
   private static final String CONST_OFFLOAD = "OFFLOAD";
   private static final String CONST_LISTCONTAINER = "LISTCONTAINER";
   private static final String CONST_MAILHISTORY = "MAILHISTORY";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID =
	   									"mailtracking.defaults.mailBagEnquiry";
   private static final String INV_SCREEN_ID =
			"mailtracking.defaults.inventorylist";
   private static final String DSN_SCREEN_ID =
	   										"mailtracking.defaults.dsnEnquiry";
   private static final String ASSGN_CONT_SCREEN_ID =
	   									"mailtracking.defaults.assignContainer";
   private static final String MAIL_EXPORT_LIST_SCREEN_ID = "mailtracking.defaults.mailexportlist";
   private static final String CURRENTSTATUS = "mailtracking.defaults.mailstatus";
   private static final String CATEGORY = "mailtracking.defaults.mailcategory";
   private static final String CONTAINERTYPE = "mailtracking.defaults.containertype";
   private static final String OPERATIONTYPE = "mailtracking.defaults.operationtype";
   //Added by A-7531 for CRQ ICRD-197238
   private static final String CONST_MAILBAGENQUIRY = "MAILBAGENQUIRY";

	 /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
  
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ListCommand","execute");

    	MailBagEnquiryForm mailBagEnquiryForm =
    		(MailBagEnquiryForm)invocationContext.screenModel;
    	MailBagEnquirySession mailBagEnquirySession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
        	AssignContainerSession assignContainerSession =
			getScreenSession(MODULE_NAME,ASSGN_CONT_SCREEN_ID);
    	MailExportListSession mailExportListSession =
			getScreenSession(MODULE_NAME,MAIL_EXPORT_LIST_SCREEN_ID);	
    	//Added by A-5201 for ICRD-20902 starts
    	String displayPageBeforeReset = mailBagEnquiryForm.getDisplayPage();
    	
    	if(("MAILHISTORY").equals(mailBagEnquiryForm.getFromScreen())&& //Added by A-8164 for ICRD-260365
    			("").equals(mailBagEnquiryForm.getMailbagId())){
    		mailBagEnquiryForm.setScreenStatusFlag
			(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		invocationContext.target = TARGET_FAILURE;
			return;
    	}
    	
    	if (!MailbagEnquiryFilterVO.FLAG_YES.equals(mailBagEnquiryForm.getCountTotalFlag())){
    		MailbagEnquiryFilterVO sessionFilterVO = mailBagEnquirySession.getMailbagEnquiryFilterVO();
            if(sessionFilterVO != null){
                    populateForm(sessionFilterVO,mailBagEnquiryForm);
                    if(mailBagEnquiryForm.getFromScreen()!=null && !CONST_LISTCONTAINER.equals(mailBagEnquiryForm.getFromScreen()))
                    {
                    mailBagEnquiryForm.setDisplayPage(displayPageBeforeReset);
                    }
                    else{
                    	if(sessionFilterVO.getPageNumber()==0 && CONST_LISTCONTAINER.equals(mailBagEnquiryForm.getFromScreen()))
                    		{
                    		mailBagEnquiryForm.setDisplayPage(String.valueOf(1));
                    }
            }
    	}
    	}
    	//Added by A-5201 for ICRD-20902 end
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
		String success = "N";
		mailBagEnquiryForm.setSubCheck(null);
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
		mailbagEnquiryFilterVO.setPageNumber(1);

		String fromscreen = mailBagEnquiryForm.getFromScreen();
		   //Removed the code to handle the navigation from Inventory screen as well as DSN enquiry screen as part of CRQ ICRD-197238 by A-7531 as this has no relevance now
		if (CONST_ASSIGNCONTAINER.equals(fromscreen)) {

			handleMailbagVOsFromAssign(
					mailBagEnquiryForm,
					logonAttributes,
					assignContainerSession);
		}else if(CONST_MAILEXPORTLIST.equals(fromscreen)){
			handleMailbagVOsFromExportList(
					mailBagEnquiryForm,
					logonAttributes,
					mailExportListSession);
		}else if(CONST_OFFLOAD.equals(fromscreen)){
			//This method replaces RefreshMailbagenquiryCommand which was used while closing from offload screen to mailbag enq
			handleRefreshOnCloseFromOffload(
					mailBagEnquiryForm,
					logonAttributes,
					mailBagEnquirySession);

		}else if(CONST_LISTCONTAINER.equals(fromscreen)){
			getOnetimeValues(mailBagEnquirySession,
					logonAttributes);
			//mailBagEnquiryForm.setDisplayPage("1");This line is commented by A-5219 for ICRD-91360, because every time this value is set to 1
		}

		if("Y".equals(mailBagEnquiryForm.getSuccessMailFlag())){
			mailBagEnquiryForm.setSuccessMailFlag("");
			success = "Y";
		}


		//	VALIDATING FORM
		errors = validateForm(mailBagEnquiryForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}



		mailBagEnquirySession.setMailbagVOs(null);
		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
    		new MailTrackingDefaultsDelegate();

		Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();

    	// VALIDATING FLIGHT CARRIER
    	String carrier = mailBagEnquiryForm.getFlightCarrierCode();
    	AirlineDelegate airlineDelegate = new AirlineDelegate();
    	AirlineValidationVO airlineValidationVO = null;
    	errors = null;
    	if (carrier != null && !"".equals(carrier)) {

    		try {
    			airlineValidationVO = airlineDelegate.validateAlphaCode(
    					logonAttributes.getCompanyCode(),
    					carrier.trim().toUpperCase());

    		}catch (BusinessDelegateException businessDelegateException) {
        		errors = handleDelegateException(businessDelegateException);
    			errors = new ArrayList<ErrorVO>();
    			Object[] obj = {carrier.toUpperCase()};
				ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.invalidcarrier",obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
    		}
    		if (errors != null && errors.size() > 0) {
    			validationerrors.addAll(errors);
    		}
    		else {
    			mailbagEnquiryFilterVO.setCarrierId
    							(airlineValidationVO.getAirlineIdentifier());
    		}
    	}

    	if (validationerrors != null && validationerrors.size() > 0) {
			invocationContext.addAllError(validationerrors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}

		// CREATING FILTER
    	mailbagEnquiryFilterVO.setFromScreen(mailBagEnquiryForm.getFromScreen());
		mailbagEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailbagEnquiryFilterVO.setCarrierCode
							(upper(mailBagEnquiryForm.getFlightCarrierCode()));
		mailbagEnquiryFilterVO.setContainerNumber
								(upper(mailBagEnquiryForm.getContainerNo()));
		mailbagEnquiryFilterVO.setCurrentStatus
										(mailBagEnquiryForm.getCurrentStatus());
		mailbagEnquiryFilterVO.setDamageFlag
							(String.valueOf(mailBagEnquiryForm.isDamaged()));
		mailbagEnquiryFilterVO.setDespatchSerialNumber
										(upper(mailBagEnquiryForm.getDsn()));
		mailbagEnquiryFilterVO.setDoe
									(upper(mailBagEnquiryForm.getDestnOE()));
		mailbagEnquiryFilterVO.setFromCarrier
											(upper(mailBagEnquiryForm.getFromCarrier()));
		if(FLAG_YES.equals(mailBagEnquiryForm.getCarditStatus())){
			mailbagEnquiryFilterVO.setCarditPresent(MailConstantsVO.FLAG_YES);
		}
		if(FLAG_NO.equals(mailBagEnquiryForm.getCarditStatus())){
			mailbagEnquiryFilterVO.setCarditPresent(MailConstantsVO.FLAG_NO);
		}
		if(("").equals(mailBagEnquiryForm.getCarditStatus())){
			mailbagEnquiryFilterVO.setCarditPresent("ALL");
		}

		if (mailBagEnquiryForm.getFlightDate()!=null && mailBagEnquiryForm.getFlightDate().length()>0) {
			LocalDate fltdate = new LocalDate
								(logonAttributes.getAirportCode(),ARP,false);
			mailbagEnquiryFilterVO.setFlightDate(fltdate.setDate(
					mailBagEnquiryForm.getFlightDate()));
		}
		if(mailBagEnquiryForm.getFlightNumber() != null
				&& !"".equals(mailBagEnquiryForm.getFlightNumber())){
			String fltnum = (mailBagEnquiryForm.getFlightNumber());
			mailbagEnquiryFilterVO.setFlightNumber(upper(fltnum));
		}
		mailbagEnquiryFilterVO.setMailCategoryCode
											(mailBagEnquiryForm.getCategory());
		mailbagEnquiryFilterVO.setMailSubclass
									(upper(mailBagEnquiryForm.getSubClass()));
		mailbagEnquiryFilterVO.setOoe(upper(mailBagEnquiryForm.getOriginOE()));
		mailbagEnquiryFilterVO.setReceptacleSerialNumber
										(upper(mailBagEnquiryForm.getRsn()));
		//Added for ICRD-133967 starts
		mailbagEnquiryFilterVO.setConsigmentNumber(upper(mailBagEnquiryForm.getConsigmentNumber()));
		mailbagEnquiryFilterVO.setUpuCode(upper(mailBagEnquiryForm.getUpuCode()));
		//Added for ICRD-133967 ends

		//Added for ICRD-205027 starts
		mailbagEnquiryFilterVO.setMailbagId(upper(mailBagEnquiryForm.getMailbagId()));
		//Added for ICRD-205027 ends
		//Added for ICRD-214795 starts
    	if(mailBagEnquiryForm.getReqDeliveryDate()!=null&&
    			mailBagEnquiryForm.getReqDeliveryDate().trim().length()>0){
    		LocalDate rqdDlvTim=new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
    		StringBuilder reqDeliveryTime=new StringBuilder(mailBagEnquiryForm.getReqDeliveryDate());
    		if(mailBagEnquiryForm.getReqDeliveryTime()!=null&&
    				mailBagEnquiryForm.getReqDeliveryTime().trim().length()>0){
    			reqDeliveryTime.append(" ").append(mailBagEnquiryForm.getReqDeliveryTime()).append(":00");
    			rqdDlvTim.setDateAndTime(reqDeliveryTime.toString());
    		}else{
    			rqdDlvTim.setDate(reqDeliveryTime.toString());
    		}    		
    		mailbagEnquiryFilterVO.setReqDeliveryTime(rqdDlvTim);
    		
    	}
    	//Added for ICRD-214795 ends
		if (mailBagEnquiryForm.isDamaged()) {
			mailbagEnquiryFilterVO.setDamageFlag(FLAG_YES);
		}
		else {
			mailbagEnquiryFilterVO.setDamageFlag(FLAG_NO);
		}
		mailbagEnquiryFilterVO.setTransitFlag(mailBagEnquiryForm.getTransit());
		/*
		 * START
		 * RENO K ABRAHAM
		 * Purpose : Time Stamp, for searching throughout a day span from 00:00:00 To 23:59:59.
		 */
		if ((!("").equals(mailBagEnquiryForm.getFromDate())) && mailBagEnquiryForm.getFromDate()!=null) {
			LocalDate fromdate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
			String scanFromDT = new StringBuilder(mailBagEnquiryForm.getFromDate())
																	.append(" ")
																	.append("00:00:00").toString();
			mailbagEnquiryFilterVO.setScanFromDate(fromdate.setDateAndTime(scanFromDT,false));
		}
		if ((!("").equals(mailBagEnquiryForm.getToDate())) && mailBagEnquiryForm.getToDate() !=null) {
			LocalDate todate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
			String scanToDT = new StringBuilder(mailBagEnquiryForm.getToDate())
																  .append(" ")
																  .append("23:59:59").toString();
			mailbagEnquiryFilterVO.setScanToDate(todate.setDateAndTime(scanToDT,false));
		}
		//END

		mailbagEnquiryFilterVO.setScanUser
									(upper(mailBagEnquiryForm.getUserId()));
		mailbagEnquiryFilterVO.setScanPort
										(upper(mailBagEnquiryForm.getPort()));
		if (!BLANKSPACE.equals(mailBagEnquiryForm.getYear())) {
			mailbagEnquiryFilterVO.setYear(mailBagEnquiryForm.getYear());
		}
		//Removed the code to handle navigation from Inventory screen  as part of CRQ ICRD-197238 by A-7531 as this has no relevance now
		if (CONST_MAILEXPORTLIST.equals(fromscreen)) {
			mailbagEnquiryFilterVO.setFlightNumber(null);
			mailbagEnquiryFilterVO.setCarrierCode(null);
			mailbagEnquiryFilterVO.setCarrierId(0);
			mailbagEnquiryFilterVO.setFlightDate(null);
			mailbagEnquiryFilterVO.setFromExportList(MailConstantsVO.FLAG_YES);

		}
		
		if(MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED.equals(
				mailbagEnquiryFilterVO.getCurrentStatus()) && 
				(carrier == null || (carrier != null && carrier.trim().length() == 0))){
			mailbagEnquiryFilterVO.setCarrierId(logonAttributes.getOwnAirlineIdentifier());
			mailbagEnquiryFilterVO.setCarrierCode(upper(logonAttributes.getOwnAirlineCode()));			
		}
		
		log.log(Log.FINE, "MailbagEnquiryFilterVO --------->>",
				mailbagEnquiryFilterVO);
		log.log(Log.FINE, "PageNumber --------->>", mailBagEnquiryForm.getDisplayPage());
		Page<MailbagVO> mailbagVOPage = null;
		
		/** changed by A-5216
		 * to enable last link and total record count
		 * for Jira Id: ICRD-21098 and ScreenId MTK009
		 */
		if (!MailbagEnquiryFilterVO.FLAG_YES.equals(mailBagEnquiryForm
				.getCountTotalFlag())
				&& mailBagEnquirySession.getTotalRecords() != null
				&& mailBagEnquirySession.getTotalRecords().intValue() != 0) {
			mailbagEnquiryFilterVO.setTotalRecords(mailBagEnquirySession
					.getTotalRecords().intValue());
		} else {
			mailbagEnquiryFilterVO.setTotalRecords(-1);
		}
		try {
			mailBagEnquirySession
					.setMailbagEnquiryFilterVO(mailbagEnquiryFilterVO);
			mailbagVOPage = mailTrackingDefaultsDelegate.findMailbags(
					mailbagEnquiryFilterVO,
					Integer.parseInt(mailBagEnquiryForm.getDisplayPage()));
			if ((mailbagVOPage == null || mailbagVOPage.getActualPageSize() == 0)) {
				errors = new ArrayList<ErrorVO>();
				Object[] obj = { "nomailbags" };
				ErrorVO error = new ErrorVO(
						"mailtracking.defaults.mailbagenquiry.msg.err.nomailbags",
						obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
			} else {
				for (MailbagVO mailbagVO : mailbagVOPage) {
					String maibagid = mailbagVO.getMailbagId();
					if(mailbagVO.getWeight()!=null){
						double weight=0;
						
					if(maibagid.length()==29&& mailbagVO.getWeight().getSystemValue()==0.0){
					weight = Double.parseDouble(maibagid.substring(25,
							29))/10;
					Measure wgt=new Measure(UnitConstants.MAIL_WGT,weight);
					mailbagVO.setWeight(wgt);//added by A-7371
					}
					else{//added by a-7531 for ICRD-274534 
						weight =mailbagVO.getWeight().getSystemValue();	
						Measure wgt=new Measure(UnitConstants.MAIL_WGT,weight);
						mailbagVO.setWeight(wgt);//added by A-7371
					}
					
					
					}
				}
			}
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}

		/*
		 * Clearing the details once listed
		 */
		//Removed the code to handle navigation from DSN enquiry screen as part of CRQ ICRD-197238 by A-7531 as this has no relevance now
		 if (CONST_ASSIGNCONTAINER.equals(fromscreen)) {
			assignContainerSession.setSelectedContainerVOs(null);
		}

		if (mailbagVOPage != null && mailbagVOPage.size() > 0) {
			log.log(Log.FINE, "Page --------->>", mailbagVOPage);
			mailBagEnquirySession.setTotalRecords(mailbagVOPage.getTotalRecordCount());
			mailBagEnquirySession.setMailbagEnquiryFilterVO(mailbagEnquiryFilterVO);//Added by A-5201 for ICRD-20902
			mailBagEnquirySession.setMailbagVOs(mailbagVOPage);
		}
		else {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.mailbagenquiry.msg.err.nomailbags");
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		//Added by A-7531 for CRQ ICRD-197238
		
		if(CONST_ASSIGNCONTAINER.equals(mailBagEnquiryForm.getFromScreen())|| CONST_MAILEXPORTLIST.equals(mailBagEnquiryForm.getFromScreen())
				||CONST_LISTCONTAINER.equals(mailBagEnquiryForm.getFromScreen()) || CONST_MAILHISTORY.equals(mailBagEnquiryForm.getFromScreen())
				){
		mailBagEnquiryForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		}else{
			mailBagEnquiryForm.setFromScreen(CONST_MAILBAGENQUIRY)	;
		}
		if("Y".equals(success)){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.returnmail"));
		}
    	invocationContext.target = TARGET_SUCCESS;        

    	log.exiting("ListCommand","execute");

    }
 

    /**
     * Method is used to set the filter values to the form when
     * screen invoked from Assign Container
     * @param mailBagEnquiryForm
     * @param logonAttributes
     * @param assignContainerSession
     */
    private void handleMailbagVOsFromAssign(
    					MailBagEnquiryForm mailBagEnquiryForm,
    					LogonAttributes logonAttributes,
    					AssignContainerSession assignContainerSession) {

    	log.entering("ListCommand","handleMailbagVOsFromAssignContainers");

    	Collection<ContainerVO> containerVOs =
    						assignContainerSession.getSelectedContainerVOs();
    	if (containerVOs != null) {
    		for (ContainerVO containerVO : containerVOs) {

    			log.log(Log.FINE,
						"ContainerVO frm Assign Container --------->>",
						containerVO);
				mailBagEnquiryForm.setCurrentStatus("ACP");
    			mailBagEnquiryForm.setContainerNo
    										(containerVO.getContainerNumber());
    			//Added as part of bug ICRD-154553 by A-5526 starts
    			if (mailBagEnquiryForm.getContainerNo() != null
						&& mailBagEnquiryForm.getContainerNo().length() > 3
						&& "OFL".equals(mailBagEnquiryForm.getContainerNo()
								.substring(0, 3))) {    
					mailBagEnquiryForm.setCurrentStatus("");           
				}
    			//Added as part of bug ICRD-154553 by A-5526 ends
    			mailBagEnquiryForm.setFlightCarrierCode
    											(containerVO.getCarrierCode());
    			if (!"-1".equals(containerVO.getFlightNumber())) {
    				mailBagEnquiryForm.setFlightNumber
    											(containerVO.getFlightNumber());
    				if (containerVO.getFlightDate() != null) {
        				mailBagEnquiryForm.setFlightDate
        					(containerVO.getFlightDate().toDisplayFormat
        									(LocalDate.CALENDAR_DATE_FORMAT));
        			}
    			}else{
    				mailBagEnquiryForm.setFlightNumber("");
    				mailBagEnquiryForm.setFlightDate("");
    			}

    			//mailBagEnquiryForm.setDisplayPage("1");
    			//mailBagEnquiryForm.setLastPageNum("0");
    			mailBagEnquiryForm.setFromDate("");
    			mailBagEnquiryForm.setToDate("");
    			mailBagEnquiryForm.setYear("");
    			mailBagEnquiryForm.setScreenStatusFlag
    				(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    			break;
    		}
    	}
    	log.exiting("ListCommand","handleMailbagVOsFromAssignContainers");
    }

  


    /**
     * Method is used to set the filter values to the form when
     * Closing from Offload screen
     * @param mailBagEnquiryForm
     * @param logonAttributes
     * @param mailBagEnquirySession
     */
    private void handleRefreshOnCloseFromOffload(
    		MailBagEnquiryForm mailBagEnquiryForm,
    		LogonAttributes logonAttributes,
    		MailBagEnquirySession mailBagEnquirySession) {

    	log.entering("ListCommand","handleRefreshOnCloseFromOffload");

    	MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
    	mailbagEnquiryFilterVO = mailBagEnquirySession.getMailbagEnquiryFilterVO();

    	mailBagEnquiryForm.setOriginOE(mailbagEnquiryFilterVO.getOoe());
    	mailBagEnquiryForm.setDestnOE(mailbagEnquiryFilterVO.getDoe());
    	mailBagEnquiryForm.setCategory(mailbagEnquiryFilterVO.getMailCategoryCode());
    	mailBagEnquiryForm.setSubClass(mailbagEnquiryFilterVO.getMailSubclass());
    	mailBagEnquiryForm.setYear(mailbagEnquiryFilterVO.getYear());
    	mailBagEnquiryForm.setDsn(mailbagEnquiryFilterVO.getDespatchSerialNumber());
    	mailBagEnquiryForm.setRsn(mailbagEnquiryFilterVO.getReceptacleSerialNumber());
    	//Added for ICRD-133967 starts
    	mailBagEnquiryForm.setConsigmentNumber(mailbagEnquiryFilterVO.getConsigmentNumber());
    	mailBagEnquiryForm.setUpuCode(mailbagEnquiryFilterVO.getUpuCode());
    	//Added for ICRD-133967 ends
    	mailBagEnquiryForm.setMailbagId(mailbagEnquiryFilterVO.getMailbagId());//Added for ICRD-205027
		//Added for ICRD-214795 starts
		   if(mailbagEnquiryFilterVO.getReqDeliveryTime()!=null){
	    	mailBagEnquiryForm.setReqDeliveryDate(mailbagEnquiryFilterVO.getReqDeliveryTime()
	    			.toDisplayDateOnlyFormat());
	    	mailBagEnquiryForm.setReqDeliveryTime(mailbagEnquiryFilterVO.getReqDeliveryTime()
	    			.toDisplayTimeOnlyFormat());
		   }
	    	//Added for ICRD-214795 ends
    	mailBagEnquiryForm.setCurrentStatus(mailbagEnquiryFilterVO.getCurrentStatus());
    	mailBagEnquiryForm.setPort(mailbagEnquiryFilterVO.getScanPort());
    	if(mailbagEnquiryFilterVO.getScanFromDate()!=null){
    	mailBagEnquiryForm.setFromDate(mailbagEnquiryFilterVO.getScanFromDate().toDisplayDateOnlyFormat());
    	}
    	else{
    	mailBagEnquiryForm.setFromDate("");
    	}
    	if(mailbagEnquiryFilterVO.getScanToDate()!=null){
    	mailBagEnquiryForm.setToDate(mailbagEnquiryFilterVO.getScanToDate().toDisplayDateOnlyFormat());
    	}
    	else{
    		mailBagEnquiryForm.setToDate("");
    	}
    	mailBagEnquiryForm.setUserId(mailbagEnquiryFilterVO.getScanUser());
    	mailBagEnquiryForm.setContainerNo(mailbagEnquiryFilterVO.getContainerNumber());
    	mailBagEnquiryForm.setFlightCarrierCode(mailbagEnquiryFilterVO.getCarrierCode());
    	if(mailbagEnquiryFilterVO.getFlightNumber()==null){
    		mailBagEnquiryForm.setFlightNumber("");
    	}else{
    	mailBagEnquiryForm.setFlightNumber(mailbagEnquiryFilterVO.getFlightNumber());
    	}
    	if(mailbagEnquiryFilterVO.getFlightDate()!=null){
    	mailBagEnquiryForm.setFlightDate(mailbagEnquiryFilterVO.getFlightDate().toDisplayDateOnlyFormat());
    	}
    	else{
    		mailBagEnquiryForm.setFlightDate("");
    	}
    	if(("Y").equalsIgnoreCase(mailbagEnquiryFilterVO.getDamageFlag()))
    	{
    		mailBagEnquiryForm.setDamaged(true);
    	}
    	else if(("N").equalsIgnoreCase(mailbagEnquiryFilterVO.getDamageFlag()))
    	{
    		mailBagEnquiryForm.setDamaged(false);
    	}
       	mailBagEnquiryForm.setTransit(mailbagEnquiryFilterVO.getTransitFlag());
    	mailBagEnquiryForm.setCarditStatus(mailbagEnquiryFilterVO.getCarditPresent());
    	mailBagEnquiryForm.setFromCarrier(mailbagEnquiryFilterVO.getFromCarrier());
    	//Modified as part of CRQ ICRD-197238 by A-7531
		mailBagEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		mailBagEnquiryForm.setFromScreen("");       
		log.exiting("handleRefreshOnCloseFromOffload","execute");

    }

    /**
     * Method is used to set the filter values to the form when
     * screen invoked from MailExportLIst
     * @param mailBagEnquiryForm
     * @param logonAttributes
     * @param mailExportListSession
     */
    private void handleMailbagVOsFromExportList(
    		MailBagEnquiryForm mailBagEnquiryForm,
    		LogonAttributes logonAttributes,
    		MailExportListSession mailExportListSession) {

    	log.entering("ListCommand","handleMailbagVOsFromExportList");

    	Collection<DSNVO> dsnVOs = mailExportListSession.getDSNVOs();
    	MailbagEnquiryFilterVO mailbagEnquiryFilterVO=mailExportListSession.getMailbagEnquiryFilterVO();
    	if (dsnVOs != null) {
    		for (DSNVO selectedvo : dsnVOs) {

    			log.log(Log.FINE, "Selectedvo frm MailExportLIst --------->>",
						selectedvo);
				mailBagEnquiryForm.setOriginOE(mailbagEnquiryFilterVO.getOoe());
    			mailBagEnquiryForm.setDestnOE(mailbagEnquiryFilterVO.getDoe());
    			mailBagEnquiryForm.setCategory(mailbagEnquiryFilterVO.getMailCategoryCode());
    			mailBagEnquiryForm.setYear(String.valueOf(mailbagEnquiryFilterVO.getYear()));
    			mailBagEnquiryForm.setDsn(mailbagEnquiryFilterVO.getDespatchSerialNumber());
    			mailBagEnquiryForm.setMailbagId(mailbagEnquiryFilterVO.getMailbagId());//Added for ICRD-205027
    			//Added for ICRD-214795 starts
    			   if(mailbagEnquiryFilterVO.getReqDeliveryTime()!=null){
    		    	mailBagEnquiryForm.setReqDeliveryDate(mailbagEnquiryFilterVO.getReqDeliveryTime()
    		    			.toDisplayDateOnlyFormat());
    		    	mailBagEnquiryForm.setReqDeliveryTime(mailbagEnquiryFilterVO.getReqDeliveryTime()
    		    			.toDisplayTimeOnlyFormat());
    			   }
    		    	//Added for ICRD-214795 ends
    			//mailBagEnquiryForm.setCurrentStatus("ACP");
    			mailBagEnquiryForm.setContainerNo(mailbagEnquiryFilterVO.getContainerNumber());
    			mailBagEnquiryForm.setFlightCarrierCode(mailbagEnquiryFilterVO.getCarrierCode());
    			if (!"-1".equals(mailbagEnquiryFilterVO.getFlightNumber())) {
    				mailBagEnquiryForm.setFlightNumber
    											(mailbagEnquiryFilterVO.getFlightNumber());
    				if (mailbagEnquiryFilterVO.getFlightDate() != null) {
        				mailBagEnquiryForm.setFlightDate
        					(mailbagEnquiryFilterVO.getFlightDate().toDisplayFormat
        									(LocalDate.CALENDAR_DATE_FORMAT));
        			}
    			}else if("-1".equals(mailbagEnquiryFilterVO.getFlightNumber())) {
    				mailBagEnquiryForm.setFlightNumber(null);
    			}
    			mailBagEnquiryForm.setPort(mailbagEnquiryFilterVO.getScanPort());
    			mailBagEnquiryForm.setFromDate("");
    			mailBagEnquiryForm.setToDate("");
    			mailBagEnquiryForm.setScreenStatusFlag
    				(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    			break;
    		}
    	}
    	log.exiting("ListCommand","handleMailbagVOsFromExportList");
    }

    /**
     * This method is used to convert a string to upper case if
     * it is not null
	 * @param input
	 * @return String
	 */
	private String upper(String input){//to convert sting to uppercase

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return BLANKSPACE;
		}
	}
	/**
	 * This method is used for validating the form for the particular action
	 * @param mailBagEnquiryForm - MailBagEnquiryForm
	 * @return errors - Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			MailBagEnquiryForm mailBagEnquiryForm) {

		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();

		String fromscreen = mailBagEnquiryForm.getFromScreen();
		//Removed the code to handle from Inventory screen as well as from DSN enquiry screen as part of CRQ ICRD-197238 by A-7531 as this has no relevance n
		if (!CONST_ASSIGNCONTAINER.equals(fromscreen)
									&&
									!CONST_MAILEXPORTLIST.equals(fromscreen)
									&&
									!CONST_OFFLOAD.equals(fromscreen)
									&&
									!CONST_LISTCONTAINER.equals(fromscreen)) {
			String port = mailBagEnquiryForm.getPort();
			String fromDate = mailBagEnquiryForm.getFromDate();
			String toDate = mailBagEnquiryForm.getToDate();
			
			//Modified by A-7794 as part of ICRD-197479
			String dsn = mailBagEnquiryForm.getDsn();
			String carrierCode = mailBagEnquiryForm.getFlightCarrierCode();
			String flightNumber = mailBagEnquiryForm.getFlightNumber();
			String flightDate = mailBagEnquiryForm.getFlightDate();
			
			if (((BLANKSPACE).equals(port) && port != null ) &&
					((BLANKSPACE).equals(dsn) && dsn != null ) &&
					((BLANKSPACE).equals(carrierCode)
							|| (BLANKSPACE).equals(flightNumber)
							|| (BLANKSPACE).equals(flightDate)) ) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.mailbagenquiry.portordsnorflightdetailsmandatory");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
			
			if (("").equals(fromDate)&& fromDate != null) {
				ErrorVO errorVO = new ErrorVO(
				"mailtracking.defaults.mailbagenquiry.msg.err.noFromDate");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
			if (("").equals(toDate) && toDate != null) {
				ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.mailbagenquiry.msg.err.noToDate");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
			if(formErrors== null || formErrors.size()== 0){
				if(fromDate!=null && toDate!= null){
				if (!fromDate.equals(toDate)) {
					if (!DateUtilities.isLessThan(fromDate, toDate,"dd-MMM-yyyy")) {
				    	log.log(Log.FINE, "FROM DATE GRTR THAN TO DATE=========>");
				    	ErrorVO errorVO = new ErrorVO(
				    	"mailtracking.defaults.mailbagenquiry.msg.err.fromgrtrtodat");
				    	formErrors.add(errorVO);
				    }
				    }
				}
			}
		}

		String status = mailBagEnquiryForm.getCurrentStatus();
		if (FLAG_ACCEPTED.equals(status)) {
			if (mailBagEnquiryForm.getFlightNumber()!=null && !(("").equals(mailBagEnquiryForm.getFlightNumber()))){
				if (("").equals(mailBagEnquiryForm.getFlightCarrierCode())
						|| ("").equals(mailBagEnquiryForm.getFlightNumber())
						|| ("").equals(mailBagEnquiryForm.getFlightDate())) {

					ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.mailbagenquiry.msg.err.noFlightDetails");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					formErrors.add(errorVO);

				}
			}
		}

		return formErrors;
	}


	

	//Added by A-5201 for ICRD-20902 starts
	   private void populateForm(MailbagEnquiryFilterVO mailbagEnquiryFilterVO,MailBagEnquiryForm mailBagEnquiryForm){
		   mailBagEnquiryForm.setCurrentStatus(mailbagEnquiryFilterVO.getCurrentStatus());
		   mailBagEnquiryForm.setPort(mailbagEnquiryFilterVO.getScanPort());
		   if(mailbagEnquiryFilterVO.getScanFromDate() != null){
			   mailBagEnquiryForm.setFromDate(mailbagEnquiryFilterVO.getScanFromDate().toDisplayDateOnlyFormat());
		   }
		   if(mailbagEnquiryFilterVO.getScanToDate() != null){
			   mailBagEnquiryForm.setToDate(mailbagEnquiryFilterVO.getScanToDate().toDisplayDateOnlyFormat());
		   }
		   if(CONST_LISTCONTAINER.equals(mailBagEnquiryForm.getFromScreen())){
			   mailBagEnquiryForm.setFromDate(null);
			   mailBagEnquiryForm.setToDate(null);
		   }
		   mailBagEnquiryForm.setUserId(mailbagEnquiryFilterVO.getScanUser());
		   mailBagEnquiryForm.setContainerNo(mailbagEnquiryFilterVO.getContainerNumber());
		   mailBagEnquiryForm.setFlightNumber(mailbagEnquiryFilterVO.getFlightNumber());
		   mailBagEnquiryForm.setFlightCarrierCode(mailbagEnquiryFilterVO.getCarrierCode());
		   //Added by A-6245 for ICRD-92913 starts
		   if(mailbagEnquiryFilterVO.getDespatchSerialNumber() != null &&
				   mailbagEnquiryFilterVO.getDespatchSerialNumber().trim().length() > 0){
		   mailBagEnquiryForm.setDsn(mailbagEnquiryFilterVO.getDespatchSerialNumber());
		   }
		   if(mailbagEnquiryFilterVO.getReceptacleSerialNumber() != null &&
				   mailbagEnquiryFilterVO.getReceptacleSerialNumber().trim().length() > 0){
		   mailBagEnquiryForm.setRsn(mailbagEnquiryFilterVO.getReceptacleSerialNumber());
		   }
		   if(mailbagEnquiryFilterVO.getYear() != null &&
				   mailbagEnquiryFilterVO.getYear().trim().length() > 0){
		   mailBagEnquiryForm.setYear(mailbagEnquiryFilterVO.getYear());
		   }
		   if(mailbagEnquiryFilterVO.getMailSubclass() != null &&
				   mailbagEnquiryFilterVO.getMailSubclass().trim().length() > 0){
		   mailBagEnquiryForm.setSubClass(mailbagEnquiryFilterVO.getMailSubclass());
		   }
		   if(mailbagEnquiryFilterVO.getMailCategoryCode() != null &&
				   mailbagEnquiryFilterVO.getMailCategoryCode().trim().length() > 0){
		   mailBagEnquiryForm.setCategory(mailbagEnquiryFilterVO.getMailCategoryCode());
		   }
		   if(mailbagEnquiryFilterVO.getDoe() != null &&
				   mailbagEnquiryFilterVO.getDoe().trim().length() > 0){
		   mailBagEnquiryForm.setDestnOE(mailbagEnquiryFilterVO.getDoe());
		   }
		   if(mailbagEnquiryFilterVO.getOoe() != null &&
				   mailbagEnquiryFilterVO.getOoe().trim().length() > 0){
		   mailBagEnquiryForm.setOriginOE(mailbagEnquiryFilterVO.getOoe());
		   }
		   //Added by A-6245 for ICRD-92913 ends
		   if(mailbagEnquiryFilterVO.getFlightDate() != null){
			   mailBagEnquiryForm.setFlightDate(mailbagEnquiryFilterVO.getFlightDate().toDisplayDateOnlyFormat());
		   }	   
		   //Added for ICRD-133967 starts
		   if(mailbagEnquiryFilterVO.getConsigmentNumber() != null &&
				   mailbagEnquiryFilterVO.getConsigmentNumber().trim().length() > 0){
		   mailBagEnquiryForm.setConsigmentNumber(mailbagEnquiryFilterVO.getConsigmentNumber());
		   }
		   if(mailbagEnquiryFilterVO.getUpuCode() != null &&
				   mailbagEnquiryFilterVO.getUpuCode().trim().length() > 0){
		   mailBagEnquiryForm.setUpuCode(mailbagEnquiryFilterVO.getUpuCode());
		   }
		   //Added for ICRD-133967 ends
		   //Added for ICRD-205027 starts
		   if(mailbagEnquiryFilterVO.getMailbagId() != null &&
				   mailbagEnquiryFilterVO.getMailbagId().trim().length() > 0){
		   mailBagEnquiryForm.setMailbagId(mailbagEnquiryFilterVO.getMailbagId());
		   }else{
			   mailBagEnquiryForm.setMailbagId("");
		   }
		   //Added for ICRD-205027 ends
			//Added for ICRD-214795 starts
		   if(mailbagEnquiryFilterVO.getReqDeliveryTime()!=null){
	    	mailBagEnquiryForm.setReqDeliveryDate(mailbagEnquiryFilterVO.getReqDeliveryTime()
	    			.toDisplayDateOnlyFormat());
	    	if(!"00:00:00".equals(mailbagEnquiryFilterVO.getReqDeliveryTime()
	    			.toDisplayTimeOnlyFormat())){
	    	mailBagEnquiryForm.setReqDeliveryTime(mailbagEnquiryFilterVO.getReqDeliveryTime()
	    			.toDisplayTimeOnlyFormat(true));
	    	}
		   }
	    	//Added for ICRD-214795 ends
		}
	 //Added by A-5201 for ICRD-20902 end
	   
	   private void getOnetimeValues(MailBagEnquirySession mailBagEnquirySession,
			   LogonAttributes logonAttributes){
		    Collection<String> fieldTypes = new ArrayList<String>();		
		    Collection<ErrorVO> errors = null;
			fieldTypes.add(CURRENTSTATUS);
			fieldTypes.add(CATEGORY);
			fieldTypes.add(CONTAINERTYPE);
			fieldTypes.add(OPERATIONTYPE);
			Map<String,Collection<OneTimeVO>> oneTimeData = new HashMap<String,Collection<OneTimeVO>>();
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();		
			try { 		
    		
    		oneTimeData = sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					fieldTypes);  
    		
    	}catch (BusinessDelegateException businessDelegateException) {
    		errors = handleDelegateException(businessDelegateException);
		}
	   if (oneTimeData != null) {			
			Collection<OneTimeVO> status = 
				oneTimeData.get(CURRENTSTATUS);	
			Collection<OneTimeVO> category = 
				oneTimeData.get(CATEGORY);	
			Collection<OneTimeVO> containerType = 
				oneTimeData.get(CONTAINERTYPE);	
			Collection<OneTimeVO> operationType = 
				oneTimeData.get(OPERATIONTYPE);	
			
			Collection<OneTimeVO> curStatus = new ArrayList<OneTimeVO>();
			if(status != null && status.size() > 0){
				for(OneTimeVO oneTimeVO:status){
					if(!oneTimeVO.getFieldDescription().contains("Resdit")
							&& !"CDT".equals(oneTimeVO.getFieldValue())
							&& !"MFT".equals(oneTimeVO.getFieldValue())){
						curStatus.add(oneTimeVO);
					}
				}
			}
			
			mailBagEnquirySession.setCurrentStatus(curStatus);
			mailBagEnquirySession.setMailCategory(category);
			mailBagEnquirySession.setContainerTypes(containerType);
			mailBagEnquirySession.setOperationTypes(operationType); 
		}
	   }
	   

}
