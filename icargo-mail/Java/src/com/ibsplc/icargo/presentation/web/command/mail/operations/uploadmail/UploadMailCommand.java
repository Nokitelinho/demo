/*
 * UploadMailCommand.java Created on Aug 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;




import java.util.ArrayList;
import java.util.Collection;

import java.util.Map;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1740
 *
 */
public class UploadMailCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";	
	
	private static final String TARGET = "success";
	private static final String REASONCODE = "mailtracking.defaults.return.reasoncode";
	private static final String PREASSIGNMENT_SYS = "mailtracking.defaults.acceptance.preassignmentneeded";	
	private static final String HYPHEN = "-";	
	private static final int  WEIGHT_DIVISION_FACTOR = 10;
	private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";
	
		
	/** 
	 * The execute method for ScreenLoadCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("UploadCommand","execute");
	}	
/*	log.entering("UploadCommand","execute");
		UploadMailForm uploadMailForm 
			= (UploadMailForm)invocationContext.screenModel;
		UploadMailSession uploadMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String commodityCode = "";
	
		Collection<String> codes = new ArrayList<String>();
    	codes.add(PREASSIGNMENT_SYS);
    	codes.add(MAIL_COMMODITY_SYS);
    	Map<String, String> results = null;
    	try {
    		results = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
    	} catch(BusinessDelegateException businessDelegateException) {
    		handleDelegateException(businessDelegateException);
    	}
    	if(results != null && results.size() > 0) {
    		uploadMailForm.setPreassignFlag(results.get(PREASSIGNMENT_SYS));
    		commodityCode = results.get(MAIL_COMMODITY_SYS);
    	}
    	
    	
		String cmpcod = logonAttributes.getCompanyCode();
		Collection<String> commodites = new ArrayList<String>();
		if(commodityCode!=null && commodityCode.trim().length()>0) {
			commodites.add(commodityCode);
			Map<String,CommodityValidationVO> densityMap = null;
			CommodityDelegate  commodityDelegate = new CommodityDelegate();

			try {
				densityMap = commodityDelegate.validateCommodityCodes(cmpcod, commodites);
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}

			if(densityMap !=null && densityMap.size()>0){
				CommodityValidationVO commodityValidationVO = densityMap.get(commodityCode);
				log.log(Log.FINE, "DENSITY:::::::::", commodityValidationVO.getDensityFactor());
				uploadMailForm.setDensityFactor(String.valueOf(commodityValidationVO.getDensityFactor()));
			}
		}
    	

       Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
       Collection<OneTimeVO> catVOs = new ArrayList<OneTimeVO>();
       Collection<OneTimeVO> hniVOs = new ArrayList<OneTimeVO>();
       Collection<OneTimeVO> riVOs = new ArrayList<OneTimeVO>();
	   if(oneTimes!=null){
		   catVOs = oneTimes.get("mailtracking.defaults.mailcategory");
		   hniVOs = oneTimes.get("mailtracking.defaults.highestnumbermail");
		   riVOs = oneTimes.get("mailtracking.defaults.registeredorinsuredcode");
		   uploadMailSession.setOneTimeCat(catVOs);
		   uploadMailSession.setOneTimeHNI(hniVOs);
		   uploadMailSession.setOneTimeRI(riVOs);
	   }
		
		
		
		ScannedDetailsVO  scannedDetailsVO = new ScannedDetailsVO();
		
		scannedDetailsVO.setPreassignFlag(results.get(PREASSIGNMENT_SYS));
		
		Collection<MailTrackingDefaultsBatchVO> mailBatchVOs = 
					getApplicationSession().getBatchTransferData();
		log.log(Log.INFO, " uploaded data ..\n", mailBatchVOs);
		//added by anitha-START
		Collection<PostalAdministrationVO> postalAdministrationVOs = null;
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		String countryCode = null;
		
		AreaDelegate areaDelegate = new AreaDelegate();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		
		try {
			airportValidationVO = areaDelegate.validateAirportCode(
    				logonAttributes.getCompanyCode(),
    				logonAttributes.getStationCode()); 
			
			countryCode = airportValidationVO.getCountryCode();
			
			if (countryCode != null) {
				postalAdministrationVOs = mailTrackingDefaultsDelegate.findLocalPAs(
						logonAttributes.getCompanyCode(),
						countryCode);
			}				
			
		}catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		
		//added by anitha-END
		
		String current = "";
		int flagAccept = 0;
		String accept = "";
		String reassigncurrent = "";
		int flagReassign = 0;
		String reassign = "";
		String transfercurrent = "";
		int flagTransfer = 0;
		String transfer = "";
		String despatchcurrent = "";
		int flagDespatch = 0;
		String despatch = "";
		
		MailTrackingDefaultsBatchVO mailTrackingDefaultsBatchVO = null;
		
		ScannedMailDetailsVO scannedOutboundVO = new ScannedMailDetailsVO();
		ScannedMailDetailsVO scannedArrivedVO = new ScannedMailDetailsVO();
		ScannedMailDetailsVO scannedReturnVO = new ScannedMailDetailsVO();
		ScannedMailDetailsVO scannedTransferVO = new ScannedMailDetailsVO();
		ScannedMailDetailsVO scannedOffloadVO = new ScannedMailDetailsVO();
		ScannedMailDetailsVO scannedReassignmailVO = new ScannedMailDetailsVO();
		ScannedMailDetailsVO scannedReassigndespatchVO = new ScannedMailDetailsVO();
		
		Collection<MailbagVO> mailbagAcceptVOs = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagArriveVOs = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagReturnVOs = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagTransferVOs = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagOffloadVOs = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagReassignmailVOs = new ArrayList<MailbagVO>();
		Collection<DespatchDetailsVO> despatchReassignVOs = new ArrayList<DespatchDetailsVO>();
		
		Collection<ScannedMailDetailsVO> scannedOutboundVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<ScannedMailDetailsVO> scannedArrivedVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<ScannedMailDetailsVO> scannedReturnVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<ScannedMailDetailsVO> scannedTransferVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<ScannedMailDetailsVO> scannedOffloadVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<ScannedMailDetailsVO> scannedReassignmailVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<ScannedMailDetailsVO> scannedReassigndespatchesVOs = new ArrayList<ScannedMailDetailsVO>();

		if(mailBatchVOs != null && mailBatchVOs.size() > 0){
			for(MailTrackingDefaultsBatchVO mailBatchVO:mailBatchVOs){
				   if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailBatchVO.getOperation())){
					double vol = 0.0;
					if(	!current.equals(mailBatchVO.getContainer())){
						current = mailBatchVO.getContainer();
						mailTrackingDefaultsBatchVO = mailBatchVO;
						if(flagAccept == 1){
							scannedOutboundVO.setMailDetails(mailbagAcceptVOs);
							scannedOutboundVOs.add(scannedOutboundVO);
							accept = "Y";
						}
						scannedOutboundVO = new ScannedMailDetailsVO();
						scannedOutboundVO.setContainerNumber(mailBatchVO.getContainer());
						scannedOutboundVO.setContainerType(mailBatchVO.getContainerType());
						scannedOutboundVO.setCompanyCode(logonAttributes.getCompanyCode());
						scannedOutboundVO.setPol(logonAttributes.getAirportCode());
						if(mailBatchVO.getCarrierCode()!= null && mailBatchVO.getCarrierCode().length() >0 ){
							scannedOutboundVO.setCarrierCode(mailBatchVO.getCarrierCode());
						}else{
							scannedOutboundVO.setCarrierCode(mailBatchVO.getFlightCarrierCode());
						}
						scannedOutboundVO.setFlightNumber(mailBatchVO.getFlightNumber());
						if(mailBatchVO.getFlightDate() != null
								&& !"".equals(mailBatchVO.getFlightDate())){
							scannedOutboundVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(mailBatchVO.getFlightDate()));	
						}
						scannedOutboundVO.setPou(mailBatchVO.getPou());
						scannedOutboundVO.setDestination(mailBatchVO.getDestination());
						mailbagAcceptVOs = new ArrayList<MailbagVO>();
						flagAccept = 1;
						accept = "N";
					}else if(compareMailBatchVO(mailTrackingDefaultsBatchVO,mailBatchVO)){
						mailTrackingDefaultsBatchVO = mailBatchVO;
						if(flagAccept == 1){
							scannedOutboundVO.setMailDetails(mailbagAcceptVOs);
							scannedOutboundVOs.add(scannedOutboundVO);
							accept = "Y";
						}
						scannedOutboundVO = new ScannedMailDetailsVO();
						scannedOutboundVO.setContainerNumber(mailBatchVO.getContainer());
						scannedOutboundVO.setContainerType(mailBatchVO.getContainerType());
						scannedOutboundVO.setCompanyCode(logonAttributes.getCompanyCode());
						scannedOutboundVO.setPol(logonAttributes.getAirportCode());
						if(mailBatchVO.getCarrierCode()!= null && mailBatchVO.getCarrierCode().length() >0 ){
							scannedOutboundVO.setCarrierCode(mailBatchVO.getCarrierCode());
						}else{
							scannedOutboundVO.setCarrierCode(mailBatchVO.getFlightCarrierCode());
						}
						scannedOutboundVO.setFlightNumber(mailBatchVO.getFlightNumber());
						if(mailBatchVO.getFlightDate() != null
								&& !"".equals(mailBatchVO.getFlightDate())){
							scannedOutboundVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(mailBatchVO.getFlightDate()));	
						}
						scannedOutboundVO.setPou(mailBatchVO.getPou());
						scannedOutboundVO.setDestination(mailBatchVO.getDestination());
						mailbagAcceptVOs = new ArrayList<MailbagVO>();
						flagAccept = 1;
						accept = "N";
					}
					
					MailbagVO mailbagVO = new MailbagVO();
					mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_INBOUND);
					mailbagVO.setMailbagId(mailBatchVO.getMailtag());
					mailbagVO.setOoe(mailBatchVO.getMailtag().substring(0,6));
					mailbagVO.setDoe(mailBatchVO.getMailtag().substring(6,12));
					mailbagVO.setMailCategoryCode(mailBatchVO.getMailtag().substring(12,13));
					mailbagVO.setMailSubclass(mailBatchVO.getMailtag().substring(13,15));
					mailbagVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
					mailbagVO.setYear(Integer.parseInt(mailBatchVO.getMailtag().substring(15,16)));
					mailbagVO.setDespatchSerialNumber(mailBatchVO.getMailtag().substring(16,20));
					mailbagVO.setReceptacleSerialNumber(mailBatchVO.getMailtag().substring(20,23));
					mailbagVO.setHighestNumberedReceptacle(mailBatchVO.getMailtag().substring(23,24));
					mailbagVO.setRegisteredOrInsuredIndicator(mailBatchVO.getMailtag().substring(24,25));
					mailbagVO.setWeight(Double.parseDouble(mailBatchVO.getMailtag().substring(25,29))/10);
					mailbagVO.setStrWeight(mailBatchVO.getMailtag().substring(25,29));
					vol =Double.parseDouble(mailbagVO.getStrWeight())/(WEIGHT_DIVISION_FACTOR * Double.parseDouble(uploadMailForm.getDensityFactor()));
					vol = Double.parseDouble(TextFormatter.formatDouble(vol , 2));
					if(MailConstantsVO.MINIMUM_VOLUME > vol) {
						vol = MailConstantsVO.MINIMUM_VOLUME;
					}
          			mailbagVO.setVolume(vol);
					mailbagVO.setScannedDate(new LocalDate(
							LocalDate.NO_STATION,Location.NONE,false).setDateAndTime(mailBatchVO.getScanDate()));
					//added by anitha
					mailbagVO.setOffloadedRemarks(mailBatchVO.getRemarks());
					mailbagVO.setActionMode(MailConstantsVO.MAIL_STATUS_ACCEPTED);
					mailbagVO.setPol(logonAttributes.getAirportCode());
					if(mailBatchVO.getDamageCode()!=null){
						Collection<OneTimeVO> dmgVOs = new ArrayList<OneTimeVO>();
						   if(oneTimes!=null){
							   dmgVOs = oneTimes.get("mailtracking.defaults.return.reasoncode");
						   }	
						mailbagVO.setOffloadedRemarks(mailBatchVO.getRemarks());
						DamagedMailbagVO damagedMailbagVO = new DamagedMailbagVO();					
						Collection<DamagedMailbagVO> damagedVOs = new ArrayList<DamagedMailbagVO>();
						damagedMailbagVO.setDamageCode(mailBatchVO.getDamageCode());
						 for(OneTimeVO dmgVO:dmgVOs){
							   if(dmgVO.getFieldValue().equals(mailBatchVO.getDamageCode())){
								   damagedMailbagVO.setDamageDescription(dmgVO.getFieldDescription());
							   }
						   }
						damagedMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
						damagedMailbagVO.setAirportCode(logonAttributes.getAirportCode());
						damagedMailbagVO.setDamageDate(new LocalDate(logonAttributes.getAirportCode(),ARP,true));
						damagedMailbagVO.setRemarks(mailBatchVO.getDamageRemarks());
						damagedVOs.add(damagedMailbagVO);
						mailbagVO.setDamagedMailbags(damagedVOs);
						}
					mailbagAcceptVOs.add(mailbagVO);
					
				}
				if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailBatchVO.getOperation())){
					
					MailbagVO mailbagVO = new MailbagVO();
					mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_INBOUND);
					mailbagVO.setMailbagId(mailBatchVO.getMailtag());
					mailbagVO.setOoe(mailBatchVO.getMailtag().substring(0,6));
					mailbagVO.setDoe(mailBatchVO.getMailtag().substring(6,12));
					mailbagVO.setMailCategoryCode(mailBatchVO.getMailtag().substring(12,13));
					mailbagVO.setMailSubclass(mailBatchVO.getMailtag().substring(13,15));
					mailbagVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
					mailbagVO.setYear(Integer.parseInt(mailBatchVO.getMailtag().substring(15,16)));
					mailbagVO.setDespatchSerialNumber(mailBatchVO.getMailtag().substring(16,20));
					mailbagVO.setReceptacleSerialNumber(mailBatchVO.getMailtag().substring(20,23));
					mailbagVO.setHighestNumberedReceptacle(mailBatchVO.getMailtag().substring(23,24));
					mailbagVO.setRegisteredOrInsuredIndicator(mailBatchVO.getMailtag().substring(24,25));
					mailbagVO.setWeight(Double.parseDouble(mailBatchVO.getMailtag().substring(25,29))/10);
					mailbagVO.setStrWeight(mailBatchVO.getMailtag().substring(25,29));
					mailbagVO.setScannedDate(new LocalDate(
							LocalDate.NO_STATION,Location.NONE,false).setDateAndTime(mailBatchVO.getScanDate()));
					mailbagVO.setContainerNumber(mailBatchVO.getContainer());
					//mailbagVO.setPol(mailBatchVO.getPol());
					mailbagVO.setPou(logonAttributes.getAirportCode());
					mailbagVO.setCarrierCode(mailBatchVO.getFlightCarrierCode());
					mailbagVO.setFlightNumber(mailBatchVO.getFlightNumber());
					mailbagVO.setActionMode(MailConstantsVO.MAIL_STATUS_ARRIVED);
					if(mailBatchVO.getFlightDate() != null
							&& !"".equals(mailBatchVO.getFlightDate())){
						mailbagVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(mailBatchVO.getFlightDate()));
					}
					
					//added by anitha-START
					
					mailbagVO.setOffloadedRemarks(mailBatchVO.getRemarks());
					mailbagVO.setIntact(mailBatchVO.getIntact());
					
					//added by anitha-END
					
					mailbagArriveVOs.add(mailbagVO);
				}
				if(MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailBatchVO.getOperation())){
					Collection<OneTimeVO> dmgVOs = new ArrayList<OneTimeVO>();
					   if(oneTimes!=null){
						   dmgVOs = oneTimes.get("mailtracking.defaults.return.reasoncode");
					   }	
						
					MailbagVO mailbagVO = new MailbagVO();
					mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailbagVO.setMailbagId(mailBatchVO.getMailtag());
					mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_INBOUND);
					mailbagVO.setOoe(mailBatchVO.getMailtag().substring(0,6));
					mailbagVO.setDoe(mailBatchVO.getMailtag().substring(6,12));
					mailbagVO.setMailCategoryCode(mailBatchVO.getMailtag().substring(12,13));
					mailbagVO.setMailSubclass(mailBatchVO.getMailtag().substring(13,15));
					mailbagVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
					mailbagVO.setYear(Integer.parseInt(mailBatchVO.getMailtag().substring(15,16)));
					mailbagVO.setDespatchSerialNumber(mailBatchVO.getMailtag().substring(16,20));
					mailbagVO.setReceptacleSerialNumber(mailBatchVO.getMailtag().substring(20,23));
					mailbagVO.setHighestNumberedReceptacle(mailBatchVO.getMailtag().substring(23,24));
					mailbagVO.setRegisteredOrInsuredIndicator(mailBatchVO.getMailtag().substring(24,25));
					mailbagVO.setWeight(Double.parseDouble(mailBatchVO.getMailtag().substring(25,29))/10);
					mailbagVO.setStrWeight(mailBatchVO.getMailtag().substring(25,29));
					mailbagVO.setScannedDate(new LocalDate(
							LocalDate.NO_STATION,Location.NONE,false).setDateAndTime(mailBatchVO.getScanDate()));
					mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailbagVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
					mailbagVO.setScannedPort(logonAttributes.getAirportCode());
					mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
					mailbagVO.setActionMode(MailConstantsVO.MAIL_STATUS_RETURNED);
					DamagedMailbagVO damagedMailbagVO = new DamagedMailbagVO();
					//added by anitha for setting description
					Collection<DamagedMailbagVO> damagedVOs = new ArrayList<DamagedMailbagVO>();
					damagedMailbagVO.setDamageCode(mailBatchVO.getReturnRemarks());
					 for(OneTimeVO dmgVO:dmgVOs){
						   if(dmgVO.getFieldValue().equals(mailBatchVO.getReturnRemarks())){
							   damagedMailbagVO.setDamageDescription(dmgVO.getFieldDescription());
						   }
					   }
					 
					damagedMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
					damagedMailbagVO.setAirportCode(logonAttributes.getAirportCode());
					damagedMailbagVO.setDamageDate(new LocalDate(logonAttributes.getAirportCode(),ARP,true));
					damagedMailbagVO.setReturnedFlag(MailConstantsVO.FLAG_YES);	
					damagedMailbagVO.setRemarks(mailBatchVO.getRemarks());
					damagedVOs.add(damagedMailbagVO);
					
					mailbagVO.setDamagedMailbags(damagedVOs);
					ArrayList<PostalAdministrationVO> postalList = (ArrayList<PostalAdministrationVO>)postalAdministrationVOs;
					mailbagVO.setPaCode(postalList.get(0).getPaCode());
					mailbagVO.setPaDescription(postalList.get(0).getPaName());
					
					mailbagReturnVOs.add(mailbagVO);
				}
							
				if(MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailBatchVO.getOperation())){
					
					Collection<OneTimeVO> offloadReasonsVOs = new ArrayList<OneTimeVO>();
					   if(oneTimes!=null){
						   offloadReasonsVOs = oneTimes.get("mailtracking.defaults.offload.reasoncode");
					   }	
				
					MailbagVO mailbagVO = new MailbagVO();
					mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailbagVO.setMailbagId(mailBatchVO.getMailtag());
					mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_INBOUND);
					mailbagVO.setOoe(mailBatchVO.getMailtag().substring(0,6));
					mailbagVO.setDoe(mailBatchVO.getMailtag().substring(6,12));
					mailbagVO.setMailCategoryCode(mailBatchVO.getMailtag().substring(12,13));
					mailbagVO.setMailSubclass(mailBatchVO.getMailtag().substring(13,15));
					mailbagVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
					mailbagVO.setYear(Integer.parseInt(mailBatchVO.getMailtag().substring(15,16)));
					mailbagVO.setDespatchSerialNumber(mailBatchVO.getMailtag().substring(16,20));
					mailbagVO.setReceptacleSerialNumber(mailBatchVO.getMailtag().substring(20,23));
					mailbagVO.setHighestNumberedReceptacle(mailBatchVO.getMailtag().substring(23,24));
					mailbagVO.setRegisteredOrInsuredIndicator(mailBatchVO.getMailtag().substring(24,25));
					mailbagVO.setWeight(Double.parseDouble(mailBatchVO.getMailtag().substring(25,29))/10);
					mailbagVO.setStrWeight(mailBatchVO.getMailtag().substring(25,29));
					mailbagVO.setScannedDate(new LocalDate(
							LocalDate.NO_STATION,Location.NONE,false).setDateAndTime(mailBatchVO.getScanDate()));
					mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailbagVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
					mailbagVO.setScannedPort(logonAttributes.getAirportCode());
					mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
					mailbagVO.setFlightNumber(mailBatchVO.getFlightNumber());
					mailbagVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(mailBatchVO.getFlightDate()));
					mailbagVO.setCarrierCode(mailBatchVO.getFlightCarrierCode());
					mailbagVO.setPol(logonAttributes.getAirportCode());		
					for(OneTimeVO offloadVO:offloadReasonsVOs){
						   if(offloadVO.getFieldValue().equals(mailBatchVO.getOffloadReason())){
							   mailbagVO.setOffloadedDescription(offloadVO.getFieldDescription());
							   
						   }
					   }
					mailbagVO.setActionMode(MailConstantsVO.MAIL_STATUS_OFFLOADED);
					mailbagVO.setOffloadedReason(mailBatchVO.getOffloadReason());
					mailbagVO.setOffloadedRemarks(mailBatchVO.getRemarks());
					mailbagOffloadVOs.add(mailbagVO);
				
				}
				
				if(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(mailBatchVO.getOperation())){
					
					if(!reassigncurrent.equals(mailBatchVO.getToContainer())){
						reassigncurrent = mailBatchVO.getToContainer();
						if(flagReassign == 1){
							scannedReassignmailVO.setMailDetails(mailbagReassignmailVOs);
							scannedReassignmailVOs.add(scannedReassignmailVO);
							reassign = "Y";
						}
						scannedReassignmailVO = new ScannedMailDetailsVO();
						scannedReassignmailVO.setContainerNumber(mailBatchVO.getToContainer());
						scannedReassignmailVO.setContainerType(mailBatchVO.getContainerType());
						scannedReassignmailVO.setCompanyCode(logonAttributes.getCompanyCode());
						scannedReassignmailVO.setPol(logonAttributes.getAirportCode());
						scannedReassignmailVO.setCarrierCode(mailBatchVO.getToFlightCarrierCode());
						scannedReassignmailVO.setFlightNumber(mailBatchVO.getToFlightNumber());
						if(mailBatchVO.getToFlightDate() != null
								&& !"".equals(mailBatchVO.getToFlightDate())){
							scannedReassignmailVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(mailBatchVO.getToFlightDate()));	
						}
						scannedReassignmailVO.setPou(mailBatchVO.getToPou());
						scannedReassignmailVO.setDestination(mailBatchVO.getDestination());
						mailbagReassignmailVOs = new ArrayList<MailbagVO>();
						flagReassign = 1;
						reassign = "N";
					}
					
			
					MailbagVO mailbagVO = new MailbagVO();
					mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailbagVO.setMailbagId(mailBatchVO.getMailtag());
					mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_INBOUND);
					mailbagVO.setOoe(mailBatchVO.getMailtag().substring(0,6));
					mailbagVO.setDoe(mailBatchVO.getMailtag().substring(6,12));
					mailbagVO.setMailCategoryCode(mailBatchVO.getMailtag().substring(12,13));
					mailbagVO.setMailSubclass(mailBatchVO.getMailtag().substring(13,15));
					mailbagVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
					mailbagVO.setYear(Integer.parseInt(mailBatchVO.getMailtag().substring(15,16)));
					mailbagVO.setDespatchSerialNumber(mailBatchVO.getMailtag().substring(16,20));
					mailbagVO.setReceptacleSerialNumber(mailBatchVO.getMailtag().substring(20,23));
					mailbagVO.setHighestNumberedReceptacle(mailBatchVO.getMailtag().substring(23,24));
					mailbagVO.setRegisteredOrInsuredIndicator(mailBatchVO.getMailtag().substring(24,25));
					mailbagVO.setWeight(Double.parseDouble(mailBatchVO.getMailtag().substring(25,29))/10);
					mailbagVO.setStrWeight(mailBatchVO.getMailtag().substring(25,29));
					mailbagVO.setScannedDate(new LocalDate(
							LocalDate.NO_STATION,Location.NONE,false).setDateAndTime(mailBatchVO.getScanDate()));
					mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailbagVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
					mailbagVO.setScannedPort(logonAttributes.getAirportCode());
					mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
					mailbagVO.setCarrierCode(mailBatchVO.getFlightCarrierCode());
					mailbagVO.setActionMode(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);
					mailbagVO.setFlightNumber(mailBatchVO.getFlightNumber());
					mailbagVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(mailBatchVO.getFlightDate()));
					mailbagVO.setContainerNumber(mailBatchVO.getContainer());
					mailbagVO.setContainerType(mailBatchVO.getContainerType());
					mailbagVO.setDestCountryDesc(mailBatchVO.getDestination());
					mailbagReassignmailVOs.add(mailbagVO);
				
				}
				
				if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailBatchVO.getOperation())){
					
					if(!transfercurrent.equals(mailBatchVO.getToContainer())){
						transfercurrent = mailBatchVO.getToContainer();
						if(flagTransfer == 1){
							scannedTransferVO.setMailDetails(mailbagTransferVOs);
							scannedTransferVOs.add(scannedTransferVO);
							transfer = "Y";
						}
						scannedTransferVO = new ScannedMailDetailsVO();
						scannedTransferVO.setContainerNumber(mailBatchVO.getToContainer());
						scannedTransferVO.setCompanyCode(logonAttributes.getCompanyCode());
						scannedTransferVO.setPou(logonAttributes.getAirportCode());
						if(mailBatchVO.getCarrierCode()!= null && mailBatchVO.getCarrierCode().length() >0 ){
							scannedTransferVO.setCarrierCode(mailBatchVO.getCarrierCode());
						}else{
							scannedTransferVO.setCarrierCode(mailBatchVO.getToFlightCarrierCode());
							scannedTransferVO.setFlightNumber(mailBatchVO.getToFlightNumber());
						}
											
						
						if(mailBatchVO.getToFlightDate() != null
								&& !"".equals(mailBatchVO.getToFlightDate())){
							scannedTransferVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(mailBatchVO.getToFlightDate()));	
						}
						mailbagTransferVOs = new ArrayList<MailbagVO>();
						flagTransfer = 1;
						transfer = "N";
					}
					
				
					MailbagVO mailbagVO = new MailbagVO();
					mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailbagVO.setActionMode(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
					mailbagVO.setMailbagId(mailBatchVO.getMailtag());
					mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_INBOUND);
					mailbagVO.setOoe(mailBatchVO.getMailtag().substring(0,6));
					mailbagVO.setDoe(mailBatchVO.getMailtag().substring(6,12));
					mailbagVO.setMailCategoryCode(mailBatchVO.getMailtag().substring(12,13));
					mailbagVO.setMailSubclass(mailBatchVO.getMailtag().substring(13,15));
					mailbagVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
					mailbagVO.setYear(Integer.parseInt(mailBatchVO.getMailtag().substring(15,16)));
					mailbagVO.setDespatchSerialNumber(mailBatchVO.getMailtag().substring(16,20));
					mailbagVO.setReceptacleSerialNumber(mailBatchVO.getMailtag().substring(20,23));
					mailbagVO.setHighestNumberedReceptacle(mailBatchVO.getMailtag().substring(23,24));
					mailbagVO.setRegisteredOrInsuredIndicator(mailBatchVO.getMailtag().substring(24,25));
					mailbagVO.setWeight(Double.parseDouble(mailBatchVO.getMailtag().substring(25,29))/10);
					mailbagVO.setStrWeight(mailBatchVO.getMailtag().substring(25,29));
					mailbagVO.setScannedDate(new LocalDate(
							LocalDate.NO_STATION,Location.NONE,false).setDateAndTime(mailBatchVO.getScanDate()));
					mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailbagVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
					mailbagVO.setScannedPort(logonAttributes.getAirportCode());
					mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
					if(mailBatchVO.getFlightCarrierCode() != null && mailBatchVO.getFlightCarrierCode().length() >0){
						mailbagVO.setCarrierCode(mailBatchVO.getFlightCarrierCode());
					}else{
						mailbagVO.setCarrierCode(mailBatchVO.getCarrierCode());
					}
					mailbagVO.setFlightNumber(mailBatchVO.getFlightNumber());
					mailbagVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(mailBatchVO.getFlightDate()));
					mailbagVO.setPou(logonAttributes.getAirportCode());
					mailbagVO.setContainerNumber(mailBatchVO.getContainer());
					mailbagVO.setOffloadedRemarks(mailBatchVO.getRemarks());
					mailbagTransferVOs.add(mailbagVO);
				
				}
				
				if(MailConstantsVO.MAIL_STATUS_REASSIGNDESPATCH.equals(mailBatchVO.getOperation())){
					
					if(!despatchcurrent.equals(mailBatchVO.getToContainer())){
						despatchcurrent = mailBatchVO.getToContainer();
						if(flagDespatch == 1){
							scannedReassigndespatchVO.setDespatchDetails(despatchReassignVOs);
							scannedReassigndespatchesVOs.add(scannedReassigndespatchVO);
							despatch = "Y";
						}
						scannedReassigndespatchVO = new ScannedMailDetailsVO();
						scannedReassigndespatchVO.setContainerNumber(mailBatchVO.getToContainer());
						scannedReassigndespatchVO.setContainerType(mailBatchVO.getContainerType());
						scannedReassigndespatchVO.setCompanyCode(logonAttributes.getCompanyCode());
						scannedReassigndespatchVO.setCarrierCode(mailBatchVO.getToFlightCarrierCode());
						scannedReassigndespatchVO.setFlightNumber(mailBatchVO.getToFlightNumber());
						if(mailBatchVO.getToFlightDate() != null
								&& !"".equals(mailBatchVO.getToFlightDate())){
							scannedReassigndespatchVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(mailBatchVO.getToFlightDate()));	
						}
						scannedReassigndespatchVO.setPou(mailBatchVO.getToPou());
						scannedReassigndespatchVO.setPol(logonAttributes.getAirportCode());
						despatchReassignVOs = new ArrayList<DespatchDetailsVO>();
						flagDespatch = 1;
						despatch = "N";
					}
					
			
					DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
					if(mailBatchVO.getFlightCarrierCode() != null && mailBatchVO.getFlightCarrierCode().length() >0){
						despatchDetailsVO.setCarrierCode(mailBatchVO.getFlightCarrierCode());
						despatchDetailsVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(mailBatchVO.getFlightDate()));
						despatchDetailsVO.setFlightNumber(mailBatchVO.getFlightNumber());
					}else{
						despatchDetailsVO.setCarrierCode(mailBatchVO.getCarrierCode());
						despatchDetailsVO.setDestination(mailBatchVO.getDestination());
					}
					despatchDetailsVO.setPou(mailBatchVO.getPou());
					despatchDetailsVO.setOriginOfficeOfExchange(mailBatchVO.getOriginoe());
					despatchDetailsVO.setDestinationOfficeOfExchange(mailBatchVO.getDestinationoe());
					despatchDetailsVO.setMailCategoryCode(mailBatchVO.getCategory());
					despatchDetailsVO.setMailSubclass(mailBatchVO.getSubClass());
					despatchDetailsVO.setMailClass(despatchDetailsVO.getMailSubclass().substring(0,1));
					despatchDetailsVO.setYear(Integer.parseInt(mailBatchVO.getYear()));
					despatchDetailsVO.setDsn(mailBatchVO.getDsn());
					despatchDetailsVO.setConsignmentNumber(mailBatchVO.getConsignment());
					despatchDetailsVO.setConsignmentDate(new LocalDate(
							LocalDate.NO_STATION,Location.NONE,false).setDateAndTime(mailBatchVO.getScanDate()));
					despatchDetailsVO.setPaCode(mailBatchVO.getPostalCode());
					despatchDetailsVO.setStatedBags(0);
					despatchDetailsVO.setStatedWeight(0);
					despatchDetailsVO.setAcceptedBags(Integer.parseInt(mailBatchVO.getBags()));
					despatchDetailsVO.setAcceptedWeight(Double.parseDouble(mailBatchVO.getWeights()));
					despatchDetailsVO.setContainerNumber(mailBatchVO.getContainer());
					despatchDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
					despatchReassignVOs.add(despatchDetailsVO);
			}

				
			}
			if(mailbagArriveVOs != null && mailbagArriveVOs.size() > 0){
				scannedArrivedVO.setMailDetails(mailbagArriveVOs);
				scannedArrivedVOs.add(scannedArrivedVO);
			}
			if(mailbagReturnVOs != null && mailbagReturnVOs.size() > 0){
				scannedReturnVO.setMailDetails(mailbagReturnVOs);
				scannedReturnVOs.add(scannedReturnVO);
			}
			if(mailbagOffloadVOs != null && mailbagOffloadVOs.size() > 0){
				scannedOffloadVO.setMailDetails(mailbagOffloadVOs);
				scannedOffloadVOs.add(scannedOffloadVO);
			}
			
			
		}
		
		if("N".equals(accept)){
			scannedOutboundVO.setMailDetails(mailbagAcceptVOs);
			scannedOutboundVOs.add(scannedOutboundVO);
		}
		
		if(scannedOutboundVOs != null && scannedOutboundVOs.size() > 0){
			scannedDetailsVO.setOutboundMails(scannedOutboundVOs);
		}
		if(scannedArrivedVOs != null && scannedArrivedVOs.size() > 0){
			scannedDetailsVO.setArrivedMails(scannedArrivedVOs);
		}
		if(scannedReturnVOs != null && scannedReturnVOs.size() > 0){
			scannedDetailsVO.setReturnedMails(scannedReturnVOs);
		}
		
		if(scannedOffloadVOs != null && scannedOffloadVOs.size() > 0){
			scannedDetailsVO.setOffloadMails(scannedOffloadVOs);
		}
		
		if("N".equals(reassign)){
			scannedReassignmailVO.setMailDetails(mailbagReassignmailVOs);
			scannedReassignmailVOs.add(scannedReassignmailVO);
		}
		
		if(scannedReassignmailVOs != null && scannedReassignmailVOs.size() > 0){
			scannedDetailsVO.setReassignMails(scannedReassignmailVOs);
		}
		
		if("N".equals(transfer)){
			scannedTransferVO.setMailDetails(mailbagTransferVOs);
			scannedTransferVOs.add(scannedTransferVO);
		}
		
		if(scannedTransferVOs != null && scannedTransferVOs.size() > 0){
			scannedDetailsVO.setTransferMails(scannedTransferVOs);
		}
		
		if("N".equals(despatch)){
			scannedReassigndespatchVO.setDespatchDetails(despatchReassignVOs);
			scannedReassigndespatchesVOs.add(scannedReassigndespatchVO);
		}
		
		if(scannedReassigndespatchesVOs != null && scannedReassigndespatchesVOs.size() > 0){
			scannedDetailsVO.setReassignDespatch(scannedReassigndespatchesVOs);
		}
		
		
		 Collection<ScannedMailDetailsVO> scannedAcptVOs =  scannedDetailsVO.getOutboundMails();
		 if(scannedAcptVOs != null && scannedAcptVOs.size() > 0){
			for(ScannedMailDetailsVO scannedMailVO:scannedAcptVOs){
				if(scannedMailVO.getCarrierId()== 0){
					scannedMailVO.setNewContainer("Y");
				}
				if(("N").equals(scannedDetailsVO.getPreassignFlag())){
					scannedMailVO.setCarrierCode(logonAttributes.getOwnAirlineCode());
					scannedMailVO.setCarrierId(logonAttributes.getOwnAirlineIdentifier());
				}
			}
		 }
		
		scannedDetailsVO.setOutboundMails(scannedAcptVOs);
		log.log(Log.INFO, " scannedDetailsVO ..\n", scannedDetailsVO);
		uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
	
		ScannedDetailsVO summaryVO = new ScannedDetailsVO(); 
		Collection<ScannedMailDetailsVO> scannedAcpVOs = scannedDetailsVO.getOutboundMails();
		Collection<ScannedMailDetailsVO> scannedArrVOs = scannedDetailsVO.getArrivedMails();
		Collection<ScannedMailDetailsVO> scannedRetVOs = scannedDetailsVO.getReturnedMails();
		Collection<ScannedMailDetailsVO> scannedNewAcpVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<ScannedMailDetailsVO> scannedNewArrVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<ScannedMailDetailsVO> scannedNewRetVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		try{
		if(scannedAcpVOs != null && scannedAcpVOs.size() > 0){
			for(ScannedMailDetailsVO scannedMailVO:scannedAcpVOs){
				ScannedMailDetailsVO newScannedMailVO = new ScannedMailDetailsVO();
				BeanHelper.copyProperties(newScannedMailVO,scannedMailVO);
				mailbagVOs = new ArrayList<MailbagVO>();
				mailbagVOs = newScannedMailVO.getMailDetails();
				if(mailbagVOs != null && mailbagVOs.size() > 0){
					newScannedMailVO.setScannedBags(mailbagVOs.size());
					newScannedMailVO.setSavedBags(0);
					newScannedMailVO.setUnsavedBags(mailbagVOs.size());
				}
				scannedNewAcpVOs.add(newScannedMailVO);
			}
		}
		
		if(scannedArrVOs != null && scannedArrVOs.size() > 0){
			HashSet<String> key=new HashSet<String>();
			String tempKeyOne="";
			String tempKeyTwo="";
			Collection<MailbagVO> mailVOs = new ArrayList<MailbagVO>();
			for(ScannedMailDetailsVO scannedMailVO:scannedArrVOs){
				for(MailbagVO arrivedMailVO:scannedMailVO.getMailDetails()){
					ScannedMailDetailsVO arrivedSummayVO=new ScannedMailDetailsVO();
					tempKeyOne=arrivedMailVO.getCarrierCode()+HYPHEN
					+arrivedMailVO.getFlightNumber()+HYPHEN
					+arrivedMailVO.getFlightDate();
					if(!(key.contains(tempKeyOne))){
						mailVOs = new ArrayList<MailbagVO>();
						arrivedSummayVO.setCarrierCode(arrivedMailVO.getCarrierCode());
						arrivedSummayVO.setCarrierId(arrivedMailVO.getCarrierId());
						arrivedSummayVO.setCompanyCode(arrivedMailVO.getCompanyCode());
						arrivedSummayVO.setContainerNumber(arrivedMailVO.getContainerNumber());
						arrivedSummayVO.setPol(arrivedMailVO.getPol());
						arrivedSummayVO.setPou(arrivedMailVO.getPou());
						arrivedSummayVO.setFlightDate(arrivedMailVO.getFlightDate());
						arrivedSummayVO.setFlightNumber(arrivedMailVO.getFlightNumber());
						arrivedSummayVO.setFlightSequenceNumber(arrivedMailVO.getFlightSequenceNumber());
						arrivedSummayVO.setLegSerialNumber(arrivedMailVO.getLegSerialNumber());
						
						mailVOs.add(arrivedMailVO);
						arrivedSummayVO.setMailDetails(mailVOs);
						arrivedSummayVO.setScannedBags(arrivedSummayVO.getMailDetails().size());
						arrivedSummayVO.setUnsavedBags(arrivedSummayVO.getScannedBags());
						arrivedSummayVO.setSavedBags(0);
						
						key.add(arrivedSummayVO.getCarrierCode()+HYPHEN
								+arrivedSummayVO.getFlightNumber()+HYPHEN
								+arrivedSummayVO.getFlightDate());
						scannedNewArrVOs.add(arrivedSummayVO);
					}else{
						for(ScannedMailDetailsVO arrivedSummaryVO:scannedNewArrVOs){
							tempKeyTwo=arrivedSummaryVO.getCarrierCode()+HYPHEN
							+arrivedSummaryVO.getFlightNumber()+HYPHEN
							+arrivedSummaryVO.getFlightDate();
							if(tempKeyOne.equals(tempKeyTwo)){
								mailVOs.add(arrivedMailVO);
								arrivedSummaryVO.setMailDetails(mailVOs);
								arrivedSummaryVO.setScannedBags(arrivedSummaryVO.getScannedBags()+1);
								arrivedSummaryVO.setUnsavedBags(arrivedSummaryVO.getScannedBags());
							}
						}
					}
				}
			}
		}

		if(scannedRetVOs != null && scannedRetVOs.size() > 0){
			for(ScannedMailDetailsVO scannedMailVO:scannedRetVOs){
				ScannedMailDetailsVO newScannedMailVO = new ScannedMailDetailsVO();
				BeanHelper.copyProperties(newScannedMailVO,scannedMailVO);
				mailbagVOs = new ArrayList<MailbagVO>();
				mailbagVOs = newScannedMailVO.getMailDetails();
				if(mailbagVOs != null && mailbagVOs.size() > 0){
					newScannedMailVO.setScannedBags(mailbagVOs.size());
					newScannedMailVO.setSavedBags(0);
					newScannedMailVO.setUnsavedBags(mailbagVOs.size());
				}
				scannedNewRetVOs.add(newScannedMailVO);
			}
		}
		}catch (SystemException e) {
			e.getMessage();
		}
		
		summaryVO.setOutboundMails(scannedNewAcpVOs);
		summaryVO.setArrivedMails(scannedNewArrVOs);
		summaryVO.setReturnedMails(scannedNewRetVOs);
		uploadMailSession.setScannedSummaryVO(summaryVO);
		
		
	
       Collection<OneTimeVO> rcVOs = new ArrayList<OneTimeVO>();
	   if(oneTimes!=null){
		   rcVOs = oneTimes.get(REASONCODE);
	   }	
	   uploadMailSession.setOneTimeReasonCode(rcVOs);
		
		invocationContext.target = TARGET;
		log.exiting("UploadCommand","execute");
		
	}*/
	
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(REASONCODE);
			fieldValues.add("mailtracking.defaults.registeredorinsuredcode");
			fieldValues.add("mailtracking.defaults.mailcategory");
			fieldValues.add("mailtracking.defaults.highestnumbermail");
			fieldValues.add("mailtracking.defaults.offload.reasoncode");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}
	
	/**
	 * This method is to format flightNumber
	 * Not using - CRQ-AirNZ989-12
	 * @param flightNumber 
	 * @return String
	 */
	/*private String formatFlightNumber(String flightNumber){
		int numLength = flightNumber.length();
		String newFlightNumber = "" ;
	    if(numLength == 1) { 
	    	newFlightNumber = new  StringBuilder("000").append(flightNumber).toString();
	    }
	    else if(numLength == 2) {
	    	newFlightNumber = new  StringBuilder("00").append(flightNumber).toString();
	    }
	    else if(numLength == 3) { 
	    	newFlightNumber = new  StringBuilder("0").append(flightNumber).toString();
	    }
	    else {
	    	newFlightNumber = flightNumber ;
	    }
			return newFlightNumber;
	}*/
	
	/*private boolean compareMailBatchVO(MailTrackingDefaultsBatchVO mailBatchVOOne ,MailTrackingDefaultsBatchVO mailBatchVOTwo){
		log.log(Log.FINE, " mailBatchVOOne", mailBatchVOOne);
		log.log(Log.FINE, " mailBatchVOTwo", mailBatchVOTwo);
		if(mailBatchVOOne.getCarrierCode()!=null && mailBatchVOTwo.getCarrierCode()!=null && 
				mailBatchVOOne.getCarrierCode().length() >0  && mailBatchVOTwo.getCarrierCode().length() >0 ){
			log.log(Log.FINE, " mailBatchVOOne.getCarrierCode().length()",
					mailBatchVOOne.getCarrierCode().length());
			log.log(Log.FINE, " mailBatchVOTwo.getCarrierCode().length()",
					mailBatchVOTwo.getCarrierCode().length());
			if(mailBatchVOOne.getCarrierCode().equals(mailBatchVOTwo.getCarrierCode())){
				log.log(Log.FINE," in if if it is carrier");
				return false;
			}else{
				log.log(Log.FINE," in else if it is carrier");
				return true;
			}
		}else{
			if(mailBatchVOOne.getFlightCarrierCode().equals(mailBatchVOTwo.getFlightCarrierCode())
					&& mailBatchVOOne.getFlightDate().equals(mailBatchVOTwo.getFlightDate())
							&& mailBatchVOOne.getFlightNumber().equals(mailBatchVOTwo.getFlightNumber())){
					log.log(Log.FINE," in if if it is flight");
						return false;
					}else{
						log.log(Log.FINE," in else if it is flight");
						return true;
					}
		}
		*/
	}


